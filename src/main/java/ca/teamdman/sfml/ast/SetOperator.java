package ca.teamdman.sfml.ast;

import java.util.List;
import java.util.Locale;

public enum SetOperator implements ASTNode {

    OVERALL,
    SOME,
    EVERY,
    ONE,
    LONE;

    public boolean test(boolean overallMatches, List<Boolean> individualMatches) {
        switch (this) {
            case OVERALL:
                return overallMatches;
            case SOME:
                for (boolean b : individualMatches) {
                    if (b) return true;
                }
                return false;
            case EVERY:
                if (individualMatches.isEmpty()) return false;
                for (boolean b : individualMatches) {
                    if (!b) return false;
                }
                return true;
            case ONE:
                int count = 0;
                for (boolean b : individualMatches) {
                    if (b) count++;
                }
                return count == 1;
            case LONE:
                int countLone = 0;
                for (boolean b : individualMatches) {
                    if (b) countLone++;
                }
                return countLone <= 1;
            default:
                return overallMatches;
        }
    }

    public static SetOperator from(String text) {
        if (text == null) return OVERALL;
        String upper = text.toUpperCase(Locale.ROOT);
        if (upper.equals("EACH")) {
            return EVERY;
        }
        if (upper.equals("ANY")) {
            return SOME;
        }
        try {
            return SetOperator.valueOf(upper);
        } catch (IllegalArgumentException e) {
            return OVERALL;
        }
    }
}
