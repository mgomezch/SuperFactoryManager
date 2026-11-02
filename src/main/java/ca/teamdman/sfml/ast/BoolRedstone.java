package ca.teamdman.sfml.ast;

import java.util.ArrayList;
import java.util.List;

import ca.teamdman.sfm.common.program.ProgramContext;
import ca.teamdman.sfm.common.util.BlockPos;

public final class BoolRedstone implements BoolExpr {

    private final ComparisonOperator comparison;
    private final int targetValue;
    // null for the unlabelled form, which reads the manager's own input and the network's receivers
    private final LabelAccess labelAccess;
    private final SetOperator setOperator;

    public BoolRedstone(ComparisonOperator comparison, int targetValue) {
        this(null, null, comparison, targetValue);
    }

    public BoolRedstone(SetOperator setOperator, LabelAccess labelAccess, ComparisonOperator comparison,
        int targetValue) {
        this.setOperator = setOperator != null ? setOperator : SetOperator.OVERALL;
        this.labelAccess = labelAccess;
        this.comparison = comparison != null ? comparison : ComparisonOperator.GREATER;
        this.targetValue = targetValue;
    }

    @Override
    public boolean evaluate(ProgramContext context) {
        if (labelAccess == null) {
            int currentSignal = context.getRedstonePower();
            return comparison.test(currentSignal, targetValue);
        }

        List<BlockPos> positions = labelAccess.getPositions(context.getLabelPositionHolder());
        List<Integer> levels = new ArrayList<>(positions.size());
        for (BlockPos pos : positions) {
            levels.add(context.getRedstonePower(pos));
        }
        return test(levels);
    }

    /**
     * Applies the comparison and set operator to the redstone levels read from each labelled block.
     * OVERALL compares the strongest level; the other set operators compare each block's level on its own.
     */
    public boolean test(List<Integer> levels) {
        int max = 0;
        List<Boolean> satisfactionResults = new ArrayList<>(levels.size());
        for (int level : levels) {
            if (level > max) max = level;
            satisfactionResults.add(comparison.test(level, targetValue));
        }
        boolean overallSatisfied = comparison.test(max, targetValue);
        return setOperator.test(overallSatisfied, satisfactionResults);
    }

    public ComparisonOperator getComparison() {
        return comparison;
    }

    public int getTargetValue() {
        return targetValue;
    }

    public LabelAccess getLabelAccess() {
        return labelAccess;
    }

    public SetOperator getSetOperator() {
        return setOperator;
    }

    @Override
    public String toString() {
        if (labelAccess == null) {
            return "REDSTONE " + comparison + " " + targetValue;
        }
        return setOperator + " " + labelAccess + " REDSTONE " + comparison + " " + targetValue;
    }
}
