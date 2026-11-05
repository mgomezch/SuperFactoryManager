package ca.teamdman.sfm.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import ca.teamdman.sfm.Config;
import ca.teamdman.sfm.SuperFactoryManager;
import ca.teamdman.sfm.common.block.ModBlocks;
import ca.teamdman.sfm.common.gui.GuiLabelGun;
import ca.teamdman.sfm.common.item.ItemDisk;
import ca.teamdman.sfm.common.item.ModItems;
import ca.teamdman.sfm.common.label.LabelPositionHolder;
import ca.teamdman.sfm.common.tileentity.LogLevel;
import ca.teamdman.sfm.common.tileentity.TileEntityManager;
import ca.teamdman.sfm.common.util.BlockPos;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class TestAutomationController {

    private static TestAutomationController INSTANCE;
    private final int port;
    private ServerSocket serverSocket;
    private final ConcurrentLinkedQueue<TaskEntry> taskQueue = new ConcurrentLinkedQueue<>();
    private boolean autoWorldTriggered = false;
    private int ticksInMainMenu = 0;

    private static class TaskEntry {

        final Callable<String> callable;
        final CompletableFuture<String> future = new CompletableFuture<>();

        TaskEntry(Callable<String> callable) {
            this.callable = callable;
        }
    }

    public static synchronized void init() {
        if (INSTANCE == null) {
            int port = 25573;
            String portProp = System.getProperty("sfm.harness.port");
            if (portProp == null || portProp.isEmpty()) {
                portProp = System.getProperty("gtnh.sfm.port");
            }
            if (portProp != null && !portProp.isEmpty()) {
                try {
                    port = Integer.parseInt(portProp);
                } catch (NumberFormatException ignored) {}
            }
            INSTANCE = new TestAutomationController(port);
            INSTANCE.start();
        }
    }

    public TestAutomationController(int port) {
        this.port = port;
    }

    public void start() {
        FMLCommonHandler.instance()
            .bus()
            .register(this);
        Thread serverThread = new Thread(this::runServer, "SFM-Test-Automation-Server");
        serverThread.setDaemon(true);
        serverThread.start();
        SuperFactoryManager.LOG.info("[TestAutomation] SFM Controller initialized on port " + port);
    }

    private void runServer() {
        try {
            serverSocket = new ServerSocket(port, 10, InetAddress.getByName("127.0.0.1"));
            SuperFactoryManager.LOG.info("[TestAutomation] Listening on 127.0.0.1:" + port);
            while (!serverSocket.isClosed()) {
                Socket client = serverSocket.accept();
                handleClient(client);
            }
        } catch (IOException e) {
            if (!serverSocket.isClosed()) {
                SuperFactoryManager.LOG.error("[TestAutomation] Server socket error: " + e.getMessage());
            }
        }
    }

    /**
     * Serves one connection: each line is a JSON request {@code {"id": <any>, "cmd": "<name>", "args": {...}}},
     * answered by one JSON line {@code {"id": <same>, "ok": true, "result": <any>}} or
     * {@code {"id": <same>, "ok": false, "error": {"code": "<CODE>", "message": "<text>"}}}.
     */
    private void handleClient(Socket client) {
        CompletableFuture.runAsync(() -> {
            try (
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(client.getInputStream(), java.nio.charset.StandardCharsets.UTF_8));
                PrintWriter out = new PrintWriter(
                    new java.io.OutputStreamWriter(client.getOutputStream(), java.nio.charset.StandardCharsets.UTF_8),
                    true)) {
                String line;
                while ((line = in.readLine()) != null) {
                    if (line.trim()
                        .isEmpty()) continue;
                    out.println(handleRequest(line));
                }
            } catch (Exception e) {
                SuperFactoryManager.LOG.error("[TestAutomation] Error handling client: " + e.getMessage());
            } finally {
                try {
                    client.close();
                } catch (IOException ignored) {}
            }
        });
    }

    private String handleRequest(String line) {
        JsonElement id = JsonNull.INSTANCE;
        try {
            JsonElement root;
            try {
                root = new JsonParser().parse(line);
            } catch (com.google.gson.JsonParseException e) {
                throw new CommandError("BAD_REQUEST", "request is not JSON: " + e.getMessage());
            }
            if (!root.isJsonObject()) throw new CommandError("BAD_REQUEST", "request must be a JSON object");
            JsonObject req = root.getAsJsonObject();
            if (req.has("id")) id = req.get("id");
            JsonElement cmdElem = req.get("cmd");
            if (cmdElem == null || !cmdElem.isJsonPrimitive()
                || !cmdElem.getAsJsonPrimitive()
                    .isString())
                throw new CommandError("BAD_REQUEST", "request needs a string 'cmd'");
            JsonElement argsElem = req.get("args");
            Args args;
            if (argsElem == null || argsElem.isJsonNull()) args = Args.EMPTY;
            else if (argsElem.isJsonObject()) args = new Args(argsElem.getAsJsonObject());
            else throw new CommandError("BAD_REQUEST", "'args' must be a JSON object");

            String result = processCommand(
                cmdElem.getAsString()
                    .trim()
                    .toLowerCase(),
                args);
            JsonObject resp = new JsonObject();
            resp.add("id", id);
            resp.addProperty("ok", true);
            resp.add("result", toResult(result));
            return resp.toString();
        } catch (CommandError e) {
            return errorResponse(id, e.code, e.getMessage());
        } catch (Throwable t) {
            CommandError e = CommandError.wrap("INTERNAL", t);
            return errorResponse(id, e.code, e.getMessage());
        }
    }

    /** A command's result as JSON: the JSON it built, or its message as a JSON string. */
    private static JsonElement toResult(String result) {
        if (result == null) return JsonNull.INSTANCE;
        String t = result.trim();
        if ((t.startsWith("{") && t.endsWith("}")) || (t.startsWith("[") && t.endsWith("]"))) {
            try {
                return new JsonParser().parse(t);
            } catch (com.google.gson.JsonParseException ignored) {}
        }
        return new JsonPrimitive(result);
    }

    private static String errorResponse(JsonElement id, String code, String message) {
        JsonObject err = new JsonObject();
        err.addProperty("code", code);
        err.addProperty("message", message);
        JsonObject resp = new JsonObject();
        resp.add("id", id);
        resp.addProperty("ok", false);
        resp.add("error", err);
        return resp.toString();
    }

    private String processCommand(String cmd, Args a) {
        if ("ping".equals(cmd)) {
            return "pong";
        }

        CompletableFuture<String> future = executeOnClient(() -> {
            Minecraft mc = Minecraft.getMinecraft();
            switch (cmd) {
                case "status":
                    if (mc.thePlayer != null && mc.theWorld != null) {
                        String gui = mc.currentScreen != null ? mc.currentScreen.getClass()
                            .getSimpleName() : "none";
                        ItemStack held = mc.thePlayer.getHeldItem();
                        String heldName = held != null ? held.getItem()
                            .getItemStackDisplayName(held) : "empty";
                        // status: {state: IN_WORLD, pos, gui, held} | {state: MAIN_MENU|GUI, gui} | {state: LOADING}
                        JsonObject st = new JsonObject();
                        st.addProperty("state", "IN_WORLD");
                        com.google.gson.JsonArray pos = new com.google.gson.JsonArray();
                        pos.add(new JsonPrimitive(mc.thePlayer.posX));
                        pos.add(new JsonPrimitive(mc.thePlayer.posY));
                        pos.add(new JsonPrimitive(mc.thePlayer.posZ));
                        st.add("pos", pos);
                        st.addProperty("gui", gui);
                        st.addProperty("held", heldName);
                        return st.toString();
                    } else {
                        JsonObject st = new JsonObject();
                        st.addProperty(
                            "state",
                            mc.currentScreen instanceof GuiMainMenu ? "MAIN_MENU"
                                : mc.currentScreen != null ? "GUI" : "LOADING");
                        if (mc.currentScreen != null) st.addProperty(
                            "gui",
                            mc.currentScreen.getClass()
                                .getName());
                        return st.toString();
                    }

                case "autoworld":
                    if (mc.thePlayer != null) {
                        return "ALREADY_IN_WORLD";
                    }
                    String worldName = a.str("name", "TestWorld");
                    launchWorld(worldName);
                    return "LAUNCHING_WORLD: " + worldName;

                case "exportlogs":
                    // exportlogs {[enabled]}
                    Config.exportLogsToFile = a.b("enabled", Config.exportLogsToFile);
                    return "EXPORT_LOGS_TO_FILE: " + Config.exportLogsToFile;

                case "discordbutton":
                    // discordbutton {[enabled]}
                    Config.showDiscordButton = a.b("enabled", Config.showDiscordButton);
                    return "SHOW_DISCORD_BUTTON: " + Config.showDiscordButton;

                case "runscenario": {
                    // runscenario {scenario}
                    int scen = a.i("scenario");
                    boolean prevExport = Config.exportLogsToFile;
                    Config.exportLogsToFile = true;
                    File logF = new File("logs/sfm_server_export.log");
                    if (logF.exists()) logF.delete();

                    net.minecraft.world.World ws = null;
                    if (net.minecraft.server.MinecraftServer.getServer() != null) {
                        ws = net.minecraft.server.MinecraftServer.getServer()
                            .worldServerForDimension(mc.thePlayer != null ? mc.thePlayer.dimension : 0);
                    }
                    if (ws == null) {
                        ws = mc.theWorld;
                    }
                    if (ws == null) throw CommandError.of("ERROR: No world");

                    int bx = 500, by = 60, bz = 0;
                    BlockPos c1 = new BlockPos(bx + 1, by, bz);
                    BlockPos c2 = new BlockPos(bx + 2, by, bz);
                    BlockPos posA = new BlockPos(bx + 1, by + 1, bz);
                    BlockPos posB = new BlockPos(bx + 2, by + 1, bz);

                    ws.setBlock(bx, by, bz, ModBlocks.manager);
                    ws.setBlock(c1.x, c1.y, c1.z, ModBlocks.cable);
                    ws.setBlock(c2.x, c2.y, c2.z, ModBlocks.cable);
                    ws.setBlock(posA.x, posA.y, posA.z, net.minecraft.init.Blocks.chest);
                    ws.setBlock(posB.x, posB.y, posB.z, net.minecraft.init.Blocks.chest);

                    net.minecraft.tileentity.TileEntityChest cIn = (net.minecraft.tileentity.TileEntityChest) ws
                        .getTileEntity(posA.x, posA.y, posA.z);
                    net.minecraft.tileentity.TileEntityChest cOut = (net.minecraft.tileentity.TileEntityChest) ws
                        .getTileEntity(posB.x, posB.y, posB.z);
                    if (cIn != null) {
                        for (int i = 0; i < cIn.getSizeInventory(); i++) cIn.setInventorySlotContents(i, null);
                    }
                    if (cOut != null) {
                        for (int i = 0; i < cOut.getSizeInventory(); i++) cOut.setInventorySlotContents(i, null);
                    }

                    String pCode;
                    LogLevel pLvl;
                    if (scen == 1) {
                        if (cIn != null)
                            cIn.setInventorySlotContents(0, new ItemStack(net.minecraft.init.Items.apple, 64));
                        pCode = "NAME \"Test 1\"\nEVERY 20 TICKS DO\n  INPUT FROM a\n  OUTPUT TO b\nEND\n";
                        pLvl = LogLevel.TRACE;
                    } else if (scen == 2) {
                        if (cIn != null)
                            cIn.setInventorySlotContents(0, new ItemStack(net.minecraft.init.Items.apple, 64));
                        pCode = "NAME \"Test 2\"\nEVERY 20 TICKS DO\n  IF a HAS >= 1 THEN\n    INPUT FROM a\n    OUTPUT TO b\n  END\nEND\n";
                        pLvl = LogLevel.DEBUG;
                    } else {
                        pCode = "NAME \"Test 3\"\nEVERY 20 TICKS DO\n  INPUT FROM a\n  OUTPUT TO b\nEND\n";
                        pLvl = LogLevel.DEBUG;
                    }

                    TileEntityManager mgr = (TileEntityManager) ws.getTileEntity(bx, by, bz);
                    if (mgr == null)
                        throw CommandError.of("ERROR: No manager tile entity at " + bx + "," + by + "," + bz);

                    for (Object te : ws.loadedTileEntityList) {
                        if (te instanceof TileEntityManager && te != mgr) {
                            ((TileEntityManager) te).setLogLevel(LogLevel.OFF);
                        }
                    }

                    ItemStack dStack = new ItemStack(ModItems.disk);
                    LabelPositionHolder lpHolder = new LabelPositionHolder();
                    lpHolder.addPosition("a", posA);
                    lpHolder.addPosition("b", posB);
                    LabelPositionHolder.saveTo(dStack, lpHolder);
                    ItemDisk.setProgramString(dStack, pCode);
                    mgr.setInventorySlotContents(0, dStack);
                    mgr.recompile(pCode);
                    mgr.setLogLevel(pLvl);
                    mgr.setManagerTick(19);

                    mgr.updateEntity();
                    Config.exportLogsToFile = prevExport;

                    return "SCENARIO_RAN: " + scen;
                }

                case "setlabel": {
                    // setlabel {x, y, z, label, tx, ty, tz}: the manager at x, y, z labels the block at tx, ty, tz
                    int mx = a.i("x");
                    int my = a.i("y");
                    int mz = a.i("z");
                    String labelName = a.str("label");
                    int tx = a.i("tx");
                    int ty = a.i("ty");
                    int tz = a.i("tz");

                    net.minecraft.world.World ws = null;
                    if (net.minecraft.server.MinecraftServer.getServer() != null) {
                        ws = net.minecraft.server.MinecraftServer.getServer()
                            .worldServerForDimension(mc.thePlayer != null ? mc.thePlayer.dimension : 0);
                    }
                    if (ws == null) ws = mc.theWorld;
                    if (ws == null) throw CommandError.of("ERROR: No world");

                    TileEntityManager mgr = (TileEntityManager) ws.getTileEntity(mx, my, mz);
                    if (mgr == null) throw CommandError.of("ERROR: No manager at " + mx + "," + my + "," + mz);

                    ItemStack disk = mgr.getStackInSlot(0);
                    if (disk == null || disk.getItem() != ModItems.disk) {
                        disk = new ItemStack(ModItems.disk);
                    }
                    LabelPositionHolder holder = LabelPositionHolder.from(disk);
                    holder.addPosition(labelName, new BlockPos(tx, ty, tz));
                    LabelPositionHolder.saveTo(disk, holder);
                    mgr.setInventorySlotContents(0, disk);
                    mgr.rebuildNetwork();
                    mgr.markDirty();
                    if (mc.theWorld != null && mc.theWorld != ws) {
                        TileEntityManager clientMgr = (TileEntityManager) mc.theWorld.getTileEntity(mx, my, mz);
                        if (clientMgr != null) clientMgr.setInventorySlotContents(0, disk.copy());
                    }
                    return "LABEL_SET: " + labelName + " -> " + tx + "," + ty + "," + tz;
                }

                case "clearlabels": {
                    // clearlabels {x, y, z}
                    int mx = a.i("x");
                    int my = a.i("y");
                    int mz = a.i("z");

                    net.minecraft.world.World ws = null;
                    if (net.minecraft.server.MinecraftServer.getServer() != null) {
                        ws = net.minecraft.server.MinecraftServer.getServer()
                            .worldServerForDimension(mc.thePlayer != null ? mc.thePlayer.dimension : 0);
                    }
                    if (ws == null) ws = mc.theWorld;
                    if (ws == null) throw CommandError.of("ERROR: No world");

                    TileEntityManager mgr = (TileEntityManager) ws.getTileEntity(mx, my, mz);
                    if (mgr == null) throw CommandError.of("ERROR: No manager at " + mx + "," + my + "," + mz);

                    ItemStack disk = mgr.getStackInSlot(0);
                    if (disk == null || disk.getItem() != ModItems.disk) {
                        disk = new ItemStack(ModItems.disk);
                    }
                    LabelPositionHolder holder = new LabelPositionHolder();
                    LabelPositionHolder.saveTo(disk, holder);
                    mgr.setInventorySlotContents(0, disk);
                    mgr.rebuildNetwork();
                    mgr.markDirty();
                    if (mc.theWorld != null && mc.theWorld != ws) {
                        TileEntityManager clientMgr = (TileEntityManager) mc.theWorld.getTileEntity(mx, my, mz);
                        if (clientMgr != null) clientMgr.setInventorySlotContents(0, disk.copy());
                    }
                    return "LABELS_CLEARED";
                }

                case "programmanager": {
                    // programmanager {x, y, z, code}
                    int mx = a.i("x");
                    int my = a.i("y");
                    int mz = a.i("z");
                    String pCode = a.str("code");

                    net.minecraft.world.World ws = null;
                    if (net.minecraft.server.MinecraftServer.getServer() != null) {
                        ws = net.minecraft.server.MinecraftServer.getServer()
                            .worldServerForDimension(mc.thePlayer != null ? mc.thePlayer.dimension : 0);
                    }
                    if (ws == null) ws = mc.theWorld;
                    if (ws == null) throw CommandError.of("ERROR: No world");

                    TileEntityManager mgr = (TileEntityManager) ws.getTileEntity(mx, my, mz);
                    if (mgr == null) throw CommandError.of("ERROR: No manager at " + mx + "," + my + "," + mz);

                    ItemStack disk = mgr.getStackInSlot(0);
                    if (disk == null || disk.getItem() != ModItems.disk) {
                        disk = new ItemStack(ModItems.disk);
                    }
                    ItemDisk.setProgramString(disk, pCode);
                    mgr.setInventorySlotContents(0, disk);
                    mgr.recompile(pCode);
                    mgr.rebuildNetwork();
                    mgr.setManagerTick(0);
                    mgr.setRunning(true);
                    mgr.setLogLevel(LogLevel.DEBUG);
                    mgr.markDirty();
                    if (mc.theWorld != null && mc.theWorld != ws) {
                        TileEntityManager clientMgr = (TileEntityManager) mc.theWorld.getTileEntity(mx, my, mz);
                        if (clientMgr != null) {
                            clientMgr.setInventorySlotContents(0, disk.copy());
                            clientMgr.recompile(pCode);
                        }
                    }
                    JsonObject programmed = new JsonObject();
                    programmed.addProperty("valid", mgr.hasCompiledProgram());
                    return programmed.toString();
                }

                case "getmanagerstatus": {
                    // getmanagerstatus {x, y, z}: {tick, running, hasDisk, hasProgram, status}
                    int mx = a.i("x");
                    int my = a.i("y");
                    int mz = a.i("z");

                    net.minecraft.world.World ws = null;
                    if (net.minecraft.server.MinecraftServer.getServer() != null) {
                        ws = net.minecraft.server.MinecraftServer.getServer()
                            .worldServerForDimension(mc.thePlayer != null ? mc.thePlayer.dimension : 0);
                    }
                    if (ws == null) ws = mc.theWorld;
                    if (ws == null) throw CommandError.of("ERROR: No world");

                    TileEntityManager mgr = (TileEntityManager) ws.getTileEntity(mx, my, mz);
                    if (mgr == null) throw CommandError.of("ERROR: No manager at " + mx + "," + my + "," + mz);

                    JsonObject ms = new JsonObject();
                    ms.addProperty("tick", mgr.getManagerTick());
                    ms.addProperty("running", mgr.isRunning());
                    ms.addProperty("hasDisk", mgr.getDisk() != null);
                    ms.addProperty("hasProgram", mgr.hasCompiledProgram());
                    ms.addProperty("status", mgr.getStatusMessage());
                    return ms.toString();
                }

                case "stepmanager": {
                    // stepmanager {x, y, z, [ticks]}
                    int mx = a.i("x");
                    int my = a.i("y");
                    int mz = a.i("z");
                    int steps = a.i("ticks", 1);

                    net.minecraft.world.World ws = null;
                    if (net.minecraft.server.MinecraftServer.getServer() != null) {
                        ws = net.minecraft.server.MinecraftServer.getServer()
                            .worldServerForDimension(mc.thePlayer != null ? mc.thePlayer.dimension : 0);
                    }
                    if (ws == null) ws = mc.theWorld;
                    if (ws == null) throw CommandError.of("ERROR: No world");

                    TileEntityManager mgr = (TileEntityManager) ws.getTileEntity(mx, my, mz);
                    if (mgr == null) throw CommandError.of("ERROR: No manager at " + mx + "," + my + "," + mz);

                    boolean wasRunning = mgr.isRunning();
                    mgr.setRunning(true);
                    for (int s = 0; s < steps; s++) {
                        mgr.updateEntity();
                    }
                    mgr.setRunning(wasRunning);
                    return "STEPPED: " + steps + " (tick=" + mgr.getManagerTick() + ")";
                }

                case "setrunning": {
                    // setrunning {x, y, z, running}
                    int mx = a.i("x");
                    int my = a.i("y");
                    int mz = a.i("z");
                    boolean run = a.b("running");

                    net.minecraft.world.World ws = null;
                    if (net.minecraft.server.MinecraftServer.getServer() != null) {
                        ws = net.minecraft.server.MinecraftServer.getServer()
                            .worldServerForDimension(mc.thePlayer != null ? mc.thePlayer.dimension : 0);
                    }
                    if (ws == null) ws = mc.theWorld;
                    if (ws == null) throw CommandError.of("ERROR: No world");

                    TileEntityManager mgr = (TileEntityManager) ws.getTileEntity(mx, my, mz);
                    if (mgr == null) throw CommandError.of("ERROR: No manager at " + mx + "," + my + "," + mz);

                    mgr.setRunning(run);
                    mgr.markDirty();
                    return "RUNNING_SET: " + mgr.isRunning();
                }

                case "settick": {
                    // settick {x, y, z, tick}
                    int mx = a.i("x");
                    int my = a.i("y");
                    int mz = a.i("z");
                    long tick = a.l("tick");

                    net.minecraft.world.World ws = null;
                    if (net.minecraft.server.MinecraftServer.getServer() != null) {
                        ws = net.minecraft.server.MinecraftServer.getServer()
                            .worldServerForDimension(mc.thePlayer != null ? mc.thePlayer.dimension : 0);
                    }
                    if (ws == null) ws = mc.theWorld;
                    if (ws == null) throw CommandError.of("ERROR: No world");

                    TileEntityManager mgr = (TileEntityManager) ws.getTileEntity(mx, my, mz);
                    if (mgr == null) throw CommandError.of("ERROR: No manager at " + mx + "," + my + "," + mz);

                    mgr.setManagerTick(tick);
                    mgr.markDirty();
                    return "TICK_SET: " + mgr.getManagerTick();
                }

                case "opengui": {
                    // opengui {gui: labelgun|inventory|editor|manager, [x, y, z]}: the editor and manager GUIs are
                    // a manager's, at x, y, z (10, 6, 420 by default)
                    String gui = a.str("gui");
                    if ("labelgun".equalsIgnoreCase(gui)) {
                        if (mc.thePlayer == null) throw CommandError.of("ERROR: Player is null");
                        ItemStack stack = mc.thePlayer.getHeldItem();
                        if (stack == null || stack.getItem() != ModItems.labelGun) {
                            stack = new ItemStack(ModItems.labelGun);
                        }
                        mc.displayGuiScreen(new GuiLabelGun(mc.thePlayer, stack));
                        return "OPENED_LABEL_GUN_GUI";
                    } else if ("inventory".equalsIgnoreCase(gui)) {
                        if (mc.thePlayer == null) throw CommandError.of("ERROR: Player is null");
                        mc.displayGuiScreen(new GuiInventory(mc.thePlayer));
                        return "OPENED_INVENTORY_GUI";
                    } else if ("editor".equalsIgnoreCase(gui)) {
                        if (mc.thePlayer == null) throw CommandError.of("ERROR: Player is null");
                        int mx = a.i("x", 10), my = a.i("y", 6), mz = a.i("z", 420);
                        TileEntityManager m = (TileEntityManager) mc.theWorld.getTileEntity(mx, my, mz);
                        if (m == null) throw CommandError.of("ERROR: No manager at " + mx + "," + my + "," + mz);
                        mc.displayGuiScreen(new ca.teamdman.sfm.client.gui.GuiCodeEditor(m, mc.currentScreen));
                        return "OPENED_EDITOR_GUI";
                    } else if ("manager".equalsIgnoreCase(gui)) {
                        if (mc.thePlayer == null) throw CommandError.of("ERROR: Player is null");
                        int mx = 10, my = 6, mz = 420;
                        if (a.has("x")) {
                            mx = a.i("x");
                            my = a.i("y");
                            mz = a.i("z");
                        } else if (mc.theWorld.getTileEntity(500, 60, 0) instanceof TileEntityManager) {
                            mx = 500;
                            my = 60;
                            mz = 0;
                        }
                        TileEntityManager m = (TileEntityManager) mc.theWorld.getTileEntity(mx, my, mz);
                        if (m == null) throw CommandError.of("ERROR: No manager at " + mx + "," + my + "," + mz);
                        mc.displayGuiScreen(new ca.teamdman.sfm.common.gui.GuiManager(mc.thePlayer.inventory, m));
                        return "OPENED_MANAGER_GUI";
                    } else {
                        throw CommandError.of("UNKNOWN_GUI: " + gui);
                    }
                }

                case "closegui":
                    mc.gameSettings.pauseOnLostFocus = false;
                    mc.inGameHasFocus = true;
                    mc.displayGuiScreen(null);
                    return "CLOSED_GUI";

                case "slot":
                    if (mc.thePlayer == null) throw CommandError.of("ERROR: Player is null");
                    int slot = a.i("slot");
                    mc.thePlayer.inventory.currentItem = slot;
                    ItemStack slotItem = mc.thePlayer.getHeldItem();
                    return "SLOT: " + slot + " (" + (slotItem != null ? slotItem.getDisplayName() : "empty") + ")";

                case "useitem":
                    if (mc.thePlayer == null) throw CommandError.of("ERROR: Player is null");
                    ItemStack heldItem = mc.thePlayer.getHeldItem();
                    if (heldItem != null) {
                        heldItem.getItem()
                            .onItemRightClick(heldItem, mc.theWorld, mc.thePlayer);
                        return "USED_ITEM: " + heldItem.getDisplayName();
                    }
                    throw new CommandError("NO_HELD_ITEM", "the player holds nothing");

                case "type": {
                    // type {text}: typed into the open GUI
                    if (mc.currentScreen == null) throw CommandError.of("ERROR: No active GUI");
                    String text = a.str("text");
                    for (char c : text.toCharArray()) {
                        try {
                            if (mc.currentScreen instanceof ca.teamdman.sfm.client.gui.GuiCodeEditor) {
                                java.lang.reflect.Method km = ca.teamdman.sfm.client.gui.GuiCodeEditor.class
                                    .getDeclaredMethod("keyTyped", char.class, int.class);
                                km.setAccessible(true);
                                km.invoke(mc.currentScreen, c, 0);
                            } else {
                                java.lang.reflect.Method km = GuiScreen.class
                                    .getDeclaredMethod("keyTyped", char.class, int.class);
                                km.setAccessible(true);
                                km.invoke(mc.currentScreen, c, 0);
                            }
                        } catch (Exception e) {
                            throw CommandError.wrap("ERROR_TYPE", e);
                        }
                    }
                    return "TYPED: " + text;
                }

                case "settext":
                    // settext {text}: the label gun's label field
                    if (mc.currentScreen instanceof ca.teamdman.sfm.common.gui.GuiLabelGun) {
                        try {
                            java.lang.reflect.Field f = ca.teamdman.sfm.common.gui.GuiLabelGun.class
                                .getDeclaredField("labelField");
                            f.setAccessible(true);
                            GuiTextField tf = (GuiTextField) f.get(mc.currentScreen);
                            tf.setText(a.str("text"));
                            java.lang.reflect.Method m = ca.teamdman.sfm.common.gui.GuiLabelGun.class
                                .getDeclaredMethod("rebuildLabelButtons");
                            m.setAccessible(true);
                            m.invoke(mc.currentScreen);
                            return "SET_TEXT: " + a.str("text");
                        } catch (Exception e) {
                            throw CommandError.wrap("ERROR_SET_TEXT", e);
                        }
                    }
                    throw new CommandError("WRONG_GUI", "the label gun GUI is not open");

                case "enter":
                    if (mc.currentScreen instanceof ca.teamdman.sfm.common.gui.GuiLabelGun) {
                        try {
                            java.lang.reflect.Method m = ca.teamdman.sfm.common.gui.GuiLabelGun.class
                                .getDeclaredMethod("onDone");
                            m.setAccessible(true);
                            m.invoke(mc.currentScreen);
                            return "DONE_TRIGGERED";
                        } catch (Exception e) {
                            throw CommandError.wrap("ERROR_DONE", e);
                        }
                    }
                    throw new CommandError("WRONG_GUI", "the label gun GUI is not open");

                case "key":
                    if (mc.currentScreen == null) throw CommandError.of("ERROR: No active GUI");
                    // key {key}: an LWJGL key code typed into the open GUI
                    int keyCode = a.i("key");
                    try {
                        if (mc.currentScreen instanceof ca.teamdman.sfm.client.gui.GuiCodeEditor) {
                            java.lang.reflect.Method km = ca.teamdman.sfm.client.gui.GuiCodeEditor.class
                                .getDeclaredMethod("keyTyped", char.class, int.class);
                            km.setAccessible(true);
                            km.invoke(mc.currentScreen, '\0', keyCode);
                        } else {
                            java.lang.reflect.Method km = GuiScreen.class
                                .getDeclaredMethod("keyTyped", char.class, int.class);
                            km.setAccessible(true);
                            km.invoke(mc.currentScreen, '\0', keyCode);
                        }
                    } catch (Exception e) {
                        throw CommandError.wrap("ERROR_KEY", e);
                    }
                    return "KEY_PRESSED: " + keyCode;

                case "chat":
                    if (mc.thePlayer == null) throw CommandError.of("ERROR: Player is null");
                    // chat {message}
                    mc.thePlayer.sendChatMessage(a.str("message"));
                    return "CHAT_SENT: " + a.str("message");

                case "click":
                    if (mc.currentScreen == null) throw CommandError.of("ERROR: No active GUI to click");
                    // click {x, y, [button]}
                    int x = a.i("x");
                    int y = a.i("y");
                    int button = a.i("button", 0);
                    try {
                        if (mc.currentScreen instanceof ca.teamdman.sfm.client.gui.GuiCodeEditor) {
                            java.lang.reflect.Method m = ca.teamdman.sfm.client.gui.GuiCodeEditor.class
                                .getDeclaredMethod("mouseClicked", int.class, int.class, int.class);
                            m.setAccessible(true);
                            m.invoke(mc.currentScreen, x, y, button);
                        } else {
                            java.lang.reflect.Method m = GuiScreen.class
                                .getDeclaredMethod("mouseClicked", int.class, int.class, int.class);
                            m.setAccessible(true);
                            m.invoke(mc.currentScreen, x, y, button);
                        }
                    } catch (Exception e) {
                        throw CommandError.wrap("ERROR_CLICK", e);
                    }
                    return "CLICKED: " + x + ", " + y + " (button " + button + ")";

                case "drag":
                    if (mc.currentScreen == null) throw CommandError.of("ERROR: No active GUI to drag");
                    // drag {x1, y1, x2, y2, [steps]}
                    int dx1 = a.i("x1");
                    int dy1 = a.i("y1");
                    int dx2 = a.i("x2");
                    int dy2 = a.i("y2");
                    int steps = a.i("steps", 10);
                    try {
                        Method mDown = null;
                        Method mMove = null;
                        Method mUp = null;
                        Class<?> cls = mc.currentScreen.getClass();
                        while (cls != null && cls != Object.class) {
                            for (Method m : cls.getDeclaredMethods()) {
                                if (m.getName()
                                    .equals("mouseClicked") && m.getParameterTypes().length == 3) mDown = m;
                                if (m.getName()
                                    .equals("mouseClickMove") && m.getParameterTypes().length == 4) mMove = m;
                                if (m.getName()
                                    .equals("mouseMovedOrUp") && m.getParameterTypes().length == 3) mUp = m;
                            }
                            cls = cls.getSuperclass();
                        }
                        if (mDown != null) {
                            mDown.setAccessible(true);
                            mDown.invoke(mc.currentScreen, dx1, dy1, 0);
                        }
                        if (mMove != null) {
                            mMove.setAccessible(true);
                            for (int s = 1; s <= steps; s++) {
                                int cx = dx1 + (dx2 - dx1) * s / steps;
                                int cy = dy1 + (dy2 - dy1) * s / steps;
                                mMove.invoke(mc.currentScreen, cx, cy, 0, 50L * s);
                            }
                        }
                        if (mUp != null) {
                            mUp.setAccessible(true);
                            mUp.invoke(mc.currentScreen, dx2, dy2, 0);
                        }
                        return "DRAGGED: (" + dx1 + "," + dy1 + ") -> (" + dx2 + "," + dy2 + ")";
                    } catch (Exception e) {
                        throw CommandError.wrap("ERROR_DRAG", e);
                    }

                case "resizegui":
                    if (mc.currentScreen instanceof ca.teamdman.sfm.client.gui.GuiCodeEditor) {
                        try {
                            ca.teamdman.sfm.client.gui.GuiCodeEditor ed = (ca.teamdman.sfm.client.gui.GuiCodeEditor) mc.currentScreen;
                            // resizegui {width, height}
                            int newW = a.i("width");
                            int newH = a.i("height");
                            Field fW = ca.teamdman.sfm.client.gui.GuiCodeEditor.class.getDeclaredField("editorW");
                            Field fH = ca.teamdman.sfm.client.gui.GuiCodeEditor.class.getDeclaredField("editorH");
                            fW.setAccessible(true);
                            fH.setAccessible(true);
                            fW.setInt(ed, newW);
                            fH.setInt(ed, newH);
                            ed.updateLayout();
                            return "RESIZED_GUI: " + newW + "x" + newH;
                        } catch (Exception e) {
                            throw CommandError.wrap("ERROR_RESIZE_GUI", e);
                        }
                    }
                    throw new CommandError("WRONG_GUI", "the code editor is not open");

                case "autoindent":
                    if (mc.currentScreen instanceof ca.teamdman.sfm.client.gui.GuiCodeEditor) {
                        try {
                            ca.teamdman.sfm.client.gui.GuiCodeEditor ed = (ca.teamdman.sfm.client.gui.GuiCodeEditor) mc.currentScreen;
                            ed.autoIndentAll();
                            return "AUTOINDENTED";
                        } catch (Exception e) {
                            throw CommandError.wrap("ERROR_AUTOINDENT", e);
                        }
                    }
                    throw new CommandError("WRONG_GUI", "the code editor is not open");

                case "getcode":
                    if (mc.currentScreen instanceof ca.teamdman.sfm.client.gui.GuiCodeEditor) {
                        try {
                            ca.teamdman.sfm.client.gui.GuiCodeEditor ed = (ca.teamdman.sfm.client.gui.GuiCodeEditor) mc.currentScreen;
                            Field fLines = ca.teamdman.sfm.client.gui.GuiCodeEditor.class.getDeclaredField("lines");
                            fLines.setAccessible(true);
                            java.util.List<String> edLines = (java.util.List<String>) fLines.get(ed);
                            // getcode: {code}
                            JsonObject code = new JsonObject();
                            code.addProperty("code", String.join("\n", edLines));
                            return code.toString();
                        } catch (Exception e) {
                            throw CommandError.wrap("ERROR_GET_CODE", e);
                        }
                    }
                    throw new CommandError("WRONG_GUI", "the code editor is not open");

                case "setcode":
                    if (mc.currentScreen instanceof ca.teamdman.sfm.client.gui.GuiCodeEditor) {
                        try {
                            ca.teamdman.sfm.client.gui.GuiCodeEditor ed = (ca.teamdman.sfm.client.gui.GuiCodeEditor) mc.currentScreen;
                            java.lang.reflect.Field fLines = ca.teamdman.sfm.client.gui.GuiCodeEditor.class
                                .getDeclaredField("lines");
                            fLines.setAccessible(true);
                            java.util.List<String> edLines = (java.util.List<String>) fLines.get(ed);
                            edLines.clear();
                            // setcode {code}
                            String codeText = a.str("code");
                            edLines.addAll(java.util.Arrays.asList(codeText.split("\n", -1)));
                            Field fCursorLine = ca.teamdman.sfm.client.gui.GuiCodeEditor.class
                                .getDeclaredField("cursorLine");
                            Field fCursorCol = ca.teamdman.sfm.client.gui.GuiCodeEditor.class
                                .getDeclaredField("cursorCol");
                            fCursorLine.setAccessible(true);
                            fCursorCol.setAccessible(true);
                            fCursorLine.setInt(ed, 0);
                            fCursorCol.setInt(ed, 0);
                            ed.updateLayout();
                            java.lang.reflect.Method m = ca.teamdman.sfm.client.gui.GuiCodeEditor.class
                                .getDeclaredMethod("validateSyntax");
                            m.setAccessible(true);
                            m.invoke(ed);
                            return "CODE_SET: " + edLines.size() + " lines";
                        } catch (Exception e) {
                            throw CommandError.wrap("ERROR_SET_CODE", e);
                        }
                    }
                    throw new CommandError("WRONG_GUI", "the code editor is not open");

                case "clickslot":
                    if (mc.thePlayer != null && mc.thePlayer.openContainer != null) {
                        // clickslot {slot, [button], [mode]}
                        int slotId = a.i("slot");
                        int mouseBtn = a.i("button", 0);
                        int clickMode = a.i("mode", 0);
                        mc.playerController.windowClick(
                            mc.thePlayer.openContainer.windowId,
                            slotId,
                            mouseBtn,
                            clickMode,
                            mc.thePlayer);
                        return "CLICKED_SLOT: " + slotId + " (btn " + mouseBtn + ", mode " + clickMode + ")";
                    }
                    throw new CommandError("NO_OPEN_CONTAINER", "no container is open");

                case "clickbutton": {
                    // clickbutton {button}: a button's id or a substring of its label
                    String which = a.scalar("button");
                    if (mc.currentScreen != null) {
                        try {
                            java.lang.reflect.Field f = GuiScreen.class.getDeclaredField("buttonList");
                            f.setAccessible(true);
                            java.util.List<net.minecraft.client.gui.GuiButton> btns = (java.util.List<net.minecraft.client.gui.GuiButton>) f
                                .get(mc.currentScreen);
                            for (net.minecraft.client.gui.GuiButton b : btns) {
                                if (String.valueOf(b.id)
                                    .equals(which)
                                    || b.displayString.toLowerCase()
                                        .contains(which.toLowerCase())) {
                                    java.lang.reflect.Method m = GuiScreen.class
                                        .getDeclaredMethod("actionPerformed", net.minecraft.client.gui.GuiButton.class);
                                    m.setAccessible(true);
                                    m.invoke(mc.currentScreen, b);
                                    return "CLICKED_BUTTON: " + b.id + " (" + b.displayString + ")";
                                }
                            }
                            throw new CommandError("NOT_FOUND", "no button matching '" + which + "'");
                        } catch (Exception e) {
                            throw CommandError.wrap("ERROR_CLICK_BUTTON", e);
                        }
                    }
                    throw new CommandError("NO_ACTIVE_GUI", "no GUI is open");
                }

                case "clicklabel":
                    if (mc.currentScreen instanceof ca.teamdman.sfm.common.gui.GuiLabelGun) {
                        try {
                            ca.teamdman.sfm.common.gui.GuiLabelGun gunGui = (ca.teamdman.sfm.common.gui.GuiLabelGun) mc.currentScreen;
                            java.lang.reflect.Field f = ca.teamdman.sfm.common.gui.GuiLabelGun.class
                                .getDeclaredField("labelBtnMap");
                            f.setAccessible(true);
                            java.util.Map<net.minecraft.client.gui.GuiButton, String> map = (java.util.Map<net.minecraft.client.gui.GuiButton, String>) f
                                .get(gunGui);
                            for (java.util.Map.Entry<net.minecraft.client.gui.GuiButton, String> entry : map
                                .entrySet()) {
                                if (entry.getValue()
                                    .equalsIgnoreCase(a.str("label"))) {
                                    java.lang.reflect.Method m = GuiScreen.class
                                        .getDeclaredMethod("actionPerformed", net.minecraft.client.gui.GuiButton.class);
                                    m.setAccessible(true);
                                    m.invoke(gunGui, entry.getKey());
                                    return "CLICKED_LABEL: " + entry.getValue();
                                }
                            }
                            throw new CommandError("NOT_FOUND", "no label button '" + a.str("label") + "'");
                        } catch (Exception e) {
                            throw CommandError.wrap("ERROR_CLICK_LABEL", e);
                        }
                    }
                    throw new CommandError("WRONG_GUI", "the label gun GUI is not open");

                case "look":
                    if (mc.thePlayer == null) throw CommandError.of("ERROR: Player is null");
                    // look {yaw, [pitch]}
                    mc.thePlayer.rotationYaw = (float) a.d("yaw");
                    mc.thePlayer.rotationPitch = (float) a.d("pitch", mc.thePlayer.rotationPitch);
                    return "LOOK_SET: yaw=" + mc.thePlayer.rotationYaw + " pitch=" + mc.thePlayer.rotationPitch;

                case "teleport":
                    if (mc.thePlayer == null) throw CommandError.of("ERROR: Player is null");
                    // teleport {x, y, z}
                    double tx = a.d("x");
                    double ty = a.d("y");
                    double tz = a.d("z");
                    mc.thePlayer.setPositionAndUpdate(tx, ty, tz);
                    return "TELEPORTED: " + tx + ", " + ty + ", " + tz;

                case "rightclickblock":
                    if (mc.thePlayer == null) throw CommandError.of("ERROR: Player is null");
                    // rightclickblock {x, y, z, [side]}
                    int rx = a.i("x");
                    int ry = a.i("y");
                    int rz = a.i("z");
                    int rside = a.i("side", 1);
                    boolean rsuccess = mc.playerController.onPlayerRightClick(
                        mc.thePlayer,
                        mc.theWorld,
                        mc.thePlayer.getHeldItem(),
                        rx,
                        ry,
                        rz,
                        rside,
                        net.minecraft.util.Vec3.createVectorHelper(rx + 0.5, ry + 0.5, rz + 0.5));
                    return "RIGHT_CLICK_BLOCK: " + rx + "," + ry + "," + rz + " (side " + rside + ") -> " + rsuccess;

                case "leftclickblock":
                    if (mc.thePlayer == null) throw CommandError.of("ERROR: Player is null");
                    // leftclickblock {x, y, z, [side]}
                    int lx = a.i("x");
                    int ly = a.i("y");
                    int lz = a.i("z");
                    int lside = a.i("side", 1);
                    mc.playerController.clickBlock(lx, ly, lz, lside);
                    return "LEFT_CLICK_BLOCK: " + lx + "," + ly + "," + lz + " (side " + lside + ")";

                case "screenshot":
                    if (mc.guiAchievement != null) {
                        mc.guiAchievement.func_146257_b();
                    }
                    // screenshot {[path]}
                    String destPath = a.str("path", "");
                    File dest = destPath.isEmpty() ? new File(mc.mcDataDir, "screenshots/test.png")
                        : new File(destPath);
                    if (dest.getParentFile() != null) dest.getParentFile()
                        .mkdirs();
                    net.minecraft.util.ScreenShotHelper.saveScreenshot(
                        mc.mcDataDir,
                        dest.getName(),
                        mc.displayWidth,
                        mc.displayHeight,
                        mc.getFramebuffer());
                    File created = new File(new File(mc.mcDataDir, "screenshots"), dest.getName());
                    if (created.exists() && !created.getAbsolutePath()
                        .equals(dest.getAbsolutePath())) {
                        try {
                            java.nio.file.Files.move(
                                created.toPath(),
                                dest.toPath(),
                                java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                        } catch (Exception e) {
                            throw CommandError.wrap("ERROR_MOVE", e);
                        }
                    }
                    return "SCREENSHOT_SAVED: " + dest.getAbsolutePath();

                default:
                    throw new CommandError("UNKNOWN_COMMAND", "no command '" + cmd + "'");
            }
        });

        try {
            return future.get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw CommandError.wrap("TIMEOUT_OR_ERROR", e);
        }
    }

    private CompletableFuture<String> executeOnClient(Callable<String> callable) {
        TaskEntry entry = new TaskEntry(callable);
        taskQueue.add(entry);
        return entry.future;
    }

    private void launchWorld(String worldName) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.getSaveLoader() != null && mc.getSaveLoader()
            .canLoadWorld(worldName)) {
            mc.launchIntegratedServer(worldName, worldName, (WorldSettings) null);
        } else {
            WorldSettings settings = new WorldSettings(
                0L,
                WorldSettings.GameType.CREATIVE,
                false,
                false,
                WorldType.FLAT);
            settings.enableCommands();
            mc.launchIntegratedServer(worldName, worldName, settings);
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getMinecraft();

        TaskEntry entry;
        while ((entry = taskQueue.poll()) != null) {
            try {
                String result = entry.callable.call();
                entry.future.complete(result);
            } catch (Throwable t) {
                entry.future.completeExceptionally(t);
            }
        }

        // Permanently suppress achievement / tutorial toasts (e.g. "Press E to open your inventory")
        // to guarantee deterministic and reproducible UI testing / screenshots without toast overlays.
        if (mc.guiAchievement != null
            && (Boolean.getBoolean("sfm.testHarness") || Boolean.getBoolean("gtnh.testHarness"))) {
            mc.guiAchievement.func_146257_b();
        }

        String autoWorld = System.getProperty("gtnh.autoworld");
        if (autoWorld != null && !autoWorldTriggered && !"false".equalsIgnoreCase(autoWorld)) {
            if (mc.currentScreen instanceof GuiMainMenu) {
                ticksInMainMenu++;
                if (ticksInMainMenu > 20) {
                    autoWorldTriggered = true;
                    String worldName = "true".equalsIgnoreCase(autoWorld) ? "TestWorld" : autoWorld;
                    SuperFactoryManager.LOG.info("[TestAutomation] Auto-loading world: " + worldName);
                    launchWorld(worldName);
                }
            }
        }
    }
}
