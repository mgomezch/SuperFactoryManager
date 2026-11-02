// Generated from src/main/resources/assets/superfactorymanager/grammar/SFML.g4 by ANTLR 4.13.2

package ca.teamdman.langs;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SFMLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
public interface SFMLVisitor<T> extends ParseTreeVisitor<T> {

    /**
     * Visit a parse tree produced by {@link SFMLParser#program}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitProgram(SFMLParser.ProgramContext ctx);

    /**
     * Visit a parse tree produced by {@link SFMLParser#name}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitName(SFMLParser.NameContext ctx);

    /**
     * Visit a parse tree produced by the {@code TimerTrigger}
     * labeled alternative in {@link SFMLParser#trigger}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTimerTrigger(SFMLParser.TimerTriggerContext ctx);

    /**
     * Visit a parse tree produced by the {@code PulseTrigger}
     * labeled alternative in {@link SFMLParser#trigger}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPulseTrigger(SFMLParser.PulseTriggerContext ctx);

    /**
     * Visit a parse tree produced by the {@code IntervalSpace}
     * labeled alternative in {@link SFMLParser#interval}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIntervalSpace(SFMLParser.IntervalSpaceContext ctx);

    /**
     * Visit a parse tree produced by the {@code IntervalNoSpace}
     * labeled alternative in {@link SFMLParser#interval}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIntervalNoSpace(SFMLParser.IntervalNoSpaceContext ctx);

    /**
     * Visit a parse tree produced by {@link SFMLParser#block}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBlock(SFMLParser.BlockContext ctx);

    /**
     * Visit a parse tree produced by {@link SFMLParser#statement}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitStatement(SFMLParser.StatementContext ctx);

    /**
     * Visit a parse tree produced by {@link SFMLParser#redstoneOutputStatement}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitRedstoneOutputStatement(SFMLParser.RedstoneOutputStatementContext ctx);

    /**
     * Visit a parse tree produced by {@link SFMLParser#forgetStatement}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitForgetStatement(SFMLParser.ForgetStatementContext ctx);

    /**
     * Visit a parse tree produced by {@link SFMLParser#inputStatement}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInputStatement(SFMLParser.InputStatementContext ctx);

    /**
     * Visit a parse tree produced by {@link SFMLParser#outputStatement}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitOutputStatement(SFMLParser.OutputStatementContext ctx);

    /**
     * Visit a parse tree produced by {@link SFMLParser#inputResourceLimits}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInputResourceLimits(SFMLParser.InputResourceLimitsContext ctx);

    /**
     * Visit a parse tree produced by {@link SFMLParser#outputResourceLimits}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitOutputResourceLimits(SFMLParser.OutputResourceLimitsContext ctx);

    /**
     * Visit a parse tree produced by {@link SFMLParser#resourceLimitList}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitResourceLimitList(SFMLParser.ResourceLimitListContext ctx);

    /**
     * Visit a parse tree produced by {@link SFMLParser#resourceLimit}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitResourceLimit(SFMLParser.ResourceLimitContext ctx);

    /**
     * Visit a parse tree produced by the {@code QuantityRetentionLimit}
     * labeled alternative in {@link SFMLParser#limit}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitQuantityRetentionLimit(SFMLParser.QuantityRetentionLimitContext ctx);

    /**
     * Visit a parse tree produced by the {@code RetentionLimit}
     * labeled alternative in {@link SFMLParser#limit}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitRetentionLimit(SFMLParser.RetentionLimitContext ctx);

    /**
     * Visit a parse tree produced by the {@code QuantityLimit}
     * labeled alternative in {@link SFMLParser#limit}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitQuantityLimit(SFMLParser.QuantityLimitContext ctx);

    /**
     * Visit a parse tree produced by {@link SFMLParser#quantity}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitQuantity(SFMLParser.QuantityContext ctx);

    /**
     * Visit a parse tree produced by {@link SFMLParser#retention}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitRetention(SFMLParser.RetentionContext ctx);

    /**
     * Visit a parse tree produced by {@link SFMLParser#resourceExclusion}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitResourceExclusion(SFMLParser.ResourceExclusionContext ctx);

    /**
     * Visit a parse tree produced by the {@code Resource}
     * labeled alternative in {@link SFMLParser#resourceId}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitResource(SFMLParser.ResourceContext ctx);

    /**
     * Visit a parse tree produced by the {@code StringResource}
     * labeled alternative in {@link SFMLParser#resourceId}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitStringResource(SFMLParser.StringResourceContext ctx);

    /**
     * Visit a parse tree produced by {@link SFMLParser#resourceIdList}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitResourceIdList(SFMLParser.ResourceIdListContext ctx);

    /**
     * Visit a parse tree produced by {@link SFMLParser#resourceIdDisjunction}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitResourceIdDisjunction(SFMLParser.ResourceIdDisjunctionContext ctx);

    /**
     * Visit a parse tree produced by {@link SFMLParser#with}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitWith(SFMLParser.WithContext ctx);

    /**
     * Visit a parse tree produced by the {@code WithConjunction}
     * labeled alternative in {@link SFMLParser#withClause}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitWithConjunction(SFMLParser.WithConjunctionContext ctx);

    /**
     * Visit a parse tree produced by the {@code WithParen}
     * labeled alternative in {@link SFMLParser#withClause}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitWithParen(SFMLParser.WithParenContext ctx);

    /**
     * Visit a parse tree produced by the {@code WithNegation}
     * labeled alternative in {@link SFMLParser#withClause}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitWithNegation(SFMLParser.WithNegationContext ctx);

    /**
     * Visit a parse tree produced by the {@code WithTag}
     * labeled alternative in {@link SFMLParser#withClause}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitWithTag(SFMLParser.WithTagContext ctx);

    /**
     * Visit a parse tree produced by the {@code WithDisjunction}
     * labeled alternative in {@link SFMLParser#withClause}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitWithDisjunction(SFMLParser.WithDisjunctionContext ctx);

    /**
     * Visit a parse tree produced by {@link SFMLParser#tagMatcher}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTagMatcher(SFMLParser.TagMatcherContext ctx);

    /**
     * Visit a parse tree produced by the {@code EachSide}
     * labeled alternative in {@link SFMLParser#sidequalifier}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitEachSide(SFMLParser.EachSideContext ctx);

    /**
     * Visit a parse tree produced by the {@code ListedSides}
     * labeled alternative in {@link SFMLParser#sidequalifier}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitListedSides(SFMLParser.ListedSidesContext ctx);

    /**
     * Visit a parse tree produced by {@link SFMLParser#side}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSide(SFMLParser.SideContext ctx);

    /**
     * Visit a parse tree produced by {@link SFMLParser#slotqualifier}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSlotqualifier(SFMLParser.SlotqualifierContext ctx);

    /**
     * Visit a parse tree produced by {@link SFMLParser#rangeset}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitRangeset(SFMLParser.RangesetContext ctx);

    /**
     * Visit a parse tree produced by {@link SFMLParser#range}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitRange(SFMLParser.RangeContext ctx);

    /**
     * Visit a parse tree produced by {@link SFMLParser#ifStatement}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIfStatement(SFMLParser.IfStatementContext ctx);

    /**
     * Visit a parse tree produced by the {@code BooleanHas}
     * labeled alternative in {@link SFMLParser#boolexpr}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBooleanHas(SFMLParser.BooleanHasContext ctx);

    /**
     * Visit a parse tree produced by the {@code BooleanConjunction}
     * labeled alternative in {@link SFMLParser#boolexpr}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBooleanConjunction(SFMLParser.BooleanConjunctionContext ctx);

    /**
     * Visit a parse tree produced by the {@code BooleanRedstone}
     * labeled alternative in {@link SFMLParser#boolexpr}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBooleanRedstone(SFMLParser.BooleanRedstoneContext ctx);

    /**
     * Visit a parse tree produced by the {@code BooleanDisjunction}
     * labeled alternative in {@link SFMLParser#boolexpr}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBooleanDisjunction(SFMLParser.BooleanDisjunctionContext ctx);

    /**
     * Visit a parse tree produced by the {@code BooleanFalse}
     * labeled alternative in {@link SFMLParser#boolexpr}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBooleanFalse(SFMLParser.BooleanFalseContext ctx);

    /**
     * Visit a parse tree produced by the {@code BooleanParen}
     * labeled alternative in {@link SFMLParser#boolexpr}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBooleanParen(SFMLParser.BooleanParenContext ctx);

    /**
     * Visit a parse tree produced by the {@code BooleanNegation}
     * labeled alternative in {@link SFMLParser#boolexpr}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBooleanNegation(SFMLParser.BooleanNegationContext ctx);

    /**
     * Visit a parse tree produced by the {@code BooleanTrue}
     * labeled alternative in {@link SFMLParser#boolexpr}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBooleanTrue(SFMLParser.BooleanTrueContext ctx);

    /**
     * Visit a parse tree produced by the {@code BooleanLabelledRedstone}
     * labeled alternative in {@link SFMLParser#boolexpr}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBooleanLabelledRedstone(SFMLParser.BooleanLabelledRedstoneContext ctx);

    /**
     * Visit a parse tree produced by {@link SFMLParser#comparisonOp}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitComparisonOp(SFMLParser.ComparisonOpContext ctx);

    /**
     * Visit a parse tree produced by {@link SFMLParser#setOp}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSetOp(SFMLParser.SetOpContext ctx);

    /**
     * Visit a parse tree produced by {@link SFMLParser#labelAccess}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitLabelAccess(SFMLParser.LabelAccessContext ctx);

    /**
     * Visit a parse tree produced by {@link SFMLParser#roundrobin}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitRoundrobin(SFMLParser.RoundrobinContext ctx);

    /**
     * Visit a parse tree produced by the {@code RawLabel}
     * labeled alternative in {@link SFMLParser#label}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitRawLabel(SFMLParser.RawLabelContext ctx);

    /**
     * Visit a parse tree produced by the {@code StringLabel}
     * labeled alternative in {@link SFMLParser#label}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitStringLabel(SFMLParser.StringLabelContext ctx);

    /**
     * Visit a parse tree produced by {@link SFMLParser#emptyslots}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitEmptyslots(SFMLParser.EmptyslotsContext ctx);

    /**
     * Visit a parse tree produced by {@link SFMLParser#identifier}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIdentifier(SFMLParser.IdentifierContext ctx);

    /**
     * Visit a parse tree produced by {@link SFMLParser#string}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitString(SFMLParser.StringContext ctx);

    /**
     * Visit a parse tree produced by {@link SFMLParser#number}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNumber(SFMLParser.NumberContext ctx);
}
