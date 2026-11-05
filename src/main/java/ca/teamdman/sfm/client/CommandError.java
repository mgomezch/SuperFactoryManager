package ca.teamdman.sfm.client;

/**
 * A command's failure, reported to the client as {@code {"ok": false, "error": {"code": ..., "message": ...}}}.
 */
final class CommandError extends RuntimeException {

    final String code;

    CommandError(String code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * Turns one of the controller's old {@code "CODE: message"} failure strings into an error: the code is what comes
     * before the first colon, or the whole text when it is a bare code.
     */
    static CommandError of(String text) {
        int colon = text.indexOf(':');
        String head = (colon < 0 ? text : text.substring(0, colon)).trim();
        if (!head.isEmpty() && head.matches("[A-Z0-9_]+")) {
            String rest = colon < 0 ? ""
                : text.substring(colon + 1)
                    .trim();
            return new CommandError(head, rest.isEmpty() ? head : rest);
        }
        return new CommandError("ERROR", text);
    }

    /**
     * The error for a failure caught while waiting on a task: the task's own {@code CommandError} when it threw one,
     * otherwise one with {@code code} describing the root cause.
     */
    static CommandError wrap(String code, Throwable t) {
        Throwable root = t;
        for (Throwable c = t; c != null; c = c.getCause()) {
            if (c instanceof CommandError ce) return ce;
            root = c;
        }
        if (root instanceof java.util.concurrent.TimeoutException) {
            return new CommandError("TIMEOUT", code + ": timed out");
        }
        String msg = root.getMessage();
        return new CommandError(
            code,
            root.getClass()
                .getSimpleName() + (msg == null ? "" : ": " + msg));
    }
}
