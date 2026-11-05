package ca.teamdman.sfm.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * A request's named arguments, read with the type each command expects. A missing required argument, or one of the
 * wrong type, fails the request with {@code BAD_ARGS} instead of being guessed at.
 */
final class Args {

    static final Args EMPTY = new Args(new JsonObject());

    private final JsonObject o;

    Args(JsonObject o) {
        this.o = o;
    }

    JsonObject raw() {
        return o;
    }

    boolean has(String k) {
        return o.has(k) && !o.get(k)
            .isJsonNull();
    }

    private JsonElement get(String k) {
        if (!has(k)) throw new CommandError("BAD_ARGS", "missing argument '" + k + "'");
        return o.get(k);
    }

    private JsonPrimitive prim(String k) {
        JsonElement e = get(k);
        if (!e.isJsonPrimitive()) throw new CommandError("BAD_ARGS", "argument '" + k + "' must be a scalar");
        return e.getAsJsonPrimitive();
    }

    String str(String k) {
        JsonPrimitive p = prim(k);
        if (!p.isString()) throw new CommandError("BAD_ARGS", "argument '" + k + "' must be a string");
        return p.getAsString();
    }

    String str(String k, String def) {
        return has(k) ? str(k) : def;
    }

    /** A string or a number, as text: for arguments such as a side, given as 0-5 or as a direction name. */
    String scalar(String k) {
        return prim(k).getAsString();
    }

    String scalar(String k, String def) {
        return has(k) ? scalar(k) : def;
    }

    private Number num(String k) {
        JsonPrimitive p = prim(k);
        if (!p.isNumber()) throw new CommandError("BAD_ARGS", "argument '" + k + "' must be a number");
        return p.getAsNumber();
    }

    int i(String k) {
        double d = num(k).doubleValue();
        if (d != Math.rint(d)) throw new CommandError("BAD_ARGS", "argument '" + k + "' must be an integer");
        return (int) d;
    }

    int i(String k, int def) {
        return has(k) ? i(k) : def;
    }

    long l(String k) {
        return num(k).longValue();
    }

    long l(String k, long def) {
        return has(k) ? l(k) : def;
    }

    double d(String k) {
        return num(k).doubleValue();
    }

    double d(String k, double def) {
        return has(k) ? d(k) : def;
    }

    boolean b(String k) {
        JsonPrimitive p = prim(k);
        if (!p.isBoolean()) throw new CommandError("BAD_ARGS", "argument '" + k + "' must be a boolean");
        return p.getAsBoolean();
    }

    boolean b(String k, boolean def) {
        return has(k) ? b(k) : def;
    }

    JsonObject obj(String k) {
        JsonElement e = get(k);
        if (!e.isJsonObject()) throw new CommandError("BAD_ARGS", "argument '" + k + "' must be an object");
        return e.getAsJsonObject();
    }

    JsonArray arr(String k) {
        JsonElement e = get(k);
        if (!e.isJsonArray()) throw new CommandError("BAD_ARGS", "argument '" + k + "' must be an array");
        return e.getAsJsonArray();
    }

    /** An {@code [x, y, z]} integer triple. */
    int[] pos(String k) {
        JsonArray a = arr(k);
        if (a.size() != 3) throw new CommandError("BAD_ARGS", "argument '" + k + "' must be [x, y, z]");
        int[] r = new int[3];
        for (int n = 0; n < 3; n++) {
            JsonElement e = a.get(n);
            if (!e.isJsonPrimitive() || !e.getAsJsonPrimitive()
                .isNumber()) throw new CommandError("BAD_ARGS", "argument '" + k + "' must be [x, y, z]");
            r[n] = e.getAsInt();
        }
        return r;
    }
}
