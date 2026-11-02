package ca.teamdman.sfml.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.antlr.v4.runtime.tree.TerminalNode;

import ca.teamdman.langs.SFMLBaseVisitor;
import ca.teamdman.langs.SFMLParser;

public class ASTBuilder extends SFMLBaseVisitor<ASTNode> {

    private final Set<String> usedLabels = new HashSet<>();

    public Set<String> getUsedLabels() {
        return usedLabels;
    }

    @Override
    public ASTNode visitProgram(SFMLParser.ProgramContext ctx) {
        String name = "";
        if (ctx.name() != null && ctx.name()
            .string() != null) {
            String raw = ctx.name()
                .string()
                .getText();
            if (raw.length() >= 2 && raw.startsWith("\"") && raw.endsWith("\"")) {
                name = raw.substring(1, raw.length() - 1);
            } else {
                name = raw;
            }
        }

        List<Trigger> triggers = new ArrayList<>();
        if (ctx.trigger() != null) {
            for (SFMLParser.TriggerContext tCtx : ctx.trigger()) {
                ASTNode node = visit(tCtx);
                if (node instanceof Trigger) {
                    triggers.add((Trigger) node);
                }
            }
        }

        return new Program(name, triggers, usedLabels);
    }

    @Override
    public ASTNode visitTimerTrigger(SFMLParser.TimerTriggerContext ctx) {
        Interval interval = (Interval) visit(ctx.interval());
        Block block = visitBlock(ctx.block());
        return new TimerTrigger(interval, block);
    }

    @Override
    public ASTNode visitPulseTrigger(SFMLParser.PulseTriggerContext ctx) {
        Block block = visitBlock(ctx.block());
        return new PulseTrigger(block);
    }

    @Override
    public ASTNode visitIntervalSpace(SFMLParser.IntervalSpaceContext ctx) {
        TerminalNode firstNumNode = ctx.NUMBER(0);
        int ticks = 1;
        if (firstNumNode != null) {
            try {
                ticks = Integer.parseInt(firstNumNode.getText());
            } catch (NumberFormatException ignored) {}
        }
        if (ctx.SECONDS() != null || ctx.SECOND() != null) {
            ticks *= 20;
        }

        boolean isGlobal = ctx.GLOBAL() != null;
        int offset = 0;
        TerminalNode secondNumNode = ctx.NUMBER(1);
        if (secondNumNode != null) {
            try {
                offset = Integer.parseInt(secondNumNode.getText());
            } catch (NumberFormatException ignored) {}
            if (ctx.SECONDS() != null || ctx.SECOND() != null) {
                offset *= 20;
            }
        }

        return new Interval(ticks, isGlobal, offset);
    }

    @Override
    public ASTNode visitIntervalNoSpace(SFMLParser.IntervalNoSpaceContext ctx) {
        String first = ctx.NUMBER_WITH_G_SUFFIX()
            .getText();
        String digits = first.replaceAll("[^0-9]", "");
        int ticks = 1;
        try {
            ticks = Integer.parseInt(digits);
        } catch (NumberFormatException ignored) {}

        if (ctx.SECONDS() != null || ctx.SECOND() != null) {
            ticks *= 20;
        }

        int offset = 0;
        TerminalNode secondNumNode = ctx.NUMBER();
        if (secondNumNode != null) {
            try {
                offset = Integer.parseInt(secondNumNode.getText());
            } catch (NumberFormatException ignored) {}
            if (ctx.SECONDS() != null || ctx.SECOND() != null) {
                offset *= 20;
            }
        }

        return new Interval(ticks, true, offset);
    }

    public Block visitBlock(SFMLParser.BlockContext ctx) {
        if (ctx == null || ctx.statement() == null) {
            return new Block(Collections.emptyList());
        }
        List<Statement> statements = new ArrayList<>();
        for (SFMLParser.StatementContext sCtx : ctx.statement()) {
            ASTNode node = visit(sCtx);
            if (node instanceof Statement) {
                statements.add((Statement) node);
            }
        }
        return new Block(statements);
    }

    @Override
    public ASTNode visitInputStatement(SFMLParser.InputStatementContext ctx) {
        LabelAccess labelAccess = (LabelAccess) visit(ctx.labelAccess());
        ResourceLimits limits = ctx.inputResourceLimits() != null ? (ResourceLimits) visit(ctx.inputResourceLimits())
            : ResourceLimits.DEFAULT;
        if (ctx.resourceExclusion() != null) {
            ResourceIdSet exclusions = (ResourceIdSet) visit(ctx.resourceExclusion());
            limits = new ResourceLimits(limits.getLimits(), exclusions);
        }
        boolean each = ctx.EACH() != null;
        return new InputStatement(labelAccess, limits, each);
    }

    @Override
    public ASTNode visitRedstoneOutputStatement(SFMLParser.RedstoneOutputStatementContext ctx) {
        LabelAccess labelAccess = (LabelAccess) visit(ctx.labelAccess());
        int level = 15;
        boolean each = ctx.EACH() != null;
        if (ctx.quantity() != null && !ctx.quantity()
            .isEmpty()) {
            ResourceQuantity q = (ResourceQuantity) visit(ctx.quantity(0));
            if (q != null) {
                level = (int) Math.min(15, Math.max(0, q.getAmount()));
                if (q.isEach()) {
                    each = true;
                }
            }
        }
        return new RedstoneOutputStatement(labelAccess, level, each);
    }

    @Override
    public ASTNode visitOutputStatement(SFMLParser.OutputStatementContext ctx) {
        LabelAccess labelAccess = (LabelAccess) visit(ctx.labelAccess());
        ResourceLimits limits = ctx.outputResourceLimits() != null ? (ResourceLimits) visit(ctx.outputResourceLimits())
            : ResourceLimits.DEFAULT;
        if (ctx.resourceExclusion() != null) {
            ResourceIdSet exclusions = (ResourceIdSet) visit(ctx.resourceExclusion());
            limits = new ResourceLimits(limits.getLimits(), exclusions);
        }
        boolean each = ctx.EACH() != null;
        boolean emptySlotsOnly = ctx.emptyslots() != null;
        return new OutputStatement(labelAccess, limits, each, emptySlotsOnly);
    }

    @Override
    public ASTNode visitIfStatement(SFMLParser.IfStatementContext ctx) {
        List<SFMLParser.BoolexprContext> exprs = ctx.boolexpr();
        List<SFMLParser.BlockContext> blocks = ctx.block();

        // Build chained if-else blocks from back to front
        Block elseBlock = null;
        if (blocks.size() > exprs.size()) {
            elseBlock = visitBlock(blocks.get(blocks.size() - 1));
        }

        Statement currentIf = null;
        for (int i = exprs.size() - 1; i >= 0; i--) {
            BoolExpr condition = (BoolExpr) visit(exprs.get(i));
            Block trueBlock = visitBlock(blocks.get(i));
            Block falseBlock = currentIf != null ? new Block(Collections.singletonList(currentIf)) : elseBlock;
            currentIf = new IfStatement(condition, trueBlock, falseBlock);
        }

        return currentIf != null ? currentIf : new IfStatement(BoolFalse.INSTANCE, new Block(null), null);
    }

    @Override
    public ASTNode visitForgetStatement(SFMLParser.ForgetStatementContext ctx) {
        List<Label> labels = new ArrayList<>();
        if (ctx.label() != null) {
            for (SFMLParser.LabelContext lCtx : ctx.label()) {
                labels.add((Label) visit(lCtx));
            }
        }
        return new ForgetStatement(labels);
    }

    @Override
    public ASTNode visitLabelAccess(SFMLParser.LabelAccessContext ctx) {
        List<Label> labels = new ArrayList<>();
        for (SFMLParser.LabelContext lCtx : ctx.label()) {
            labels.add((Label) visit(lCtx));
        }

        SideQualifier sideQualifier = ctx.sidequalifier() != null ? (SideQualifier) visit(ctx.sidequalifier())
            : SideQualifier.ALL;
        NumberRangeSet slots = ctx.slotqualifier() != null ? (NumberRangeSet) visit(ctx.slotqualifier())
            : NumberRangeSet.ALL;
        RoundRobin roundRobin = ctx.roundrobin() != null ? (RoundRobin) visit(ctx.roundrobin()) : RoundRobin.DISABLED;

        return new LabelAccess(labels, sideQualifier, slots, roundRobin);
    }

    @Override
    public ASTNode visitRawLabel(SFMLParser.RawLabelContext ctx) {
        String name = ctx.getText();
        usedLabels.add(name);
        return new Label(name);
    }

    @Override
    public ASTNode visitStringLabel(SFMLParser.StringLabelContext ctx) {
        String raw = ctx.string()
            .getText();
        String name = raw.length() >= 2 ? raw.substring(1, raw.length() - 1) : raw;
        usedLabels.add(name);
        return new Label(name);
    }

    @Override
    public ASTNode visitEachSide(SFMLParser.EachSideContext ctx) {
        return SideQualifier.ALL;
    }

    @Override
    public ASTNode visitListedSides(SFMLParser.ListedSidesContext ctx) {
        List<Side> sides = new ArrayList<>();
        for (SFMLParser.SideContext sCtx : ctx.side()) {
            sides.add(Side.from(sCtx.getText()));
        }
        return new SideQualifier(sides);
    }

    @Override
    public ASTNode visitSlotqualifier(SFMLParser.SlotqualifierContext ctx) {
        return visit(ctx.rangeset());
    }

    @Override
    public ASTNode visitRangeset(SFMLParser.RangesetContext ctx) {
        List<NumberRange> ranges = new ArrayList<>();
        for (SFMLParser.RangeContext rCtx : ctx.range()) {
            ranges.add((NumberRange) visit(rCtx));
        }
        return new NumberRangeSet(ranges);
    }

    @Override
    public ASTNode visitRange(SFMLParser.RangeContext ctx) {
        List<SFMLParser.NumberContext> nums = ctx.number();
        int min = Integer.parseInt(
            nums.get(0)
                .getText());
        int max = nums.size() > 1 ? Integer.parseInt(
            nums.get(1)
                .getText())
            : min;
        return new NumberRange(min, max);
    }

    @Override
    public ASTNode visitRoundrobin(SFMLParser.RoundrobinContext ctx) {
        if (ctx.BLOCK() != null) {
            return new RoundRobin(RoundRobin.Mode.BY_BLOCK);
        }
        return new RoundRobin(RoundRobin.Mode.BY_LABEL);
    }

    @Override
    public ASTNode visitResourceLimitList(SFMLParser.ResourceLimitListContext ctx) {
        List<ResourceLimit> list = new ArrayList<>();
        for (SFMLParser.ResourceLimitContext rlCtx : ctx.resourceLimit()) {
            list.add((ResourceLimit) visit(rlCtx));
        }
        return new ResourceLimits(list, null);
    }

    @Override
    public ASTNode visitResourceLimit(SFMLParser.ResourceLimitContext ctx) {
        Limit limit = ctx.limit() != null ? (Limit) visit(ctx.limit()) : Limit.UNLIMITED;
        ResourceIdSet resourceIds = ctx.resourceIdDisjunction() != null
            ? (ResourceIdSet) visit(ctx.resourceIdDisjunction())
            : ResourceIdSet.MATCH_ALL;
        With with = ctx.with() != null ? (With) visit(ctx.with()) : With.ALWAYS_TRUE;

        return new ResourceLimit(resourceIds, limit, with);
    }

    @Override
    public ASTNode visitQuantityRetentionLimit(SFMLParser.QuantityRetentionLimitContext ctx) {
        ResourceQuantity quantity = (ResourceQuantity) visit(ctx.quantity());
        ResourceQuantity retention = (ResourceQuantity) visit(ctx.retention());
        return new Limit(quantity, retention);
    }

    @Override
    public ASTNode visitRetentionLimit(SFMLParser.RetentionLimitContext ctx) {
        ResourceQuantity retention = (ResourceQuantity) visit(ctx.retention());
        return new Limit(ResourceQuantity.UNLIMITED, retention);
    }

    @Override
    public ASTNode visitQuantityLimit(SFMLParser.QuantityLimitContext ctx) {
        ResourceQuantity quantity = (ResourceQuantity) visit(ctx.quantity());
        return new Limit(quantity, ResourceQuantity.ZERO);
    }

    @Override
    public ASTNode visitQuantity(SFMLParser.QuantityContext ctx) {
        long amount = Long.parseLong(
            ctx.number()
                .getText());
        boolean each = ctx.EACH() != null;
        return new ResourceQuantity(amount, each);
    }

    @Override
    public ASTNode visitRetention(SFMLParser.RetentionContext ctx) {
        long amount = Long.parseLong(
            ctx.number()
                .getText());
        boolean each = ctx.EACH() != null;
        return new ResourceQuantity(amount, each);
    }

    @Override
    public ASTNode visitResourceExclusion(SFMLParser.ResourceExclusionContext ctx) {
        if (ctx == null) return null;
        return visit(ctx.resourceIdList());
    }

    @Override
    public ASTNode visitResourceIdList(SFMLParser.ResourceIdListContext ctx) {
        List<ResourceIdentifier> ids = new ArrayList<>();
        for (SFMLParser.ResourceIdContext rCtx : ctx.resourceId()) {
            ids.add((ResourceIdentifier) visit(rCtx));
        }
        return new ResourceIdSet(ids);
    }

    @Override
    public ASTNode visitResourceIdDisjunction(SFMLParser.ResourceIdDisjunctionContext ctx) {
        List<ResourceIdentifier> ids = new ArrayList<>();
        for (SFMLParser.ResourceIdContext rCtx : ctx.resourceId()) {
            ids.add((ResourceIdentifier) visit(rCtx));
        }
        return new ResourceIdSet(ids);
    }

    @Override
    public ASTNode visitResource(SFMLParser.ResourceContext ctx) {
        return ResourceIdentifier.fromString(ctx.getText());
    }

    @Override
    public ASTNode visitStringResource(SFMLParser.StringResourceContext ctx) {
        String raw = ctx.string()
            .getText();
        String text = raw.length() >= 2 ? raw.substring(1, raw.length() - 1) : raw;
        return ResourceIdentifier.fromString(text);
    }

    @Override
    public ASTNode visitWith(SFMLParser.WithContext ctx) {
        boolean negated = ctx.WITHOUT() != null;
        TagMatcher matcher = extractTagMatcher(ctx.withClause());
        return new With(matcher, negated);
    }

    private TagMatcher extractTagMatcher(SFMLParser.WithClauseContext ctx) {
        if (ctx instanceof SFMLParser.WithTagContext) {
            SFMLParser.TagMatcherContext tmCtx = ((SFMLParser.WithTagContext) ctx).tagMatcher();
            return new TagMatcher(tmCtx.getText());
        }
        return null;
    }

    @Override
    public ASTNode visitBooleanTrue(SFMLParser.BooleanTrueContext ctx) {
        return BoolTrue.INSTANCE;
    }

    @Override
    public ASTNode visitBooleanFalse(SFMLParser.BooleanFalseContext ctx) {
        return BoolFalse.INSTANCE;
    }

    @Override
    public ASTNode visitBooleanParen(SFMLParser.BooleanParenContext ctx) {
        return new BoolParen((BoolExpr) visit(ctx.boolexpr()));
    }

    @Override
    public ASTNode visitBooleanNegation(SFMLParser.BooleanNegationContext ctx) {
        return new BoolNegation((BoolExpr) visit(ctx.boolexpr()));
    }

    @Override
    public ASTNode visitBooleanConjunction(SFMLParser.BooleanConjunctionContext ctx) {
        return new BoolConjunction((BoolExpr) visit(ctx.boolexpr(0)), (BoolExpr) visit(ctx.boolexpr(1)));
    }

    @Override
    public ASTNode visitBooleanDisjunction(SFMLParser.BooleanDisjunctionContext ctx) {
        return new BoolDisjunction((BoolExpr) visit(ctx.boolexpr(0)), (BoolExpr) visit(ctx.boolexpr(1)));
    }

    @Override
    public ASTNode visitBooleanRedstone(SFMLParser.BooleanRedstoneContext ctx) {
        ComparisonOperator op = ctx.comparisonOp() != null ? ComparisonOperator.from(
            ctx.comparisonOp()
                .getText())
            : ComparisonOperator.GREATER;
        int val = ctx.number() != null ? Integer.parseInt(
            ctx.number()
                .getText())
            : 0;
        return new BoolRedstone(op, val);
    }

    @Override
    public ASTNode visitBooleanLabelledRedstone(SFMLParser.BooleanLabelledRedstoneContext ctx) {
        SetOperator setOp = ctx.setOp() != null ? SetOperator.from(
            ctx.setOp()
                .getText())
            : SetOperator.OVERALL;
        LabelAccess labelAccess = (LabelAccess) visit(ctx.labelAccess());
        ComparisonOperator op = ctx.comparisonOp() != null ? ComparisonOperator.from(
            ctx.comparisonOp()
                .getText())
            : ComparisonOperator.GREATER;
        int val = ctx.number() != null ? Integer.parseInt(
            ctx.number()
                .getText())
            : 0;
        return new BoolRedstone(setOp, labelAccess, op, val);
    }

    @Override
    public ASTNode visitBooleanHas(SFMLParser.BooleanHasContext ctx) {
        SetOperator setOp = ctx.setOp() != null ? SetOperator.from(
            ctx.setOp()
                .getText())
            : SetOperator.OVERALL;
        LabelAccess labelAccess = (LabelAccess) visit(ctx.labelAccess());
        ComparisonOperator compOp = ComparisonOperator.from(
            ctx.comparisonOp()
                .getText());
        long quantity = Long.parseLong(
            ctx.number()
                .getText());
        ResourceIdSet resIds = ctx.resourceIdDisjunction() != null ? (ResourceIdSet) visit(ctx.resourceIdDisjunction())
            : ResourceIdSet.MATCH_ALL;
        With with = ctx.with() != null ? (With) visit(ctx.with()) : With.ALWAYS_TRUE;
        ResourceIdSet except = ctx.resourceIdList() != null ? (ResourceIdSet) visit(ctx.resourceIdList()) : null;

        return new BoolHas(setOp, labelAccess, compOp, quantity, resIds, with, except);
    }
}
