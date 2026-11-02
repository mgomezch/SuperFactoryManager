package ca.teamdman.sfm.client.gui.syntax;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public final class SyntaxRegistry {

    private static final List<SyntaxSnippet> ALL_SNIPPETS = new ArrayList<>();
    private static final Map<SnippetCategory, List<SyntaxSnippet>> CATEGORY_MAP = new LinkedHashMap<>();

    static {
        for (SnippetCategory cat : SnippetCategory.values()) {
            CATEGORY_MAP.put(cat, new ArrayList<>());
        }

        // --- TRIGGERS ---
        register(
            new SyntaxSnippet(
                "trig_timer",
                "EVERY <20> TICKS DO ... END",
                SnippetCategory.TRIGGER,
                "EVERY <20> TICKS DO\n  <statements>\nEND",
                "Executes every N ticks (20 ticks = 1 second)"));

        register(
            new SyntaxSnippet(
                "trig_pulse",
                "EVERY REDSTONE PULSE DO ... END",
                SnippetCategory.TRIGGER,
                "EVERY REDSTONE PULSE DO\n  <statements>\nEND",
                "Executes when redstone input rises or changes"));

        // --- I/O TRANSFER ---
        register(
            new SyntaxSnippet(
                "io_basic",
                "INPUT FROM <src> / OUTPUT TO <dst>",
                SnippetCategory.IO,
                "INPUT FROM <src>\nOUTPUT TO <dst>",
                "Moves all items from source block to destination"));

        register(
            new SyntaxSnippet(
                "io_input_all",
                "INPUT FROM <src>",
                SnippetCategory.IO,
                "INPUT FROM <src>",
                "Pulls all items from labeled block into buffer"));

        register(
            new SyntaxSnippet(
                "io_input_qty",
                "INPUT <quantity> FROM <src>",
                SnippetCategory.IO,
                "INPUT <quantity> FROM <src>",
                "Pulls up to quantity items from source"));

        register(
            new SyntaxSnippet(
                "io_input_with",
                "INPUT <quantity> WITH <item> FROM <src>",
                SnippetCategory.IO,
                "INPUT <quantity> WITH <item> FROM <src>",
                "Pulls specific item and quantity into buffer"));

        register(
            new SyntaxSnippet(
                "io_input_retain",
                "INPUT <quantity> RETAIN <amount> FROM <src>",
                SnippetCategory.IO,
                "INPUT <quantity> RETAIN <amount> FROM <src>",
                "Transfers items while preserving a minimum count"));

        register(
            new SyntaxSnippet(
                "io_output_all",
                "OUTPUT TO <dst>",
                SnippetCategory.IO,
                "OUTPUT TO <dst>",
                "Transfers buffered items into destination block"));

        register(
            new SyntaxSnippet(
                "io_output_qty",
                "OUTPUT <quantity> TO <dst>",
                SnippetCategory.IO,
                "OUTPUT <quantity> TO <dst>",
                "Outputs up to quantity items to destination"));

        register(
            new SyntaxSnippet(
                "io_output_empty",
                "OUTPUT TO EMPTY SLOTS ONLY <dst>",
                SnippetCategory.IO,
                "OUTPUT TO EMPTY SLOTS ONLY <dst>",
                "Inserts only into unoccupied destination slots"));

        register(
            new SyntaxSnippet(
                "io_redstone_out",
                "OUTPUT <15> REDSTONE LEVEL TO <emit>",
                SnippetCategory.IO,
                "OUTPUT <15> REDSTONE LEVEL TO <emit>",
                "Emits analog redstone power (0-15) via cable emitter"));

        register(
            new SyntaxSnippet(
                "io_forget",
                "FORGET <label>",
                SnippetCategory.IO,
                "FORGET <label>",
                "Clears manager buffer tracking for labeled block"));

        // --- CONDITIONS ---
        register(
            new SyntaxSnippet(
                "cond_if",
                "IF <condition> THEN ... END",
                SnippetCategory.CONDITION,
                "IF <condition> THEN\n  <statements>\nEND",
                "Executes block only if condition evaluates true"));

        register(
            new SyntaxSnippet(
                "cond_if_else",
                "IF <condition> THEN ... ELSE ... END",
                SnippetCategory.CONDITION,
                "IF <condition> THEN\n  <statements>\nELSE\n  <fallback>\nEND",
                "Branching logic with true/false blocks"));

        register(
            new SyntaxSnippet(
                "cond_has",
                "<src> HAS GT <quantity> WITH <item>",
                SnippetCategory.CONDITION,
                "<src> HAS GT <quantity> WITH <item>",
                "Tests if container contents meet quantity threshold"));

        register(
            new SyntaxSnippet(
                "cond_redstone_on",
                "REDSTONE > 0",
                SnippetCategory.CONDITION,
                "REDSTONE > 0",
                "Tests if redstone receiver or manager is powered"));

        register(
            new SyntaxSnippet(
                "cond_redstone_level",
                "REDSTONE >= <level>",
                SnippetCategory.CONDITION,
                "REDSTONE >= <level>",
                "Tests if redstone signal level reaches threshold"));

        register(
            new SyntaxSnippet(
                "cond_redstone_label",
                "<rcv> REDSTONE LEVEL >= <level>",
                SnippetCategory.CONDITION,
                "<rcv> REDSTONE LEVEL >= <level>",
                "Tests the strongest signal into the labelled blocks"));

        register(
            new SyntaxSnippet(
                "cond_redstone_label_each",
                "EACH <rcv> REDSTONE LEVEL >= <level>",
                SnippetCategory.CONDITION,
                "EACH <rcv> REDSTONE LEVEL >= <level>",
                "Tests every labelled block's signal (ANY: at least one)"));

        register(
            new SyntaxSnippet(
                "cond_not",
                "NOT <condition>",
                SnippetCategory.CONDITION,
                "NOT <condition>",
                "Negates the inner condition"));

        // --- LABELS & SIDES ---
        register(
            new SyntaxSnippet(
                "label_each",
                "EACH <label>",
                SnippetCategory.LABEL,
                "EACH <label>",
                "Applies operation independently to each matching block"));

        register(
            new SyntaxSnippet(
                "side_top",
                "<label> TOP SIDE",
                SnippetCategory.LABEL,
                "<label> TOP SIDE",
                "Interacts strictly with top face of container"));

        register(
            new SyntaxSnippet(
                "side_bottom",
                "<label> BOTTOM SIDE",
                SnippetCategory.LABEL,
                "<label> BOTTOM SIDE",
                "Interacts strictly with bottom face of container"));

        register(
            new SyntaxSnippet(
                "side_north",
                "<label> NORTH SIDE",
                SnippetCategory.LABEL,
                "<label> NORTH SIDE",
                "Interacts strictly with north face of container"));

        register(
            new SyntaxSnippet(
                "side_south",
                "<label> SOUTH SIDE",
                SnippetCategory.LABEL,
                "<label> SOUTH SIDE",
                "Interacts strictly with south face of container"));

        register(
            new SyntaxSnippet(
                "side_east",
                "<label> EAST SIDE",
                SnippetCategory.LABEL,
                "<label> EAST SIDE",
                "Interacts strictly with east face of container"));

        register(
            new SyntaxSnippet(
                "side_west",
                "<label> WEST SIDE",
                SnippetCategory.LABEL,
                "<label> WEST SIDE",
                "Interacts strictly with west face of container"));

        // --- MODIFIERS ---
        register(
            new SyntaxSnippet(
                "mod_slots",
                "SLOTS <0..8>",
                SnippetCategory.MODIFIER,
                "SLOTS <0..8>",
                "Limits transfer to specified slot range"));

        register(
            new SyntaxSnippet(
                "mod_slot_single",
                "SLOT <0>",
                SnippetCategory.MODIFIER,
                "SLOT <0>",
                "Limits transfer to a single specific slot"));

        register(
            new SyntaxSnippet(
                "mod_retain",
                "RETAIN <amount>",
                SnippetCategory.MODIFIER,
                "RETAIN <amount>",
                "Retention minimum to guarantee items remain"));

        register(
            new SyntaxSnippet(
                "mod_with_tag",
                "WITH #<forge:ores>",
                SnippetCategory.MODIFIER,
                "WITH #<forge:ores>",
                "Filters items by OreDictionary / tag pattern"));

        register(
            new SyntaxSnippet(
                "mod_except",
                "EXCEPT <item>",
                SnippetCategory.MODIFIER,
                "EXCEPT <item>",
                "Excludes specific items from being transferred"));
    }

    private static void register(SyntaxSnippet snippet) {
        ALL_SNIPPETS.add(snippet);
        List<SyntaxSnippet> list = CATEGORY_MAP.get(snippet.getCategory());
        if (list != null) {
            list.add(snippet);
        }
    }

    public static List<SyntaxSnippet> getAllSnippets() {
        return Collections.unmodifiableList(ALL_SNIPPETS);
    }

    public static List<SyntaxSnippet> getSnippetsForCategory(SnippetCategory cat) {
        List<SyntaxSnippet> list = CATEGORY_MAP.get(cat);
        return list != null ? Collections.unmodifiableList(list) : Collections.emptyList();
    }

    /**
     * Dynamically generates label snippets from disk labels.
     */
    public static List<SyntaxSnippet> getLabelSnippets(Set<String> diskLabels) {
        List<SyntaxSnippet> result = new ArrayList<>();
        if (diskLabels != null && !diskLabels.isEmpty()) {
            for (String label : diskLabels) {
                result.add(
                    new SyntaxSnippet(
                        "label_" + label,
                        label,
                        SnippetCategory.LABEL,
                        label,
                        "Configured disk label targeting blocks in world"));
            }
        } else {
            result.add(
                new SyntaxSnippet(
                    "label_placeholder",
                    "<label>",
                    SnippetCategory.LABEL,
                    "<label>",
                    "Name of a labeled block in cable network"));
        }
        return result;
    }

    /**
     * Determines the most relevant snippets based on the program text and cursor position.
     */
    public static List<SyntaxSnippet> getContextualSuggestions(String fullText, int cursorLine, int cursorCol,
        Set<String> diskLabels) {
        List<SyntaxSnippet> suggestions = new ArrayList<>();

        String[] lines = fullText.split("\n", -1);
        String currentLine = (cursorLine >= 0 && cursorLine < lines.length) ? lines[cursorLine] : "";
        String textBeforeCursor = currentLine.substring(0, Math.min(cursorCol, currentLine.length()))
            .trim();
        String upperBefore = textBeforeCursor.toUpperCase(Locale.ROOT);

        // Check if inside a trigger DO ... END
        boolean insideTrigger = false;
        int triggerDepth = 0;
        for (int i = 0; i < cursorLine; i++) {
            String l = lines[i].toUpperCase(Locale.ROOT);
            if (l.contains("DO") && !l.startsWith("#") && !l.startsWith("//")) triggerDepth++;
            if (l.contains("END") && !l.startsWith("#") && !l.startsWith("//"))
                triggerDepth = Math.max(0, triggerDepth - 1);
        }
        insideTrigger = triggerDepth > 0;

        // Context 1: After FROM or TO -> Suggest Labels & Sides
        if (upperBefore.endsWith("FROM") || upperBefore.endsWith("TO")
            || upperBefore.contains("FROM ")
            || upperBefore.contains("TO ")) {
            suggestions.addAll(getLabelSnippets(diskLabels));
            suggestions.addAll(getSnippetsForCategory(SnippetCategory.LABEL));
            suggestions.addAll(getSnippetsForCategory(SnippetCategory.MODIFIER));
            return filterOrDeduplicate(suggestions, textBeforeCursor);
        }

        // Context 2: After IF or ELSE IF -> Suggest Conditions
        if (upperBefore.endsWith("IF") || upperBefore.endsWith("THEN") || upperBefore.contains("IF ")) {
            suggestions.addAll(getSnippetsForCategory(SnippetCategory.CONDITION));
            suggestions.addAll(getLabelSnippets(diskLabels));
            return filterOrDeduplicate(suggestions, textBeforeCursor);
        }

        // Context 3: Outside any trigger -> Prioritize Triggers
        if (!insideTrigger) {
            suggestions.addAll(getSnippetsForCategory(SnippetCategory.TRIGGER));
            suggestions.addAll(getSnippetsForCategory(SnippetCategory.IO));
            return filterOrDeduplicate(suggestions, textBeforeCursor);
        }

        // Context 4: Inside trigger -> Prioritize Statements (I/O, Conditions, Labels)
        suggestions.addAll(getSnippetsForCategory(SnippetCategory.IO));
        suggestions.addAll(getSnippetsForCategory(SnippetCategory.CONDITION));
        suggestions.addAll(getLabelSnippets(diskLabels));
        suggestions.addAll(getSnippetsForCategory(SnippetCategory.MODIFIER));
        suggestions.addAll(getSnippetsForCategory(SnippetCategory.TRIGGER));

        return filterOrDeduplicate(suggestions, textBeforeCursor);
    }

    private static List<SyntaxSnippet> filterOrDeduplicate(List<SyntaxSnippet> input, String textBeforeCursor) {
        String lastWord = getLastWord(textBeforeCursor).toUpperCase(Locale.ROOT);
        if (lastWord.isEmpty()) {
            return input;
        }

        List<SyntaxSnippet> filtered = new ArrayList<>();
        for (SyntaxSnippet s : input) {
            String titleUpper = s.getTitle()
                .toUpperCase(Locale.ROOT);
            String templUpper = s.getTemplate()
                .toUpperCase(Locale.ROOT);
            if (titleUpper.contains(lastWord) || templUpper.contains(lastWord)) {
                filtered.add(s);
            }
        }
        return filtered.isEmpty() ? input : filtered;
    }

    public static String getLastWord(String text) {
        if (text == null || text.isEmpty()) return "";
        int lastSpace = Math.max(text.lastIndexOf(' '), text.lastIndexOf('\t'));
        return lastSpace != -1 ? text.substring(lastSpace + 1) : text;
    }
}
