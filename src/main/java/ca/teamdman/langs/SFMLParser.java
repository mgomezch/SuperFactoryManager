// Generated from src/main/resources/assets/superfactorymanager/grammar/SFML.g4 by ANTLR 4.13.2

package ca.teamdman.langs;

import java.util.List;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;

@SuppressWarnings({ "all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape" })
public class SFMLParser extends Parser {

    static {
        RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION);
    }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache = new PredictionContextCache();
    public static final int IF = 1, THEN = 2, ELSE = 3, HAS = 4, OVERALL = 5, SOME = 6, ANY = 7, ONE = 8, LONE = 9,
        TRUE = 10, FALSE = 11, NOT = 12, AND = 13, OR = 14, GT = 15, GT_SYMBOL = 16, LT = 17, LT_SYMBOL = 18, EQ = 19,
        EQ_SYMBOL = 20, LE = 21, LE_SYMBOL = 22, GE = 23, GE_SYMBOL = 24, FROM = 25, TO = 26, INPUT = 27, OUTPUT = 28,
        WHERE = 29, SLOTS = 30, SLOT = 31, RETAIN = 32, EACH = 33, EXCEPT = 34, FORGET = 35, EMPTY = 36, IN = 37,
        WITHOUT = 38, WITH = 39, TAG = 40, HASHTAG = 41, ROUND = 42, ROBIN = 43, BY = 44, LABEL = 45, BLOCK = 46,
        TOP = 47, BOTTOM = 48, NORTH = 49, EAST = 50, SOUTH = 51, WEST = 52, SIDE = 53, LEFT = 54, RIGHT = 55,
        FRONT = 56, BACK = 57, NULL = 58, TICKS = 59, TICK = 60, SECONDS = 61, SECOND = 62, GLOBAL = 63, PLUS = 64,
        REDSTONE = 65, LEVEL = 66, LEVELS = 67, PULSE = 68, DO = 69, END = 70, NAME = 71, EVERY = 72, COMMA = 73,
        COLON = 74, SLASH = 75, DASH = 76, LPAREN = 77, RPAREN = 78, NUMBER_WITH_G_SUFFIX = 79, NUMBER = 80,
        IDENTIFIER = 81, STRING = 82, LINE_COMMENT = 83, WS = 84, UNUSED = 85;
    public static final int RULE_program = 0, RULE_name = 1, RULE_trigger = 2, RULE_interval = 3, RULE_block = 4,
        RULE_statement = 5, RULE_redstoneOutputStatement = 6, RULE_forgetStatement = 7, RULE_inputStatement = 8,
        RULE_outputStatement = 9, RULE_inputResourceLimits = 10, RULE_outputResourceLimits = 11,
        RULE_resourceLimitList = 12, RULE_resourceLimit = 13, RULE_limit = 14, RULE_quantity = 15, RULE_retention = 16,
        RULE_resourceExclusion = 17, RULE_resourceId = 18, RULE_resourceIdList = 19, RULE_resourceIdDisjunction = 20,
        RULE_with = 21, RULE_withClause = 22, RULE_tagMatcher = 23, RULE_sidequalifier = 24, RULE_side = 25,
        RULE_slotqualifier = 26, RULE_rangeset = 27, RULE_range = 28, RULE_ifStatement = 29, RULE_boolexpr = 30,
        RULE_comparisonOp = 31, RULE_setOp = 32, RULE_labelAccess = 33, RULE_roundrobin = 34, RULE_label = 35,
        RULE_emptyslots = 36, RULE_identifier = 37, RULE_string = 38, RULE_number = 39;

    private static String[] makeRuleNames() {
        return new String[] { "program", "name", "trigger", "interval", "block", "statement", "redstoneOutputStatement",
            "forgetStatement", "inputStatement", "outputStatement", "inputResourceLimits", "outputResourceLimits",
            "resourceLimitList", "resourceLimit", "limit", "quantity", "retention", "resourceExclusion", "resourceId",
            "resourceIdList", "resourceIdDisjunction", "with", "withClause", "tagMatcher", "sidequalifier", "side",
            "slotqualifier", "rangeset", "range", "ifStatement", "boolexpr", "comparisonOp", "setOp", "labelAccess",
            "roundrobin", "label", "emptyslots", "identifier", "string", "number" };
    }

    public static final String[] ruleNames = makeRuleNames();

    private static String[] makeLiteralNames() {
        return new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, "'>'", null, "'<'", null, "'='", null, "'<='", null, "'>='", null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, "'#'", null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, "','", "':'", "'/'", "'-'", "'('", "')'" };
    }

    private static final String[] _LITERAL_NAMES = makeLiteralNames();

    private static String[] makeSymbolicNames() {
        return new String[] { null, "IF", "THEN", "ELSE", "HAS", "OVERALL", "SOME", "ANY", "ONE", "LONE", "TRUE",
            "FALSE", "NOT", "AND", "OR", "GT", "GT_SYMBOL", "LT", "LT_SYMBOL", "EQ", "EQ_SYMBOL", "LE", "LE_SYMBOL",
            "GE", "GE_SYMBOL", "FROM", "TO", "INPUT", "OUTPUT", "WHERE", "SLOTS", "SLOT", "RETAIN", "EACH", "EXCEPT",
            "FORGET", "EMPTY", "IN", "WITHOUT", "WITH", "TAG", "HASHTAG", "ROUND", "ROBIN", "BY", "LABEL", "BLOCK",
            "TOP", "BOTTOM", "NORTH", "EAST", "SOUTH", "WEST", "SIDE", "LEFT", "RIGHT", "FRONT", "BACK", "NULL",
            "TICKS", "TICK", "SECONDS", "SECOND", "GLOBAL", "PLUS", "REDSTONE", "LEVEL", "LEVELS", "PULSE", "DO", "END",
            "NAME", "EVERY", "COMMA", "COLON", "SLASH", "DASH", "LPAREN", "RPAREN", "NUMBER_WITH_G_SUFFIX", "NUMBER",
            "IDENTIFIER", "STRING", "LINE_COMMENT", "WS", "UNUSED" };
    }

    private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    static {
        tokenNames = new String[_SYMBOLIC_NAMES.length];
        for (int i = 0; i < tokenNames.length; i++) {
            tokenNames[i] = VOCABULARY.getLiteralName(i);
            if (tokenNames[i] == null) {
                tokenNames[i] = VOCABULARY.getSymbolicName(i);
            }

            if (tokenNames[i] == null) {
                tokenNames[i] = "<INVALID>";
            }
        }
    }

    @Override
    @Deprecated
    public String[] getTokenNames() {
        return tokenNames;
    }

    @Override

    public Vocabulary getVocabulary() {
        return VOCABULARY;
    }

    @Override
    public String getGrammarFileName() {
        return "SFML.g4";
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }

    public SFMLParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ProgramContext extends ParserRuleContext {

        public TerminalNode EOF() {
            return getToken(SFMLParser.EOF, 0);
        }

        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }

        public List<TriggerContext> trigger() {
            return getRuleContexts(TriggerContext.class);
        }

        public TriggerContext trigger(int i) {
            return getRuleContext(TriggerContext.class, i);
        }

        public ProgramContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_program;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterProgram(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitProgram(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitProgram(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ProgramContext program() throws RecognitionException {
        ProgramContext _localctx = new ProgramContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_program);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(81);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == NAME) {
                    {
                        setState(80);
                        name();
                    }
                }

                setState(86);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == EVERY) {
                    {
                        {
                            setState(83);
                            trigger();
                        }
                    }
                    setState(88);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(89);
                match(EOF);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class NameContext extends ParserRuleContext {

        public TerminalNode NAME() {
            return getToken(SFMLParser.NAME, 0);
        }

        public StringContext string() {
            return getRuleContext(StringContext.class, 0);
        }

        public NameContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_name;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterName(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitName(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitName(this);
            else return visitor.visitChildren(this);
        }
    }

    public final NameContext name() throws RecognitionException {
        NameContext _localctx = new NameContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_name);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(91);
                match(NAME);
                setState(92);
                string();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class TriggerContext extends ParserRuleContext {

        public TriggerContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_trigger;
        }

        public TriggerContext() {}

        public void copyFrom(TriggerContext ctx) {
            super.copyFrom(ctx);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class PulseTriggerContext extends TriggerContext {

        public TerminalNode EVERY() {
            return getToken(SFMLParser.EVERY, 0);
        }

        public TerminalNode REDSTONE() {
            return getToken(SFMLParser.REDSTONE, 0);
        }

        public TerminalNode PULSE() {
            return getToken(SFMLParser.PULSE, 0);
        }

        public TerminalNode DO() {
            return getToken(SFMLParser.DO, 0);
        }

        public BlockContext block() {
            return getRuleContext(BlockContext.class, 0);
        }

        public TerminalNode END() {
            return getToken(SFMLParser.END, 0);
        }

        public PulseTriggerContext(TriggerContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterPulseTrigger(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitPulseTrigger(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitPulseTrigger(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class TimerTriggerContext extends TriggerContext {

        public TerminalNode EVERY() {
            return getToken(SFMLParser.EVERY, 0);
        }

        public IntervalContext interval() {
            return getRuleContext(IntervalContext.class, 0);
        }

        public TerminalNode DO() {
            return getToken(SFMLParser.DO, 0);
        }

        public BlockContext block() {
            return getRuleContext(BlockContext.class, 0);
        }

        public TerminalNode END() {
            return getToken(SFMLParser.END, 0);
        }

        public TimerTriggerContext(TriggerContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterTimerTrigger(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitTimerTrigger(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitTimerTrigger(this);
            else return visitor.visitChildren(this);
        }
    }

    public final TriggerContext trigger() throws RecognitionException {
        TriggerContext _localctx = new TriggerContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_trigger);
        try {
            setState(107);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 2, _ctx)) {
                case 1:
                    _localctx = new TimerTriggerContext(_localctx);
                    enterOuterAlt(_localctx, 1); {
                    setState(94);
                    match(EVERY);
                    setState(95);
                    interval();
                    setState(96);
                    match(DO);
                    setState(97);
                    block();
                    setState(98);
                    match(END);
                }
                    break;
                case 2:
                    _localctx = new PulseTriggerContext(_localctx);
                    enterOuterAlt(_localctx, 2); {
                    setState(100);
                    match(EVERY);
                    setState(101);
                    match(REDSTONE);
                    setState(102);
                    match(PULSE);
                    setState(103);
                    match(DO);
                    setState(104);
                    block();
                    setState(105);
                    match(END);
                }
                    break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class IntervalContext extends ParserRuleContext {

        public IntervalContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_interval;
        }

        public IntervalContext() {}

        public void copyFrom(IntervalContext ctx) {
            super.copyFrom(ctx);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class IntervalNoSpaceContext extends IntervalContext {

        public TerminalNode NUMBER_WITH_G_SUFFIX() {
            return getToken(SFMLParser.NUMBER_WITH_G_SUFFIX, 0);
        }

        public TerminalNode TICKS() {
            return getToken(SFMLParser.TICKS, 0);
        }

        public TerminalNode TICK() {
            return getToken(SFMLParser.TICK, 0);
        }

        public TerminalNode SECONDS() {
            return getToken(SFMLParser.SECONDS, 0);
        }

        public TerminalNode SECOND() {
            return getToken(SFMLParser.SECOND, 0);
        }

        public TerminalNode PLUS() {
            return getToken(SFMLParser.PLUS, 0);
        }

        public TerminalNode NUMBER() {
            return getToken(SFMLParser.NUMBER, 0);
        }

        public IntervalNoSpaceContext(IntervalContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterIntervalNoSpace(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitIntervalNoSpace(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitIntervalNoSpace(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class IntervalSpaceContext extends IntervalContext {

        public TerminalNode TICKS() {
            return getToken(SFMLParser.TICKS, 0);
        }

        public TerminalNode TICK() {
            return getToken(SFMLParser.TICK, 0);
        }

        public TerminalNode SECONDS() {
            return getToken(SFMLParser.SECONDS, 0);
        }

        public TerminalNode SECOND() {
            return getToken(SFMLParser.SECOND, 0);
        }

        public List<TerminalNode> NUMBER() {
            return getTokens(SFMLParser.NUMBER);
        }

        public TerminalNode NUMBER(int i) {
            return getToken(SFMLParser.NUMBER, i);
        }

        public TerminalNode GLOBAL() {
            return getToken(SFMLParser.GLOBAL, 0);
        }

        public TerminalNode PLUS() {
            return getToken(SFMLParser.PLUS, 0);
        }

        public IntervalSpaceContext(IntervalContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterIntervalSpace(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitIntervalSpace(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitIntervalSpace(this);
            else return visitor.visitChildren(this);
        }
    }

    public final IntervalContext interval() throws RecognitionException {
        IntervalContext _localctx = new IntervalContext(_ctx, getState());
        enterRule(_localctx, 6, RULE_interval);
        int _la;
        try {
            setState(126);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case TICKS:
                case TICK:
                case SECONDS:
                case SECOND:
                case GLOBAL:
                case PLUS:
                case NUMBER:
                    _localctx = new IntervalSpaceContext(_localctx);
                    enterOuterAlt(_localctx, 1); {
                    setState(110);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == NUMBER) {
                        {
                            setState(109);
                            match(NUMBER);
                        }
                    }

                    setState(113);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == GLOBAL) {
                        {
                            setState(112);
                            match(GLOBAL);
                        }
                    }

                    setState(117);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == PLUS) {
                        {
                            setState(115);
                            match(PLUS);
                            setState(116);
                            match(NUMBER);
                        }
                    }

                    setState(119);
                    _la = _input.LA(1);
                    if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 8646911284551352320L) != 0))) {
                        _errHandler.recoverInline(this);
                    } else {
                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                        _errHandler.reportMatch(this);
                        consume();
                    }
                }
                    break;
                case NUMBER_WITH_G_SUFFIX:
                    _localctx = new IntervalNoSpaceContext(_localctx);
                    enterOuterAlt(_localctx, 2); {
                    setState(120);
                    match(NUMBER_WITH_G_SUFFIX);
                    setState(123);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == PLUS) {
                        {
                            setState(121);
                            match(PLUS);
                            setState(122);
                            match(NUMBER);
                        }
                    }

                    setState(125);
                    _la = _input.LA(1);
                    if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 8646911284551352320L) != 0))) {
                        _errHandler.recoverInline(this);
                    } else {
                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                        _errHandler.reportMatch(this);
                        consume();
                    }
                }
                    break;
                default:
                    throw new NoViableAltException(this);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class BlockContext extends ParserRuleContext {

        public List<StatementContext> statement() {
            return getRuleContexts(StatementContext.class);
        }

        public StatementContext statement(int i) {
            return getRuleContext(StatementContext.class, i);
        }

        public BlockContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_block;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterBlock(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitBlock(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitBlock(this);
            else return visitor.visitChildren(this);
        }
    }

    public final BlockContext block() throws RecognitionException {
        BlockContext _localctx = new BlockContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_block);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(131);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 34863054850L) != 0)) {
                    {
                        {
                            setState(128);
                            statement();
                        }
                    }
                    setState(133);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class StatementContext extends ParserRuleContext {

        public InputStatementContext inputStatement() {
            return getRuleContext(InputStatementContext.class, 0);
        }

        public RedstoneOutputStatementContext redstoneOutputStatement() {
            return getRuleContext(RedstoneOutputStatementContext.class, 0);
        }

        public OutputStatementContext outputStatement() {
            return getRuleContext(OutputStatementContext.class, 0);
        }

        public IfStatementContext ifStatement() {
            return getRuleContext(IfStatementContext.class, 0);
        }

        public ForgetStatementContext forgetStatement() {
            return getRuleContext(ForgetStatementContext.class, 0);
        }

        public StatementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_statement;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterStatement(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitStatement(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitStatement(this);
            else return visitor.visitChildren(this);
        }
    }

    public final StatementContext statement() throws RecognitionException {
        StatementContext _localctx = new StatementContext(_ctx, getState());
        enterRule(_localctx, 10, RULE_statement);
        try {
            setState(139);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 9, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1); {
                    setState(134);
                    inputStatement();
                }
                    break;
                case 2:
                    enterOuterAlt(_localctx, 2); {
                    setState(135);
                    redstoneOutputStatement();
                }
                    break;
                case 3:
                    enterOuterAlt(_localctx, 3); {
                    setState(136);
                    outputStatement();
                }
                    break;
                case 4:
                    enterOuterAlt(_localctx, 4); {
                    setState(137);
                    ifStatement();
                }
                    break;
                case 5:
                    enterOuterAlt(_localctx, 5); {
                    setState(138);
                    forgetStatement();
                }
                    break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class RedstoneOutputStatementContext extends ParserRuleContext {

        public TerminalNode OUTPUT() {
            return getToken(SFMLParser.OUTPUT, 0);
        }

        public TerminalNode REDSTONE() {
            return getToken(SFMLParser.REDSTONE, 0);
        }

        public TerminalNode TO() {
            return getToken(SFMLParser.TO, 0);
        }

        public LabelAccessContext labelAccess() {
            return getRuleContext(LabelAccessContext.class, 0);
        }

        public TerminalNode LEVEL() {
            return getToken(SFMLParser.LEVEL, 0);
        }

        public TerminalNode LEVELS() {
            return getToken(SFMLParser.LEVELS, 0);
        }

        public List<QuantityContext> quantity() {
            return getRuleContexts(QuantityContext.class);
        }

        public QuantityContext quantity(int i) {
            return getRuleContext(QuantityContext.class, i);
        }

        public TerminalNode EACH() {
            return getToken(SFMLParser.EACH, 0);
        }

        public RedstoneOutputStatementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_redstoneOutputStatement;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterRedstoneOutputStatement(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitRedstoneOutputStatement(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor)
                return ((SFMLVisitor<? extends T>) visitor).visitRedstoneOutputStatement(this);
            else return visitor.visitChildren(this);
        }
    }

    public final RedstoneOutputStatementContext redstoneOutputStatement() throws RecognitionException {
        RedstoneOutputStatementContext _localctx = new RedstoneOutputStatementContext(_ctx, getState());
        enterRule(_localctx, 12, RULE_redstoneOutputStatement);
        int _la;
        try {
            setState(169);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case OUTPUT:
                    enterOuterAlt(_localctx, 1); {
                    setState(141);
                    match(OUTPUT);
                    setState(143);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == NUMBER) {
                        {
                            setState(142);
                            quantity();
                        }
                    }

                    setState(145);
                    match(REDSTONE);
                    setState(146);
                    _la = _input.LA(1);
                    if (!(_la == LEVEL || _la == LEVELS)) {
                        _errHandler.recoverInline(this);
                    } else {
                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                        _errHandler.reportMatch(this);
                        consume();
                    }
                    setState(148);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == NUMBER) {
                        {
                            setState(147);
                            quantity();
                        }
                    }

                    setState(150);
                    match(TO);
                    setState(152);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == EACH) {
                        {
                            setState(151);
                            match(EACH);
                        }
                    }

                    setState(154);
                    labelAccess();
                }
                    break;
                case TO:
                    enterOuterAlt(_localctx, 2); {
                    setState(155);
                    match(TO);
                    setState(157);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == EACH) {
                        {
                            setState(156);
                            match(EACH);
                        }
                    }

                    setState(159);
                    labelAccess();
                    setState(160);
                    match(OUTPUT);
                    setState(162);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == NUMBER) {
                        {
                            setState(161);
                            quantity();
                        }
                    }

                    setState(164);
                    match(REDSTONE);
                    setState(165);
                    _la = _input.LA(1);
                    if (!(_la == LEVEL || _la == LEVELS)) {
                        _errHandler.recoverInline(this);
                    } else {
                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                        _errHandler.reportMatch(this);
                        consume();
                    }
                    setState(167);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == NUMBER) {
                        {
                            setState(166);
                            quantity();
                        }
                    }

                }
                    break;
                default:
                    throw new NoViableAltException(this);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ForgetStatementContext extends ParserRuleContext {

        public TerminalNode FORGET() {
            return getToken(SFMLParser.FORGET, 0);
        }

        public List<LabelContext> label() {
            return getRuleContexts(LabelContext.class);
        }

        public LabelContext label(int i) {
            return getRuleContext(LabelContext.class, i);
        }

        public List<TerminalNode> COMMA() {
            return getTokens(SFMLParser.COMMA);
        }

        public TerminalNode COMMA(int i) {
            return getToken(SFMLParser.COMMA, i);
        }

        public ForgetStatementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_forgetStatement;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterForgetStatement(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitForgetStatement(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitForgetStatement(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ForgetStatementContext forgetStatement() throws RecognitionException {
        ForgetStatementContext _localctx = new ForgetStatementContext(_ctx, getState());
        enterRule(_localctx, 14, RULE_forgetStatement);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(171);
                match(FORGET);
                setState(173);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if ((((_la) & ~0x3f) == 0 && ((1L << _la) & -2035204819106398080L) != 0)
                    || ((((_la - 65)) & ~0x3f) == 0 && ((1L << (_la - 65)) & 196615L) != 0)) {
                    {
                        setState(172);
                        label();
                    }
                }

                setState(179);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 18, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        {
                            {
                                setState(175);
                                match(COMMA);
                                setState(176);
                                label();
                            }
                        }
                    }
                    setState(181);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 18, _ctx);
                }
                setState(183);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == COMMA) {
                    {
                        setState(182);
                        match(COMMA);
                    }
                }

            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class InputStatementContext extends ParserRuleContext {

        public TerminalNode INPUT() {
            return getToken(SFMLParser.INPUT, 0);
        }

        public TerminalNode FROM() {
            return getToken(SFMLParser.FROM, 0);
        }

        public LabelAccessContext labelAccess() {
            return getRuleContext(LabelAccessContext.class, 0);
        }

        public InputResourceLimitsContext inputResourceLimits() {
            return getRuleContext(InputResourceLimitsContext.class, 0);
        }

        public ResourceExclusionContext resourceExclusion() {
            return getRuleContext(ResourceExclusionContext.class, 0);
        }

        public TerminalNode EACH() {
            return getToken(SFMLParser.EACH, 0);
        }

        public InputStatementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_inputStatement;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterInputStatement(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitInputStatement(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitInputStatement(this);
            else return visitor.visitChildren(this);
        }
    }

    public final InputStatementContext inputStatement() throws RecognitionException {
        InputStatementContext _localctx = new InputStatementContext(_ctx, getState());
        enterRule(_localctx, 16, RULE_inputStatement);
        int _la;
        try {
            setState(209);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case INPUT:
                    enterOuterAlt(_localctx, 1); {
                    setState(185);
                    match(INPUT);
                    setState(187);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if ((((_la) & ~0x3f) == 0 && ((1L << _la) & -2035203990177709952L) != 0)
                        || ((((_la - 65)) & ~0x3f) == 0 && ((1L << (_la - 65)) & 229383L) != 0)) {
                        {
                            setState(186);
                            inputResourceLimits();
                        }
                    }

                    setState(190);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == EXCEPT) {
                        {
                            setState(189);
                            resourceExclusion();
                        }
                    }

                    setState(192);
                    match(FROM);
                    setState(194);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == EACH) {
                        {
                            setState(193);
                            match(EACH);
                        }
                    }

                    setState(196);
                    labelAccess();
                }
                    break;
                case FROM:
                    enterOuterAlt(_localctx, 2); {
                    setState(197);
                    match(FROM);
                    setState(199);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == EACH) {
                        {
                            setState(198);
                            match(EACH);
                        }
                    }

                    setState(201);
                    labelAccess();
                    setState(202);
                    match(INPUT);
                    setState(204);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if ((((_la) & ~0x3f) == 0 && ((1L << _la) & -2035203990177709952L) != 0)
                        || ((((_la - 65)) & ~0x3f) == 0 && ((1L << (_la - 65)) & 229383L) != 0)) {
                        {
                            setState(203);
                            inputResourceLimits();
                        }
                    }

                    setState(207);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == EXCEPT) {
                        {
                            setState(206);
                            resourceExclusion();
                        }
                    }

                }
                    break;
                default:
                    throw new NoViableAltException(this);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class OutputStatementContext extends ParserRuleContext {

        public TerminalNode OUTPUT() {
            return getToken(SFMLParser.OUTPUT, 0);
        }

        public TerminalNode TO() {
            return getToken(SFMLParser.TO, 0);
        }

        public LabelAccessContext labelAccess() {
            return getRuleContext(LabelAccessContext.class, 0);
        }

        public OutputResourceLimitsContext outputResourceLimits() {
            return getRuleContext(OutputResourceLimitsContext.class, 0);
        }

        public ResourceExclusionContext resourceExclusion() {
            return getRuleContext(ResourceExclusionContext.class, 0);
        }

        public EmptyslotsContext emptyslots() {
            return getRuleContext(EmptyslotsContext.class, 0);
        }

        public TerminalNode EACH() {
            return getToken(SFMLParser.EACH, 0);
        }

        public OutputStatementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_outputStatement;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterOutputStatement(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitOutputStatement(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitOutputStatement(this);
            else return visitor.visitChildren(this);
        }
    }

    public final OutputStatementContext outputStatement() throws RecognitionException {
        OutputStatementContext _localctx = new OutputStatementContext(_ctx, getState());
        enterRule(_localctx, 18, RULE_outputStatement);
        int _la;
        try {
            setState(241);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case OUTPUT:
                    enterOuterAlt(_localctx, 1); {
                    setState(211);
                    match(OUTPUT);
                    setState(213);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if ((((_la) & ~0x3f) == 0 && ((1L << _la) & -2035203990177709952L) != 0)
                        || ((((_la - 65)) & ~0x3f) == 0 && ((1L << (_la - 65)) & 229383L) != 0)) {
                        {
                            setState(212);
                            outputResourceLimits();
                        }
                    }

                    setState(216);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == EXCEPT) {
                        {
                            setState(215);
                            resourceExclusion();
                        }
                    }

                    setState(218);
                    match(TO);
                    setState(220);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == EMPTY) {
                        {
                            setState(219);
                            emptyslots();
                        }
                    }

                    setState(223);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == EACH) {
                        {
                            setState(222);
                            match(EACH);
                        }
                    }

                    setState(225);
                    labelAccess();
                }
                    break;
                case TO:
                    enterOuterAlt(_localctx, 2); {
                    setState(226);
                    match(TO);
                    setState(228);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == EMPTY) {
                        {
                            setState(227);
                            emptyslots();
                        }
                    }

                    setState(231);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == EACH) {
                        {
                            setState(230);
                            match(EACH);
                        }
                    }

                    setState(233);
                    labelAccess();
                    setState(234);
                    match(OUTPUT);
                    setState(236);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if ((((_la) & ~0x3f) == 0 && ((1L << _la) & -2035203990177709952L) != 0)
                        || ((((_la - 65)) & ~0x3f) == 0 && ((1L << (_la - 65)) & 229383L) != 0)) {
                        {
                            setState(235);
                            outputResourceLimits();
                        }
                    }

                    setState(239);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == EXCEPT) {
                        {
                            setState(238);
                            resourceExclusion();
                        }
                    }

                }
                    break;
                default:
                    throw new NoViableAltException(this);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class InputResourceLimitsContext extends ParserRuleContext {

        public ResourceLimitListContext resourceLimitList() {
            return getRuleContext(ResourceLimitListContext.class, 0);
        }

        public InputResourceLimitsContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_inputResourceLimits;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterInputResourceLimits(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitInputResourceLimits(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor)
                return ((SFMLVisitor<? extends T>) visitor).visitInputResourceLimits(this);
            else return visitor.visitChildren(this);
        }
    }

    public final InputResourceLimitsContext inputResourceLimits() throws RecognitionException {
        InputResourceLimitsContext _localctx = new InputResourceLimitsContext(_ctx, getState());
        enterRule(_localctx, 20, RULE_inputResourceLimits);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(243);
                resourceLimitList();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class OutputResourceLimitsContext extends ParserRuleContext {

        public ResourceLimitListContext resourceLimitList() {
            return getRuleContext(ResourceLimitListContext.class, 0);
        }

        public OutputResourceLimitsContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_outputResourceLimits;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterOutputResourceLimits(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitOutputResourceLimits(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor)
                return ((SFMLVisitor<? extends T>) visitor).visitOutputResourceLimits(this);
            else return visitor.visitChildren(this);
        }
    }

    public final OutputResourceLimitsContext outputResourceLimits() throws RecognitionException {
        OutputResourceLimitsContext _localctx = new OutputResourceLimitsContext(_ctx, getState());
        enterRule(_localctx, 22, RULE_outputResourceLimits);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(245);
                resourceLimitList();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ResourceLimitListContext extends ParserRuleContext {

        public List<ResourceLimitContext> resourceLimit() {
            return getRuleContexts(ResourceLimitContext.class);
        }

        public ResourceLimitContext resourceLimit(int i) {
            return getRuleContext(ResourceLimitContext.class, i);
        }

        public List<TerminalNode> COMMA() {
            return getTokens(SFMLParser.COMMA);
        }

        public TerminalNode COMMA(int i) {
            return getToken(SFMLParser.COMMA, i);
        }

        public ResourceLimitListContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_resourceLimitList;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterResourceLimitList(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitResourceLimitList(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor)
                return ((SFMLVisitor<? extends T>) visitor).visitResourceLimitList(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ResourceLimitListContext resourceLimitList() throws RecognitionException {
        ResourceLimitListContext _localctx = new ResourceLimitListContext(_ctx, getState());
        enterRule(_localctx, 24, RULE_resourceLimitList);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(247);
                resourceLimit();
                setState(252);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 36, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        {
                            {
                                setState(248);
                                match(COMMA);
                                setState(249);
                                resourceLimit();
                            }
                        }
                    }
                    setState(254);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 36, _ctx);
                }
                setState(256);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == COMMA) {
                    {
                        setState(255);
                        match(COMMA);
                    }
                }

            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ResourceLimitContext extends ParserRuleContext {

        public ResourceIdDisjunctionContext resourceIdDisjunction() {
            return getRuleContext(ResourceIdDisjunctionContext.class, 0);
        }

        public LimitContext limit() {
            return getRuleContext(LimitContext.class, 0);
        }

        public WithContext with() {
            return getRuleContext(WithContext.class, 0);
        }

        public ResourceLimitContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_resourceLimit;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterResourceLimit(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitResourceLimit(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitResourceLimit(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ResourceLimitContext resourceLimit() throws RecognitionException {
        ResourceLimitContext _localctx = new ResourceLimitContext(_ctx, getState());
        enterRule(_localctx, 26, RULE_resourceLimit);
        int _la;
        try {
            setState(270);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 41, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1); {
                    setState(259);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == RETAIN || _la == NUMBER) {
                        {
                            setState(258);
                            limit();
                        }
                    }

                    setState(261);
                    resourceIdDisjunction();
                    setState(263);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == WITHOUT || _la == WITH) {
                        {
                            setState(262);
                            with();
                        }
                    }

                }
                    break;
                case 2:
                    enterOuterAlt(_localctx, 2); {
                    setState(265);
                    limit();
                    setState(267);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == WITHOUT || _la == WITH) {
                        {
                            setState(266);
                            with();
                        }
                    }

                }
                    break;
                case 3:
                    enterOuterAlt(_localctx, 3); {
                    setState(269);
                    with();
                }
                    break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class LimitContext extends ParserRuleContext {

        public LimitContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_limit;
        }

        public LimitContext() {}

        public void copyFrom(LimitContext ctx) {
            super.copyFrom(ctx);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class RetentionLimitContext extends LimitContext {

        public RetentionContext retention() {
            return getRuleContext(RetentionContext.class, 0);
        }

        public RetentionLimitContext(LimitContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterRetentionLimit(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitRetentionLimit(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitRetentionLimit(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class QuantityRetentionLimitContext extends LimitContext {

        public QuantityContext quantity() {
            return getRuleContext(QuantityContext.class, 0);
        }

        public RetentionContext retention() {
            return getRuleContext(RetentionContext.class, 0);
        }

        public QuantityRetentionLimitContext(LimitContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterQuantityRetentionLimit(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitQuantityRetentionLimit(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor)
                return ((SFMLVisitor<? extends T>) visitor).visitQuantityRetentionLimit(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class QuantityLimitContext extends LimitContext {

        public QuantityContext quantity() {
            return getRuleContext(QuantityContext.class, 0);
        }

        public QuantityLimitContext(LimitContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterQuantityLimit(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitQuantityLimit(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitQuantityLimit(this);
            else return visitor.visitChildren(this);
        }
    }

    public final LimitContext limit() throws RecognitionException {
        LimitContext _localctx = new LimitContext(_ctx, getState());
        enterRule(_localctx, 28, RULE_limit);
        try {
            setState(277);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 42, _ctx)) {
                case 1:
                    _localctx = new QuantityRetentionLimitContext(_localctx);
                    enterOuterAlt(_localctx, 1); {
                    setState(272);
                    quantity();
                    setState(273);
                    retention();
                }
                    break;
                case 2:
                    _localctx = new RetentionLimitContext(_localctx);
                    enterOuterAlt(_localctx, 2); {
                    setState(275);
                    retention();
                }
                    break;
                case 3:
                    _localctx = new QuantityLimitContext(_localctx);
                    enterOuterAlt(_localctx, 3); {
                    setState(276);
                    quantity();
                }
                    break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class QuantityContext extends ParserRuleContext {

        public NumberContext number() {
            return getRuleContext(NumberContext.class, 0);
        }

        public TerminalNode EACH() {
            return getToken(SFMLParser.EACH, 0);
        }

        public QuantityContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_quantity;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterQuantity(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitQuantity(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitQuantity(this);
            else return visitor.visitChildren(this);
        }
    }

    public final QuantityContext quantity() throws RecognitionException {
        QuantityContext _localctx = new QuantityContext(_ctx, getState());
        enterRule(_localctx, 30, RULE_quantity);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(279);
                number();
                setState(281);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == EACH) {
                    {
                        setState(280);
                        match(EACH);
                    }
                }

            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class RetentionContext extends ParserRuleContext {

        public TerminalNode RETAIN() {
            return getToken(SFMLParser.RETAIN, 0);
        }

        public NumberContext number() {
            return getRuleContext(NumberContext.class, 0);
        }

        public TerminalNode EACH() {
            return getToken(SFMLParser.EACH, 0);
        }

        public RetentionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_retention;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterRetention(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitRetention(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitRetention(this);
            else return visitor.visitChildren(this);
        }
    }

    public final RetentionContext retention() throws RecognitionException {
        RetentionContext _localctx = new RetentionContext(_ctx, getState());
        enterRule(_localctx, 32, RULE_retention);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(283);
                match(RETAIN);
                setState(284);
                number();
                setState(286);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == EACH) {
                    {
                        setState(285);
                        match(EACH);
                    }
                }

            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ResourceExclusionContext extends ParserRuleContext {

        public TerminalNode EXCEPT() {
            return getToken(SFMLParser.EXCEPT, 0);
        }

        public ResourceIdListContext resourceIdList() {
            return getRuleContext(ResourceIdListContext.class, 0);
        }

        public ResourceExclusionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_resourceExclusion;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterResourceExclusion(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitResourceExclusion(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor)
                return ((SFMLVisitor<? extends T>) visitor).visitResourceExclusion(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ResourceExclusionContext resourceExclusion() throws RecognitionException {
        ResourceExclusionContext _localctx = new ResourceExclusionContext(_ctx, getState());
        enterRule(_localctx, 34, RULE_resourceExclusion);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(288);
                match(EXCEPT);
                setState(289);
                resourceIdList();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ResourceIdContext extends ParserRuleContext {

        public ResourceIdContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_resourceId;
        }

        public ResourceIdContext() {}

        public void copyFrom(ResourceIdContext ctx) {
            super.copyFrom(ctx);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class StringResourceContext extends ResourceIdContext {

        public StringContext string() {
            return getRuleContext(StringContext.class, 0);
        }

        public StringResourceContext(ResourceIdContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterStringResource(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitStringResource(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitStringResource(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ResourceContext extends ResourceIdContext {

        public List<IdentifierContext> identifier() {
            return getRuleContexts(IdentifierContext.class);
        }

        public IdentifierContext identifier(int i) {
            return getRuleContext(IdentifierContext.class, i);
        }

        public List<TerminalNode> COLON() {
            return getTokens(SFMLParser.COLON);
        }

        public TerminalNode COLON(int i) {
            return getToken(SFMLParser.COLON, i);
        }

        public ResourceContext(ResourceIdContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterResource(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitResource(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitResource(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ResourceIdContext resourceId() throws RecognitionException {
        ResourceIdContext _localctx = new ResourceIdContext(_ctx, getState());
        enterRule(_localctx, 36, RULE_resourceId);
        try {
            setState(311);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case ANY:
                case TOP:
                case BOTTOM:
                case LEFT:
                case RIGHT:
                case FRONT:
                case BACK:
                case SECONDS:
                case SECOND:
                case GLOBAL:
                case REDSTONE:
                case LEVEL:
                case LEVELS:
                case IDENTIFIER:
                    _localctx = new ResourceContext(_localctx);
                    enterOuterAlt(_localctx, 1); {
                    {
                        setState(291);
                        identifier();
                    }
                    setState(308);
                    _errHandler.sync(this);
                    switch (getInterpreter().adaptivePredict(_input, 50, _ctx)) {
                        case 1: {
                            setState(292);
                            match(COLON);
                            setState(294);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 45, _ctx)) {
                                case 1: {
                                    setState(293);
                                    identifier();
                                }
                                    break;
                            }
                            setState(306);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 49, _ctx)) {
                                case 1: {
                                    setState(296);
                                    match(COLON);
                                    setState(298);
                                    _errHandler.sync(this);
                                    switch (getInterpreter().adaptivePredict(_input, 46, _ctx)) {
                                        case 1: {
                                            setState(297);
                                            identifier();
                                        }
                                            break;
                                    }
                                    setState(304);
                                    _errHandler.sync(this);
                                    switch (getInterpreter().adaptivePredict(_input, 48, _ctx)) {
                                        case 1: {
                                            setState(300);
                                            match(COLON);
                                            setState(302);
                                            _errHandler.sync(this);
                                            switch (getInterpreter().adaptivePredict(_input, 47, _ctx)) {
                                                case 1: {
                                                    setState(301);
                                                    identifier();
                                                }
                                                    break;
                                            }
                                        }
                                            break;
                                    }
                                }
                                    break;
                            }
                        }
                            break;
                    }
                }
                    break;
                case STRING:
                    _localctx = new StringResourceContext(_localctx);
                    enterOuterAlt(_localctx, 2); {
                    setState(310);
                    string();
                }
                    break;
                default:
                    throw new NoViableAltException(this);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ResourceIdListContext extends ParserRuleContext {

        public List<ResourceIdContext> resourceId() {
            return getRuleContexts(ResourceIdContext.class);
        }

        public ResourceIdContext resourceId(int i) {
            return getRuleContext(ResourceIdContext.class, i);
        }

        public List<TerminalNode> COMMA() {
            return getTokens(SFMLParser.COMMA);
        }

        public TerminalNode COMMA(int i) {
            return getToken(SFMLParser.COMMA, i);
        }

        public ResourceIdListContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_resourceIdList;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterResourceIdList(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitResourceIdList(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitResourceIdList(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ResourceIdListContext resourceIdList() throws RecognitionException {
        ResourceIdListContext _localctx = new ResourceIdListContext(_ctx, getState());
        enterRule(_localctx, 38, RULE_resourceIdList);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(313);
                resourceId();
                setState(318);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 52, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        {
                            {
                                setState(314);
                                match(COMMA);
                                setState(315);
                                resourceId();
                            }
                        }
                    }
                    setState(320);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 52, _ctx);
                }
                setState(322);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 53, _ctx)) {
                    case 1: {
                        setState(321);
                        match(COMMA);
                    }
                        break;
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ResourceIdDisjunctionContext extends ParserRuleContext {

        public List<ResourceIdContext> resourceId() {
            return getRuleContexts(ResourceIdContext.class);
        }

        public ResourceIdContext resourceId(int i) {
            return getRuleContext(ResourceIdContext.class, i);
        }

        public List<TerminalNode> OR() {
            return getTokens(SFMLParser.OR);
        }

        public TerminalNode OR(int i) {
            return getToken(SFMLParser.OR, i);
        }

        public ResourceIdDisjunctionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_resourceIdDisjunction;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterResourceIdDisjunction(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitResourceIdDisjunction(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor)
                return ((SFMLVisitor<? extends T>) visitor).visitResourceIdDisjunction(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ResourceIdDisjunctionContext resourceIdDisjunction() throws RecognitionException {
        ResourceIdDisjunctionContext _localctx = new ResourceIdDisjunctionContext(_ctx, getState());
        enterRule(_localctx, 40, RULE_resourceIdDisjunction);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(324);
                resourceId();
                setState(329);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 54, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        {
                            {
                                setState(325);
                                match(OR);
                                setState(326);
                                resourceId();
                            }
                        }
                    }
                    setState(331);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 54, _ctx);
                }
                setState(333);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 55, _ctx)) {
                    case 1: {
                        setState(332);
                        match(OR);
                    }
                        break;
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class WithContext extends ParserRuleContext {

        public TerminalNode WITH() {
            return getToken(SFMLParser.WITH, 0);
        }

        public WithClauseContext withClause() {
            return getRuleContext(WithClauseContext.class, 0);
        }

        public TerminalNode WITHOUT() {
            return getToken(SFMLParser.WITHOUT, 0);
        }

        public WithContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_with;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterWith(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitWith(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitWith(this);
            else return visitor.visitChildren(this);
        }
    }

    public final WithContext with() throws RecognitionException {
        WithContext _localctx = new WithContext(_ctx, getState());
        enterRule(_localctx, 42, RULE_with);
        try {
            setState(339);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case WITH:
                    enterOuterAlt(_localctx, 1); {
                    setState(335);
                    match(WITH);
                    setState(336);
                    withClause(0);
                }
                    break;
                case WITHOUT:
                    enterOuterAlt(_localctx, 2); {
                    setState(337);
                    match(WITHOUT);
                    setState(338);
                    withClause(0);
                }
                    break;
                default:
                    throw new NoViableAltException(this);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class WithClauseContext extends ParserRuleContext {

        public WithClauseContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_withClause;
        }

        public WithClauseContext() {}

        public void copyFrom(WithClauseContext ctx) {
            super.copyFrom(ctx);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class WithConjunctionContext extends WithClauseContext {

        public List<WithClauseContext> withClause() {
            return getRuleContexts(WithClauseContext.class);
        }

        public WithClauseContext withClause(int i) {
            return getRuleContext(WithClauseContext.class, i);
        }

        public TerminalNode AND() {
            return getToken(SFMLParser.AND, 0);
        }

        public WithConjunctionContext(WithClauseContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterWithConjunction(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitWithConjunction(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitWithConjunction(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class WithParenContext extends WithClauseContext {

        public TerminalNode LPAREN() {
            return getToken(SFMLParser.LPAREN, 0);
        }

        public WithClauseContext withClause() {
            return getRuleContext(WithClauseContext.class, 0);
        }

        public TerminalNode RPAREN() {
            return getToken(SFMLParser.RPAREN, 0);
        }

        public WithParenContext(WithClauseContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterWithParen(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitWithParen(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitWithParen(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class WithNegationContext extends WithClauseContext {

        public TerminalNode NOT() {
            return getToken(SFMLParser.NOT, 0);
        }

        public WithClauseContext withClause() {
            return getRuleContext(WithClauseContext.class, 0);
        }

        public WithNegationContext(WithClauseContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterWithNegation(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitWithNegation(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitWithNegation(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class WithTagContext extends WithClauseContext {

        public TagMatcherContext tagMatcher() {
            return getRuleContext(TagMatcherContext.class, 0);
        }

        public TerminalNode TAG() {
            return getToken(SFMLParser.TAG, 0);
        }

        public TerminalNode HASHTAG() {
            return getToken(SFMLParser.HASHTAG, 0);
        }

        public WithTagContext(WithClauseContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterWithTag(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitWithTag(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitWithTag(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class WithDisjunctionContext extends WithClauseContext {

        public List<WithClauseContext> withClause() {
            return getRuleContexts(WithClauseContext.class);
        }

        public WithClauseContext withClause(int i) {
            return getRuleContext(WithClauseContext.class, i);
        }

        public TerminalNode OR() {
            return getToken(SFMLParser.OR, 0);
        }

        public WithDisjunctionContext(WithClauseContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterWithDisjunction(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitWithDisjunction(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitWithDisjunction(this);
            else return visitor.visitChildren(this);
        }
    }

    public final WithClauseContext withClause() throws RecognitionException {
        return withClause(0);
    }

    private WithClauseContext withClause(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        WithClauseContext _localctx = new WithClauseContext(_ctx, _parentState);
        WithClauseContext _prevctx = _localctx;
        int _startState = 44;
        enterRecursionRule(_localctx, 44, RULE_withClause, _p);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(356);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case LPAREN: {
                        _localctx = new WithParenContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;

                        setState(342);
                        match(LPAREN);
                        setState(343);
                        withClause(0);
                        setState(344);
                        match(RPAREN);
                    }
                        break;
                    case NOT: {
                        _localctx = new WithNegationContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(346);
                        match(NOT);
                        setState(347);
                        withClause(4);
                    }
                        break;
                    case TAG:
                    case HASHTAG: {
                        _localctx = new WithTagContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(353);
                        _errHandler.sync(this);
                        switch (_input.LA(1)) {
                            case TAG: {
                                setState(348);
                                match(TAG);
                                setState(350);
                                _errHandler.sync(this);
                                _la = _input.LA(1);
                                if (_la == HASHTAG) {
                                    {
                                        setState(349);
                                        match(HASHTAG);
                                    }
                                }

                            }
                                break;
                            case HASHTAG: {
                                setState(352);
                                match(HASHTAG);
                            }
                                break;
                            default:
                                throw new NoViableAltException(this);
                        }
                        setState(355);
                        tagMatcher();
                    }
                        break;
                    default:
                        throw new NoViableAltException(this);
                }
                _ctx.stop = _input.LT(-1);
                setState(366);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 61, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            setState(364);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 60, _ctx)) {
                                case 1: {
                                    _localctx = new WithConjunctionContext(
                                        new WithClauseContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_withClause);
                                    setState(358);
                                    if (!(precpred(_ctx, 3)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 3)");
                                    setState(359);
                                    match(AND);
                                    setState(360);
                                    withClause(4);
                                }
                                    break;
                                case 2: {
                                    _localctx = new WithDisjunctionContext(
                                        new WithClauseContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_withClause);
                                    setState(361);
                                    if (!(precpred(_ctx, 2)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 2)");
                                    setState(362);
                                    match(OR);
                                    setState(363);
                                    withClause(3);
                                }
                                    break;
                            }
                        }
                    }
                    setState(368);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 61, _ctx);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            unrollRecursionContexts(_parentctx);
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class TagMatcherContext extends ParserRuleContext {

        public List<IdentifierContext> identifier() {
            return getRuleContexts(IdentifierContext.class);
        }

        public IdentifierContext identifier(int i) {
            return getRuleContext(IdentifierContext.class, i);
        }

        public TerminalNode COLON() {
            return getToken(SFMLParser.COLON, 0);
        }

        public List<TerminalNode> SLASH() {
            return getTokens(SFMLParser.SLASH);
        }

        public TerminalNode SLASH(int i) {
            return getToken(SFMLParser.SLASH, i);
        }

        public TagMatcherContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_tagMatcher;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterTagMatcher(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitTagMatcher(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitTagMatcher(this);
            else return visitor.visitChildren(this);
        }
    }

    public final TagMatcherContext tagMatcher() throws RecognitionException {
        TagMatcherContext _localctx = new TagMatcherContext(_ctx, getState());
        enterRule(_localctx, 46, RULE_tagMatcher);
        try {
            int _alt;
            setState(387);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 64, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1); {
                    setState(369);
                    identifier();
                    setState(370);
                    match(COLON);
                    setState(371);
                    identifier();
                    setState(376);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 62, _ctx);
                    while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                        if (_alt == 1) {
                            {
                                {
                                    setState(372);
                                    match(SLASH);
                                    setState(373);
                                    identifier();
                                }
                            }
                        }
                        setState(378);
                        _errHandler.sync(this);
                        _alt = getInterpreter().adaptivePredict(_input, 62, _ctx);
                    }
                }
                    break;
                case 2:
                    enterOuterAlt(_localctx, 2); {
                    setState(379);
                    identifier();
                    setState(384);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 63, _ctx);
                    while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                        if (_alt == 1) {
                            {
                                {
                                    setState(380);
                                    match(SLASH);
                                    setState(381);
                                    identifier();
                                }
                            }
                        }
                        setState(386);
                        _errHandler.sync(this);
                        _alt = getInterpreter().adaptivePredict(_input, 63, _ctx);
                    }
                }
                    break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class SidequalifierContext extends ParserRuleContext {

        public SidequalifierContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_sidequalifier;
        }

        public SidequalifierContext() {}

        public void copyFrom(SidequalifierContext ctx) {
            super.copyFrom(ctx);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class EachSideContext extends SidequalifierContext {

        public TerminalNode EACH() {
            return getToken(SFMLParser.EACH, 0);
        }

        public TerminalNode SIDE() {
            return getToken(SFMLParser.SIDE, 0);
        }

        public EachSideContext(SidequalifierContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterEachSide(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitEachSide(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitEachSide(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ListedSidesContext extends SidequalifierContext {

        public List<SideContext> side() {
            return getRuleContexts(SideContext.class);
        }

        public SideContext side(int i) {
            return getRuleContext(SideContext.class, i);
        }

        public TerminalNode SIDE() {
            return getToken(SFMLParser.SIDE, 0);
        }

        public List<TerminalNode> COMMA() {
            return getTokens(SFMLParser.COMMA);
        }

        public TerminalNode COMMA(int i) {
            return getToken(SFMLParser.COMMA, i);
        }

        public ListedSidesContext(SidequalifierContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterListedSides(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitListedSides(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitListedSides(this);
            else return visitor.visitChildren(this);
        }
    }

    public final SidequalifierContext sidequalifier() throws RecognitionException {
        SidequalifierContext _localctx = new SidequalifierContext(_ctx, getState());
        enterRule(_localctx, 48, RULE_sidequalifier);
        int _la;
        try {
            setState(401);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case EACH:
                    _localctx = new EachSideContext(_localctx);
                    enterOuterAlt(_localctx, 1); {
                    setState(389);
                    match(EACH);
                    setState(390);
                    match(SIDE);
                }
                    break;
                case TOP:
                case BOTTOM:
                case NORTH:
                case EAST:
                case SOUTH:
                case WEST:
                case LEFT:
                case RIGHT:
                case FRONT:
                case BACK:
                case NULL:
                    _localctx = new ListedSidesContext(_localctx);
                    enterOuterAlt(_localctx, 2); {
                    setState(391);
                    side();
                    setState(396);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    while (_la == COMMA) {
                        {
                            {
                                setState(392);
                                match(COMMA);
                                setState(393);
                                side();
                            }
                        }
                        setState(398);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                    }
                    setState(399);
                    match(SIDE);
                }
                    break;
                default:
                    throw new NoViableAltException(this);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class SideContext extends ParserRuleContext {

        public TerminalNode TOP() {
            return getToken(SFMLParser.TOP, 0);
        }

        public TerminalNode BOTTOM() {
            return getToken(SFMLParser.BOTTOM, 0);
        }

        public TerminalNode NORTH() {
            return getToken(SFMLParser.NORTH, 0);
        }

        public TerminalNode EAST() {
            return getToken(SFMLParser.EAST, 0);
        }

        public TerminalNode SOUTH() {
            return getToken(SFMLParser.SOUTH, 0);
        }

        public TerminalNode WEST() {
            return getToken(SFMLParser.WEST, 0);
        }

        public TerminalNode LEFT() {
            return getToken(SFMLParser.LEFT, 0);
        }

        public TerminalNode RIGHT() {
            return getToken(SFMLParser.RIGHT, 0);
        }

        public TerminalNode FRONT() {
            return getToken(SFMLParser.FRONT, 0);
        }

        public TerminalNode BACK() {
            return getToken(SFMLParser.BACK, 0);
        }

        public TerminalNode NULL() {
            return getToken(SFMLParser.NULL, 0);
        }

        public SideContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_side;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterSide(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitSide(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitSide(this);
            else return visitor.visitChildren(this);
        }
    }

    public final SideContext side() throws RecognitionException {
        SideContext _localctx = new SideContext(_ctx, getState());
        enterRule(_localctx, 50, RULE_side);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(403);
                _la = _input.LA(1);
                if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 567312815560327168L) != 0))) {
                    _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class SlotqualifierContext extends ParserRuleContext {

        public RangesetContext rangeset() {
            return getRuleContext(RangesetContext.class, 0);
        }

        public TerminalNode SLOTS() {
            return getToken(SFMLParser.SLOTS, 0);
        }

        public TerminalNode SLOT() {
            return getToken(SFMLParser.SLOT, 0);
        }

        public SlotqualifierContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_slotqualifier;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterSlotqualifier(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitSlotqualifier(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitSlotqualifier(this);
            else return visitor.visitChildren(this);
        }
    }

    public final SlotqualifierContext slotqualifier() throws RecognitionException {
        SlotqualifierContext _localctx = new SlotqualifierContext(_ctx, getState());
        enterRule(_localctx, 52, RULE_slotqualifier);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(405);
                _la = _input.LA(1);
                if (!(_la == SLOTS || _la == SLOT)) {
                    _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
                setState(406);
                rangeset();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class RangesetContext extends ParserRuleContext {

        public List<RangeContext> range() {
            return getRuleContexts(RangeContext.class);
        }

        public RangeContext range(int i) {
            return getRuleContext(RangeContext.class, i);
        }

        public List<TerminalNode> COMMA() {
            return getTokens(SFMLParser.COMMA);
        }

        public TerminalNode COMMA(int i) {
            return getToken(SFMLParser.COMMA, i);
        }

        public RangesetContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_rangeset;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterRangeset(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitRangeset(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitRangeset(this);
            else return visitor.visitChildren(this);
        }
    }

    public final RangesetContext rangeset() throws RecognitionException {
        RangesetContext _localctx = new RangesetContext(_ctx, getState());
        enterRule(_localctx, 54, RULE_rangeset);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(408);
                range();
                setState(413);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == COMMA) {
                    {
                        {
                            setState(409);
                            match(COMMA);
                            setState(410);
                            range();
                        }
                    }
                    setState(415);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class RangeContext extends ParserRuleContext {

        public List<NumberContext> number() {
            return getRuleContexts(NumberContext.class);
        }

        public NumberContext number(int i) {
            return getRuleContext(NumberContext.class, i);
        }

        public TerminalNode DASH() {
            return getToken(SFMLParser.DASH, 0);
        }

        public RangeContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_range;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterRange(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitRange(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitRange(this);
            else return visitor.visitChildren(this);
        }
    }

    public final RangeContext range() throws RecognitionException {
        RangeContext _localctx = new RangeContext(_ctx, getState());
        enterRule(_localctx, 56, RULE_range);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(416);
                number();
                setState(419);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == DASH) {
                    {
                        setState(417);
                        match(DASH);
                        setState(418);
                        number();
                    }
                }

            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class IfStatementContext extends ParserRuleContext {

        public List<TerminalNode> IF() {
            return getTokens(SFMLParser.IF);
        }

        public TerminalNode IF(int i) {
            return getToken(SFMLParser.IF, i);
        }

        public List<BoolexprContext> boolexpr() {
            return getRuleContexts(BoolexprContext.class);
        }

        public BoolexprContext boolexpr(int i) {
            return getRuleContext(BoolexprContext.class, i);
        }

        public List<TerminalNode> THEN() {
            return getTokens(SFMLParser.THEN);
        }

        public TerminalNode THEN(int i) {
            return getToken(SFMLParser.THEN, i);
        }

        public List<BlockContext> block() {
            return getRuleContexts(BlockContext.class);
        }

        public BlockContext block(int i) {
            return getRuleContext(BlockContext.class, i);
        }

        public TerminalNode END() {
            return getToken(SFMLParser.END, 0);
        }

        public List<TerminalNode> ELSE() {
            return getTokens(SFMLParser.ELSE);
        }

        public TerminalNode ELSE(int i) {
            return getToken(SFMLParser.ELSE, i);
        }

        public IfStatementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_ifStatement;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterIfStatement(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitIfStatement(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitIfStatement(this);
            else return visitor.visitChildren(this);
        }
    }

    public final IfStatementContext ifStatement() throws RecognitionException {
        IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
        enterRule(_localctx, 58, RULE_ifStatement);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(421);
                match(IF);
                setState(422);
                boolexpr(0);
                setState(423);
                match(THEN);
                setState(424);
                block();
                setState(433);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 69, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        {
                            {
                                setState(425);
                                match(ELSE);
                                setState(426);
                                match(IF);
                                setState(427);
                                boolexpr(0);
                                setState(428);
                                match(THEN);
                                setState(429);
                                block();
                            }
                        }
                    }
                    setState(435);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 69, _ctx);
                }
                setState(438);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == ELSE) {
                    {
                        setState(436);
                        match(ELSE);
                        setState(437);
                        block();
                    }
                }

                setState(440);
                match(END);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class BoolexprContext extends ParserRuleContext {

        public BoolexprContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_boolexpr;
        }

        public BoolexprContext() {}

        public void copyFrom(BoolexprContext ctx) {
            super.copyFrom(ctx);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class BooleanHasContext extends BoolexprContext {

        public LabelAccessContext labelAccess() {
            return getRuleContext(LabelAccessContext.class, 0);
        }

        public TerminalNode HAS() {
            return getToken(SFMLParser.HAS, 0);
        }

        public ComparisonOpContext comparisonOp() {
            return getRuleContext(ComparisonOpContext.class, 0);
        }

        public NumberContext number() {
            return getRuleContext(NumberContext.class, 0);
        }

        public SetOpContext setOp() {
            return getRuleContext(SetOpContext.class, 0);
        }

        public ResourceIdDisjunctionContext resourceIdDisjunction() {
            return getRuleContext(ResourceIdDisjunctionContext.class, 0);
        }

        public WithContext with() {
            return getRuleContext(WithContext.class, 0);
        }

        public TerminalNode EXCEPT() {
            return getToken(SFMLParser.EXCEPT, 0);
        }

        public ResourceIdListContext resourceIdList() {
            return getRuleContext(ResourceIdListContext.class, 0);
        }

        public BooleanHasContext(BoolexprContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterBooleanHas(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitBooleanHas(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitBooleanHas(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class BooleanConjunctionContext extends BoolexprContext {

        public List<BoolexprContext> boolexpr() {
            return getRuleContexts(BoolexprContext.class);
        }

        public BoolexprContext boolexpr(int i) {
            return getRuleContext(BoolexprContext.class, i);
        }

        public TerminalNode AND() {
            return getToken(SFMLParser.AND, 0);
        }

        public BooleanConjunctionContext(BoolexprContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterBooleanConjunction(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitBooleanConjunction(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor)
                return ((SFMLVisitor<? extends T>) visitor).visitBooleanConjunction(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class BooleanRedstoneContext extends BoolexprContext {

        public TerminalNode REDSTONE() {
            return getToken(SFMLParser.REDSTONE, 0);
        }

        public ComparisonOpContext comparisonOp() {
            return getRuleContext(ComparisonOpContext.class, 0);
        }

        public NumberContext number() {
            return getRuleContext(NumberContext.class, 0);
        }

        public TerminalNode LEVEL() {
            return getToken(SFMLParser.LEVEL, 0);
        }

        public TerminalNode LEVELS() {
            return getToken(SFMLParser.LEVELS, 0);
        }

        public BooleanRedstoneContext(BoolexprContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterBooleanRedstone(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitBooleanRedstone(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitBooleanRedstone(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class BooleanDisjunctionContext extends BoolexprContext {

        public List<BoolexprContext> boolexpr() {
            return getRuleContexts(BoolexprContext.class);
        }

        public BoolexprContext boolexpr(int i) {
            return getRuleContext(BoolexprContext.class, i);
        }

        public TerminalNode OR() {
            return getToken(SFMLParser.OR, 0);
        }

        public BooleanDisjunctionContext(BoolexprContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterBooleanDisjunction(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitBooleanDisjunction(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor)
                return ((SFMLVisitor<? extends T>) visitor).visitBooleanDisjunction(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class BooleanFalseContext extends BoolexprContext {

        public TerminalNode FALSE() {
            return getToken(SFMLParser.FALSE, 0);
        }

        public BooleanFalseContext(BoolexprContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterBooleanFalse(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitBooleanFalse(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitBooleanFalse(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class BooleanParenContext extends BoolexprContext {

        public TerminalNode LPAREN() {
            return getToken(SFMLParser.LPAREN, 0);
        }

        public BoolexprContext boolexpr() {
            return getRuleContext(BoolexprContext.class, 0);
        }

        public TerminalNode RPAREN() {
            return getToken(SFMLParser.RPAREN, 0);
        }

        public BooleanParenContext(BoolexprContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterBooleanParen(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitBooleanParen(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitBooleanParen(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class BooleanNegationContext extends BoolexprContext {

        public TerminalNode NOT() {
            return getToken(SFMLParser.NOT, 0);
        }

        public BoolexprContext boolexpr() {
            return getRuleContext(BoolexprContext.class, 0);
        }

        public BooleanNegationContext(BoolexprContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterBooleanNegation(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitBooleanNegation(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitBooleanNegation(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class BooleanTrueContext extends BoolexprContext {

        public TerminalNode TRUE() {
            return getToken(SFMLParser.TRUE, 0);
        }

        public BooleanTrueContext(BoolexprContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterBooleanTrue(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitBooleanTrue(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitBooleanTrue(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class BooleanLabelledRedstoneContext extends BoolexprContext {

        public LabelAccessContext labelAccess() {
            return getRuleContext(LabelAccessContext.class, 0);
        }

        public TerminalNode REDSTONE() {
            return getToken(SFMLParser.REDSTONE, 0);
        }

        public SetOpContext setOp() {
            return getRuleContext(SetOpContext.class, 0);
        }

        public ComparisonOpContext comparisonOp() {
            return getRuleContext(ComparisonOpContext.class, 0);
        }

        public NumberContext number() {
            return getRuleContext(NumberContext.class, 0);
        }

        public TerminalNode LEVEL() {
            return getToken(SFMLParser.LEVEL, 0);
        }

        public TerminalNode LEVELS() {
            return getToken(SFMLParser.LEVELS, 0);
        }

        public BooleanLabelledRedstoneContext(BoolexprContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterBooleanLabelledRedstone(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitBooleanLabelledRedstone(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor)
                return ((SFMLVisitor<? extends T>) visitor).visitBooleanLabelledRedstone(this);
            else return visitor.visitChildren(this);
        }
    }

    public final BoolexprContext boolexpr() throws RecognitionException {
        return boolexpr(0);
    }

    private BoolexprContext boolexpr(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        BoolexprContext _localctx = new BoolexprContext(_ctx, _parentState);
        BoolexprContext _prevctx = _localctx;
        int _startState = 60;
        enterRecursionRule(_localctx, 60, RULE_boolexpr, _p);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(490);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 80, _ctx)) {
                    case 1: {
                        _localctx = new BooleanTrueContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;

                        setState(443);
                        match(TRUE);
                    }
                        break;
                    case 2: {
                        _localctx = new BooleanFalseContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(444);
                        match(FALSE);
                    }
                        break;
                    case 3: {
                        _localctx = new BooleanParenContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(445);
                        match(LPAREN);
                        setState(446);
                        boolexpr(0);
                        setState(447);
                        match(RPAREN);
                    }
                        break;
                    case 4: {
                        _localctx = new BooleanNegationContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(449);
                        match(NOT);
                        setState(450);
                        boolexpr(6);
                    }
                        break;
                    case 5: {
                        _localctx = new BooleanHasContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(452);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 71, _ctx)) {
                            case 1: {
                                setState(451);
                                setOp();
                            }
                                break;
                        }
                        setState(454);
                        labelAccess();
                        setState(455);
                        match(HAS);
                        setState(456);
                        comparisonOp();
                        setState(457);
                        number();
                        setState(459);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 72, _ctx)) {
                            case 1: {
                                setState(458);
                                resourceIdDisjunction();
                            }
                                break;
                        }
                        setState(462);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 73, _ctx)) {
                            case 1: {
                                setState(461);
                                with();
                            }
                                break;
                        }
                        setState(466);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 74, _ctx)) {
                            case 1: {
                                setState(464);
                                match(EXCEPT);
                                setState(465);
                                resourceIdList();
                            }
                                break;
                        }
                    }
                        break;
                    case 6: {
                        _localctx = new BooleanRedstoneContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(468);
                        match(REDSTONE);
                        setState(470);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 75, _ctx)) {
                            case 1: {
                                setState(469);
                                _la = _input.LA(1);
                                if (!(_la == LEVEL || _la == LEVELS)) {
                                    _errHandler.recoverInline(this);
                                } else {
                                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                    _errHandler.reportMatch(this);
                                    consume();
                                }
                            }
                                break;
                        }
                        setState(475);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 76, _ctx)) {
                            case 1: {
                                setState(472);
                                comparisonOp();
                                setState(473);
                                number();
                            }
                                break;
                        }
                    }
                        break;
                    case 7: {
                        _localctx = new BooleanLabelledRedstoneContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(478);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 77, _ctx)) {
                            case 1: {
                                setState(477);
                                setOp();
                            }
                                break;
                        }
                        setState(480);
                        labelAccess();
                        setState(481);
                        match(REDSTONE);
                        setState(483);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 78, _ctx)) {
                            case 1: {
                                setState(482);
                                _la = _input.LA(1);
                                if (!(_la == LEVEL || _la == LEVELS)) {
                                    _errHandler.recoverInline(this);
                                } else {
                                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                    _errHandler.reportMatch(this);
                                    consume();
                                }
                            }
                                break;
                        }
                        setState(488);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 79, _ctx)) {
                            case 1: {
                                setState(485);
                                comparisonOp();
                                setState(486);
                                number();
                            }
                                break;
                        }
                    }
                        break;
                }
                _ctx.stop = _input.LT(-1);
                setState(500);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 82, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            setState(498);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 81, _ctx)) {
                                case 1: {
                                    _localctx = new BooleanConjunctionContext(
                                        new BoolexprContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_boolexpr);
                                    setState(492);
                                    if (!(precpred(_ctx, 5)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 5)");
                                    setState(493);
                                    match(AND);
                                    setState(494);
                                    boolexpr(6);
                                }
                                    break;
                                case 2: {
                                    _localctx = new BooleanDisjunctionContext(
                                        new BoolexprContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_boolexpr);
                                    setState(495);
                                    if (!(precpred(_ctx, 4)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 4)");
                                    setState(496);
                                    match(OR);
                                    setState(497);
                                    boolexpr(5);
                                }
                                    break;
                            }
                        }
                    }
                    setState(502);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 82, _ctx);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            unrollRecursionContexts(_parentctx);
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ComparisonOpContext extends ParserRuleContext {

        public TerminalNode GT() {
            return getToken(SFMLParser.GT, 0);
        }

        public TerminalNode LT() {
            return getToken(SFMLParser.LT, 0);
        }

        public TerminalNode EQ() {
            return getToken(SFMLParser.EQ, 0);
        }

        public TerminalNode LE() {
            return getToken(SFMLParser.LE, 0);
        }

        public TerminalNode GE() {
            return getToken(SFMLParser.GE, 0);
        }

        public TerminalNode GT_SYMBOL() {
            return getToken(SFMLParser.GT_SYMBOL, 0);
        }

        public TerminalNode LT_SYMBOL() {
            return getToken(SFMLParser.LT_SYMBOL, 0);
        }

        public TerminalNode EQ_SYMBOL() {
            return getToken(SFMLParser.EQ_SYMBOL, 0);
        }

        public TerminalNode LE_SYMBOL() {
            return getToken(SFMLParser.LE_SYMBOL, 0);
        }

        public TerminalNode GE_SYMBOL() {
            return getToken(SFMLParser.GE_SYMBOL, 0);
        }

        public ComparisonOpContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_comparisonOp;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterComparisonOp(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitComparisonOp(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitComparisonOp(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ComparisonOpContext comparisonOp() throws RecognitionException {
        ComparisonOpContext _localctx = new ComparisonOpContext(_ctx, getState());
        enterRule(_localctx, 62, RULE_comparisonOp);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(503);
                _la = _input.LA(1);
                if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 33521664L) != 0))) {
                    _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class SetOpContext extends ParserRuleContext {

        public TerminalNode OVERALL() {
            return getToken(SFMLParser.OVERALL, 0);
        }

        public TerminalNode SOME() {
            return getToken(SFMLParser.SOME, 0);
        }

        public TerminalNode EVERY() {
            return getToken(SFMLParser.EVERY, 0);
        }

        public TerminalNode EACH() {
            return getToken(SFMLParser.EACH, 0);
        }

        public TerminalNode ANY() {
            return getToken(SFMLParser.ANY, 0);
        }

        public TerminalNode ONE() {
            return getToken(SFMLParser.ONE, 0);
        }

        public TerminalNode LONE() {
            return getToken(SFMLParser.LONE, 0);
        }

        public SetOpContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_setOp;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterSetOp(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitSetOp(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitSetOp(this);
            else return visitor.visitChildren(this);
        }
    }

    public final SetOpContext setOp() throws RecognitionException {
        SetOpContext _localctx = new SetOpContext(_ctx, getState());
        enterRule(_localctx, 64, RULE_setOp);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(505);
                _la = _input.LA(1);
                if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 8589935584L) != 0) || _la == EVERY)) {
                    _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class LabelAccessContext extends ParserRuleContext {

        public List<LabelContext> label() {
            return getRuleContexts(LabelContext.class);
        }

        public LabelContext label(int i) {
            return getRuleContext(LabelContext.class, i);
        }

        public List<TerminalNode> COMMA() {
            return getTokens(SFMLParser.COMMA);
        }

        public TerminalNode COMMA(int i) {
            return getToken(SFMLParser.COMMA, i);
        }

        public RoundrobinContext roundrobin() {
            return getRuleContext(RoundrobinContext.class, 0);
        }

        public SidequalifierContext sidequalifier() {
            return getRuleContext(SidequalifierContext.class, 0);
        }

        public SlotqualifierContext slotqualifier() {
            return getRuleContext(SlotqualifierContext.class, 0);
        }

        public LabelAccessContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_labelAccess;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterLabelAccess(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitLabelAccess(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitLabelAccess(this);
            else return visitor.visitChildren(this);
        }
    }

    public final LabelAccessContext labelAccess() throws RecognitionException {
        LabelAccessContext _localctx = new LabelAccessContext(_ctx, getState());
        enterRule(_localctx, 66, RULE_labelAccess);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(507);
                label();
                setState(512);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == COMMA) {
                    {
                        {
                            setState(508);
                            match(COMMA);
                            setState(509);
                            label();
                        }
                    }
                    setState(514);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(516);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == ROUND) {
                    {
                        setState(515);
                        roundrobin();
                    }
                }

                setState(519);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 567312824150261760L) != 0)) {
                    {
                        setState(518);
                        sidequalifier();
                    }
                }

                setState(522);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == SLOTS || _la == SLOT) {
                    {
                        setState(521);
                        slotqualifier();
                    }
                }

            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class RoundrobinContext extends ParserRuleContext {

        public TerminalNode ROUND() {
            return getToken(SFMLParser.ROUND, 0);
        }

        public TerminalNode ROBIN() {
            return getToken(SFMLParser.ROBIN, 0);
        }

        public TerminalNode BY() {
            return getToken(SFMLParser.BY, 0);
        }

        public TerminalNode LABEL() {
            return getToken(SFMLParser.LABEL, 0);
        }

        public TerminalNode BLOCK() {
            return getToken(SFMLParser.BLOCK, 0);
        }

        public RoundrobinContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_roundrobin;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterRoundrobin(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitRoundrobin(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitRoundrobin(this);
            else return visitor.visitChildren(this);
        }
    }

    public final RoundrobinContext roundrobin() throws RecognitionException {
        RoundrobinContext _localctx = new RoundrobinContext(_ctx, getState());
        enterRule(_localctx, 68, RULE_roundrobin);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(524);
                match(ROUND);
                setState(525);
                match(ROBIN);
                setState(526);
                match(BY);
                setState(527);
                _la = _input.LA(1);
                if (!(_la == LABEL || _la == BLOCK)) {
                    _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class LabelContext extends ParserRuleContext {

        public LabelContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_label;
        }

        public LabelContext() {}

        public void copyFrom(LabelContext ctx) {
            super.copyFrom(ctx);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class RawLabelContext extends LabelContext {

        public IdentifierContext identifier() {
            return getRuleContext(IdentifierContext.class, 0);
        }

        public RawLabelContext(LabelContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterRawLabel(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitRawLabel(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitRawLabel(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class StringLabelContext extends LabelContext {

        public StringContext string() {
            return getRuleContext(StringContext.class, 0);
        }

        public StringLabelContext(LabelContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterStringLabel(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitStringLabel(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitStringLabel(this);
            else return visitor.visitChildren(this);
        }
    }

    public final LabelContext label() throws RecognitionException {
        LabelContext _localctx = new LabelContext(_ctx, getState());
        enterRule(_localctx, 70, RULE_label);
        try {
            setState(531);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case ANY:
                case TOP:
                case BOTTOM:
                case LEFT:
                case RIGHT:
                case FRONT:
                case BACK:
                case SECONDS:
                case SECOND:
                case GLOBAL:
                case REDSTONE:
                case LEVEL:
                case LEVELS:
                case IDENTIFIER:
                    _localctx = new RawLabelContext(_localctx);
                    enterOuterAlt(_localctx, 1); {
                    {
                        setState(529);
                        identifier();
                    }
                }
                    break;
                case STRING:
                    _localctx = new StringLabelContext(_localctx);
                    enterOuterAlt(_localctx, 2); {
                    setState(530);
                    string();
                }
                    break;
                default:
                    throw new NoViableAltException(this);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class EmptyslotsContext extends ParserRuleContext {

        public TerminalNode EMPTY() {
            return getToken(SFMLParser.EMPTY, 0);
        }

        public TerminalNode IN() {
            return getToken(SFMLParser.IN, 0);
        }

        public TerminalNode SLOTS() {
            return getToken(SFMLParser.SLOTS, 0);
        }

        public TerminalNode SLOT() {
            return getToken(SFMLParser.SLOT, 0);
        }

        public EmptyslotsContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_emptyslots;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterEmptyslots(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitEmptyslots(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitEmptyslots(this);
            else return visitor.visitChildren(this);
        }
    }

    public final EmptyslotsContext emptyslots() throws RecognitionException {
        EmptyslotsContext _localctx = new EmptyslotsContext(_ctx, getState());
        enterRule(_localctx, 72, RULE_emptyslots);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(533);
                match(EMPTY);
                setState(534);
                _la = _input.LA(1);
                if (!(_la == SLOTS || _la == SLOT)) {
                    _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
                setState(535);
                match(IN);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class IdentifierContext extends ParserRuleContext {

        public TerminalNode IDENTIFIER() {
            return getToken(SFMLParser.IDENTIFIER, 0);
        }

        public TerminalNode ANY() {
            return getToken(SFMLParser.ANY, 0);
        }

        public TerminalNode REDSTONE() {
            return getToken(SFMLParser.REDSTONE, 0);
        }

        public TerminalNode LEVEL() {
            return getToken(SFMLParser.LEVEL, 0);
        }

        public TerminalNode LEVELS() {
            return getToken(SFMLParser.LEVELS, 0);
        }

        public TerminalNode GLOBAL() {
            return getToken(SFMLParser.GLOBAL, 0);
        }

        public TerminalNode SECOND() {
            return getToken(SFMLParser.SECOND, 0);
        }

        public TerminalNode SECONDS() {
            return getToken(SFMLParser.SECONDS, 0);
        }

        public TerminalNode TOP() {
            return getToken(SFMLParser.TOP, 0);
        }

        public TerminalNode BOTTOM() {
            return getToken(SFMLParser.BOTTOM, 0);
        }

        public TerminalNode LEFT() {
            return getToken(SFMLParser.LEFT, 0);
        }

        public TerminalNode RIGHT() {
            return getToken(SFMLParser.RIGHT, 0);
        }

        public TerminalNode FRONT() {
            return getToken(SFMLParser.FRONT, 0);
        }

        public TerminalNode BACK() {
            return getToken(SFMLParser.BACK, 0);
        }

        public IdentifierContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_identifier;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterIdentifier(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitIdentifier(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitIdentifier(this);
            else return visitor.visitChildren(this);
        }
    }

    public final IdentifierContext identifier() throws RecognitionException {
        IdentifierContext _localctx = new IdentifierContext(_ctx, getState());
        enterRule(_localctx, 74, RULE_identifier);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(537);
                _la = _input.LA(1);
                if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & -2035204819106398080L) != 0)
                    || ((((_la - 65)) & ~0x3f) == 0 && ((1L << (_la - 65)) & 65543L) != 0))) {
                    _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class StringContext extends ParserRuleContext {

        public TerminalNode STRING() {
            return getToken(SFMLParser.STRING, 0);
        }

        public StringContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_string;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterString(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitString(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitString(this);
            else return visitor.visitChildren(this);
        }
    }

    public final StringContext string() throws RecognitionException {
        StringContext _localctx = new StringContext(_ctx, getState());
        enterRule(_localctx, 76, RULE_string);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(539);
                match(STRING);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class NumberContext extends ParserRuleContext {

        public TerminalNode NUMBER() {
            return getToken(SFMLParser.NUMBER, 0);
        }

        public NumberContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_number;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).enterNumber(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SFMLListener) ((SFMLListener) listener).exitNumber(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SFMLVisitor) return ((SFMLVisitor<? extends T>) visitor).visitNumber(this);
            else return visitor.visitChildren(this);
        }
    }

    public final NumberContext number() throws RecognitionException {
        NumberContext _localctx = new NumberContext(_ctx, getState());
        enterRule(_localctx, 78, RULE_number);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(541);
                match(NUMBER);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
        switch (ruleIndex) {
            case 22:
                return withClause_sempred((WithClauseContext) _localctx, predIndex);
            case 30:
                return boolexpr_sempred((BoolexprContext) _localctx, predIndex);
        }
        return true;
    }

    private boolean withClause_sempred(WithClauseContext _localctx, int predIndex) {
        switch (predIndex) {
            case 0:
                return precpred(_ctx, 3);
            case 1:
                return precpred(_ctx, 2);
        }
        return true;
    }

    private boolean boolexpr_sempred(BoolexprContext _localctx, int predIndex) {
        switch (predIndex) {
            case 2:
                return precpred(_ctx, 5);
            case 3:
                return precpred(_ctx, 4);
        }
        return true;
    }

    public static final String _serializedATN = "\u0004\u0001U\u0220\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"
        + "\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"
        + "\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"
        + "\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"
        + "\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"
        + "\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"
        + "\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"
        + "\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"
        + "\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"
        + "\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007\u001e"
        + "\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007!\u0002\"\u0007\"\u0002"
        + "#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007&\u0002\'\u0007\'\u0001"
        + "\u0000\u0003\u0000R\b\u0000\u0001\u0000\u0005\u0000U\b\u0000\n\u0000\f"
        + "\u0000X\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001"
        + "\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"
        + "\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"
        + "\u0002\u0001\u0002\u0003\u0002l\b\u0002\u0001\u0003\u0003\u0003o\b\u0003"
        + "\u0001\u0003\u0003\u0003r\b\u0003\u0001\u0003\u0001\u0003\u0003\u0003"
        + "v\b\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003"
        + "|\b\u0003\u0001\u0003\u0003\u0003\u007f\b\u0003\u0001\u0004\u0005\u0004"
        + "\u0082\b\u0004\n\u0004\f\u0004\u0085\t\u0004\u0001\u0005\u0001\u0005\u0001"
        + "\u0005\u0001\u0005\u0001\u0005\u0003\u0005\u008c\b\u0005\u0001\u0006\u0001"
        + "\u0006\u0003\u0006\u0090\b\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0003"
        + "\u0006\u0095\b\u0006\u0001\u0006\u0001\u0006\u0003\u0006\u0099\b\u0006"
        + "\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006\u009e\b\u0006\u0001\u0006"
        + "\u0001\u0006\u0001\u0006\u0003\u0006\u00a3\b\u0006\u0001\u0006\u0001\u0006"
        + "\u0001\u0006\u0003\u0006\u00a8\b\u0006\u0003\u0006\u00aa\b\u0006\u0001"
        + "\u0007\u0001\u0007\u0003\u0007\u00ae\b\u0007\u0001\u0007\u0001\u0007\u0005"
        + "\u0007\u00b2\b\u0007\n\u0007\f\u0007\u00b5\t\u0007\u0001\u0007\u0003\u0007"
        + "\u00b8\b\u0007\u0001\b\u0001\b\u0003\b\u00bc\b\b\u0001\b\u0003\b\u00bf"
        + "\b\b\u0001\b\u0001\b\u0003\b\u00c3\b\b\u0001\b\u0001\b\u0001\b\u0003\b"
        + "\u00c8\b\b\u0001\b\u0001\b\u0001\b\u0003\b\u00cd\b\b\u0001\b\u0003\b\u00d0"
        + "\b\b\u0003\b\u00d2\b\b\u0001\t\u0001\t\u0003\t\u00d6\b\t\u0001\t\u0003"
        + "\t\u00d9\b\t\u0001\t\u0001\t\u0003\t\u00dd\b\t\u0001\t\u0003\t\u00e0\b"
        + "\t\u0001\t\u0001\t\u0001\t\u0003\t\u00e5\b\t\u0001\t\u0003\t\u00e8\b\t"
        + "\u0001\t\u0001\t\u0001\t\u0003\t\u00ed\b\t\u0001\t\u0003\t\u00f0\b\t\u0003"
        + "\t\u00f2\b\t\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001"
        + "\f\u0005\f\u00fb\b\f\n\f\f\f\u00fe\t\f\u0001\f\u0003\f\u0101\b\f\u0001"
        + "\r\u0003\r\u0104\b\r\u0001\r\u0001\r\u0003\r\u0108\b\r\u0001\r\u0001\r"
        + "\u0003\r\u010c\b\r\u0001\r\u0003\r\u010f\b\r\u0001\u000e\u0001\u000e\u0001"
        + "\u000e\u0001\u000e\u0001\u000e\u0003\u000e\u0116\b\u000e\u0001\u000f\u0001"
        + "\u000f\u0003\u000f\u011a\b\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0003"
        + "\u0010\u011f\b\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0012\u0001"
        + "\u0012\u0001\u0012\u0003\u0012\u0127\b\u0012\u0001\u0012\u0001\u0012\u0003"
        + "\u0012\u012b\b\u0012\u0001\u0012\u0001\u0012\u0003\u0012\u012f\b\u0012"
        + "\u0003\u0012\u0131\b\u0012\u0003\u0012\u0133\b\u0012\u0003\u0012\u0135"
        + "\b\u0012\u0001\u0012\u0003\u0012\u0138\b\u0012\u0001\u0013\u0001\u0013"
        + "\u0001\u0013\u0005\u0013\u013d\b\u0013\n\u0013\f\u0013\u0140\t\u0013\u0001"
        + "\u0013\u0003\u0013\u0143\b\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0005"
        + "\u0014\u0148\b\u0014\n\u0014\f\u0014\u014b\t\u0014\u0001\u0014\u0003\u0014"
        + "\u014e\b\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0003\u0015"
        + "\u0154\b\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016"
        + "\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0003\u0016\u015f\b\u0016"
        + "\u0001\u0016\u0003\u0016\u0162\b\u0016\u0001\u0016\u0003\u0016\u0165\b"
        + "\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001"
        + "\u0016\u0005\u0016\u016d\b\u0016\n\u0016\f\u0016\u0170\t\u0016\u0001\u0017"
        + "\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0005\u0017\u0177\b\u0017"
        + "\n\u0017\f\u0017\u017a\t\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0005"
        + "\u0017\u017f\b\u0017\n\u0017\f\u0017\u0182\t\u0017\u0003\u0017\u0184\b"
        + "\u0017\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0005"
        + "\u0018\u018b\b\u0018\n\u0018\f\u0018\u018e\t\u0018\u0001\u0018\u0001\u0018"
        + "\u0003\u0018\u0192\b\u0018\u0001\u0019\u0001\u0019\u0001\u001a\u0001\u001a"
        + "\u0001\u001a\u0001\u001b\u0001\u001b\u0001\u001b\u0005\u001b\u019c\b\u001b"
        + "\n\u001b\f\u001b\u019f\t\u001b\u0001\u001c\u0001\u001c\u0001\u001c\u0003"
        + "\u001c\u01a4\b\u001c\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001"
        + "\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0005"
        + "\u001d\u01b0\b\u001d\n\u001d\f\u001d\u01b3\t\u001d\u0001\u001d\u0001\u001d"
        + "\u0003\u001d\u01b7\b\u001d\u0001\u001d\u0001\u001d\u0001\u001e\u0001\u001e"
        + "\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e"
        + "\u0001\u001e\u0001\u001e\u0003\u001e\u01c5\b\u001e\u0001\u001e\u0001\u001e"
        + "\u0001\u001e\u0001\u001e\u0001\u001e\u0003\u001e\u01cc\b\u001e\u0001\u001e"
        + "\u0003\u001e\u01cf\b\u001e\u0001\u001e\u0001\u001e\u0003\u001e\u01d3\b"
        + "\u001e\u0001\u001e\u0001\u001e\u0003\u001e\u01d7\b\u001e\u0001\u001e\u0001"
        + "\u001e\u0001\u001e\u0003\u001e\u01dc\b\u001e\u0001\u001e\u0003\u001e\u01df"
        + "\b\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0003\u001e\u01e4\b\u001e"
        + "\u0001\u001e\u0001\u001e\u0001\u001e\u0003\u001e\u01e9\b\u001e\u0003\u001e"
        + "\u01eb\b\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e"
        + "\u0001\u001e\u0005\u001e\u01f3\b\u001e\n\u001e\f\u001e\u01f6\t\u001e\u0001"
        + "\u001f\u0001\u001f\u0001 \u0001 \u0001!\u0001!\u0001!\u0005!\u01ff\b!"
        + "\n!\f!\u0202\t!\u0001!\u0003!\u0205\b!\u0001!\u0003!\u0208\b!\u0001!\u0003"
        + "!\u020b\b!\u0001\"\u0001\"\u0001\"\u0001\"\u0001\"\u0001#\u0001#\u0003"
        + "#\u0214\b#\u0001$\u0001$\u0001$\u0001$\u0001%\u0001%\u0001&\u0001&\u0001"
        + "\'\u0001\'\u0001\'\u0000\u0002,<(\u0000\u0002\u0004\u0006\b\n\f\u000e"
        + "\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*,.02468:<>@BDF"
        + "HJLN\u0000\b\u0001\u0000;>\u0001\u0000BC\u0002\u0000/46:\u0001\u0000\u001e"
        + "\u001f\u0001\u0000\u000f\u0018\u0003\u0000\u0005\t!!HH\u0001\u0000-.\u0006"
        + "\u0000\u0007\u0007/069=?ACQQ\u025a\u0000Q\u0001\u0000\u0000\u0000\u0002"
        + "[\u0001\u0000\u0000\u0000\u0004k\u0001\u0000\u0000\u0000\u0006~\u0001"
        + "\u0000\u0000\u0000\b\u0083\u0001\u0000\u0000\u0000\n\u008b\u0001\u0000"
        + "\u0000\u0000\f\u00a9\u0001\u0000\u0000\u0000\u000e\u00ab\u0001\u0000\u0000"
        + "\u0000\u0010\u00d1\u0001\u0000\u0000\u0000\u0012\u00f1\u0001\u0000\u0000"
        + "\u0000\u0014\u00f3\u0001\u0000\u0000\u0000\u0016\u00f5\u0001\u0000\u0000"
        + "\u0000\u0018\u00f7\u0001\u0000\u0000\u0000\u001a\u010e\u0001\u0000\u0000"
        + "\u0000\u001c\u0115\u0001\u0000\u0000\u0000\u001e\u0117\u0001\u0000\u0000"
        + "\u0000 \u011b\u0001\u0000\u0000\u0000\"\u0120\u0001\u0000\u0000\u0000"
        + "$\u0137\u0001\u0000\u0000\u0000&\u0139\u0001\u0000\u0000\u0000(\u0144"
        + "\u0001\u0000\u0000\u0000*\u0153\u0001\u0000\u0000\u0000,\u0164\u0001\u0000"
        + "\u0000\u0000.\u0183\u0001\u0000\u0000\u00000\u0191\u0001\u0000\u0000\u0000"
        + "2\u0193\u0001\u0000\u0000\u00004\u0195\u0001\u0000\u0000\u00006\u0198"
        + "\u0001\u0000\u0000\u00008\u01a0\u0001\u0000\u0000\u0000:\u01a5\u0001\u0000"
        + "\u0000\u0000<\u01ea\u0001\u0000\u0000\u0000>\u01f7\u0001\u0000\u0000\u0000"
        + "@\u01f9\u0001\u0000\u0000\u0000B\u01fb\u0001\u0000\u0000\u0000D\u020c"
        + "\u0001\u0000\u0000\u0000F\u0213\u0001\u0000\u0000\u0000H\u0215\u0001\u0000"
        + "\u0000\u0000J\u0219\u0001\u0000\u0000\u0000L\u021b\u0001\u0000\u0000\u0000"
        + "N\u021d\u0001\u0000\u0000\u0000PR\u0003\u0002\u0001\u0000QP\u0001\u0000"
        + "\u0000\u0000QR\u0001\u0000\u0000\u0000RV\u0001\u0000\u0000\u0000SU\u0003"
        + "\u0004\u0002\u0000TS\u0001\u0000\u0000\u0000UX\u0001\u0000\u0000\u0000"
        + "VT\u0001\u0000\u0000\u0000VW\u0001\u0000\u0000\u0000WY\u0001\u0000\u0000"
        + "\u0000XV\u0001\u0000\u0000\u0000YZ\u0005\u0000\u0000\u0001Z\u0001\u0001"
        + "\u0000\u0000\u0000[\\\u0005G\u0000\u0000\\]\u0003L&\u0000]\u0003\u0001"
        + "\u0000\u0000\u0000^_\u0005H\u0000\u0000_`\u0003\u0006\u0003\u0000`a\u0005"
        + "E\u0000\u0000ab\u0003\b\u0004\u0000bc\u0005F\u0000\u0000cl\u0001\u0000"
        + "\u0000\u0000de\u0005H\u0000\u0000ef\u0005A\u0000\u0000fg\u0005D\u0000"
        + "\u0000gh\u0005E\u0000\u0000hi\u0003\b\u0004\u0000ij\u0005F\u0000\u0000"
        + "jl\u0001\u0000\u0000\u0000k^\u0001\u0000\u0000\u0000kd\u0001\u0000\u0000"
        + "\u0000l\u0005\u0001\u0000\u0000\u0000mo\u0005P\u0000\u0000nm\u0001\u0000"
        + "\u0000\u0000no\u0001\u0000\u0000\u0000oq\u0001\u0000\u0000\u0000pr\u0005"
        + "?\u0000\u0000qp\u0001\u0000\u0000\u0000qr\u0001\u0000\u0000\u0000ru\u0001"
        + "\u0000\u0000\u0000st\u0005@\u0000\u0000tv\u0005P\u0000\u0000us\u0001\u0000"
        + "\u0000\u0000uv\u0001\u0000\u0000\u0000vw\u0001\u0000\u0000\u0000w\u007f"
        + "\u0007\u0000\u0000\u0000x{\u0005O\u0000\u0000yz\u0005@\u0000\u0000z|\u0005"
        + "P\u0000\u0000{y\u0001\u0000\u0000\u0000{|\u0001\u0000\u0000\u0000|}\u0001"
        + "\u0000\u0000\u0000}\u007f\u0007\u0000\u0000\u0000~n\u0001\u0000\u0000"
        + "\u0000~x\u0001\u0000\u0000\u0000\u007f\u0007\u0001\u0000\u0000\u0000\u0080"
        + "\u0082\u0003\n\u0005\u0000\u0081\u0080\u0001\u0000\u0000\u0000\u0082\u0085"
        + "\u0001\u0000\u0000\u0000\u0083\u0081\u0001\u0000\u0000\u0000\u0083\u0084"
        + "\u0001\u0000\u0000\u0000\u0084\t\u0001\u0000\u0000\u0000\u0085\u0083\u0001"
        + "\u0000\u0000\u0000\u0086\u008c\u0003\u0010\b\u0000\u0087\u008c\u0003\f"
        + "\u0006\u0000\u0088\u008c\u0003\u0012\t\u0000\u0089\u008c\u0003:\u001d"
        + "\u0000\u008a\u008c\u0003\u000e\u0007\u0000\u008b\u0086\u0001\u0000\u0000"
        + "\u0000\u008b\u0087\u0001\u0000\u0000\u0000\u008b\u0088\u0001\u0000\u0000"
        + "\u0000\u008b\u0089\u0001\u0000\u0000\u0000\u008b\u008a\u0001\u0000\u0000"
        + "\u0000\u008c\u000b\u0001\u0000\u0000\u0000\u008d\u008f\u0005\u001c\u0000"
        + "\u0000\u008e\u0090\u0003\u001e\u000f\u0000\u008f\u008e\u0001\u0000\u0000"
        + "\u0000\u008f\u0090\u0001\u0000\u0000\u0000\u0090\u0091\u0001\u0000\u0000"
        + "\u0000\u0091\u0092\u0005A\u0000\u0000\u0092\u0094\u0007\u0001\u0000\u0000"
        + "\u0093\u0095\u0003\u001e\u000f\u0000\u0094\u0093\u0001\u0000\u0000\u0000"
        + "\u0094\u0095\u0001\u0000\u0000\u0000\u0095\u0096\u0001\u0000\u0000\u0000"
        + "\u0096\u0098\u0005\u001a\u0000\u0000\u0097\u0099\u0005!\u0000\u0000\u0098"
        + "\u0097\u0001\u0000\u0000\u0000\u0098\u0099\u0001\u0000\u0000\u0000\u0099"
        + "\u009a\u0001\u0000\u0000\u0000\u009a\u00aa\u0003B!\u0000\u009b\u009d\u0005"
        + "\u001a\u0000\u0000\u009c\u009e\u0005!\u0000\u0000\u009d\u009c\u0001\u0000"
        + "\u0000\u0000\u009d\u009e\u0001\u0000\u0000\u0000\u009e\u009f\u0001\u0000"
        + "\u0000\u0000\u009f\u00a0\u0003B!\u0000\u00a0\u00a2\u0005\u001c\u0000\u0000"
        + "\u00a1\u00a3\u0003\u001e\u000f\u0000\u00a2\u00a1\u0001\u0000\u0000\u0000"
        + "\u00a2\u00a3\u0001\u0000\u0000\u0000\u00a3\u00a4\u0001\u0000\u0000\u0000"
        + "\u00a4\u00a5\u0005A\u0000\u0000\u00a5\u00a7\u0007\u0001\u0000\u0000\u00a6"
        + "\u00a8\u0003\u001e\u000f\u0000\u00a7\u00a6\u0001\u0000\u0000\u0000\u00a7"
        + "\u00a8\u0001\u0000\u0000\u0000\u00a8\u00aa\u0001\u0000\u0000\u0000\u00a9"
        + "\u008d\u0001\u0000\u0000\u0000\u00a9\u009b\u0001\u0000\u0000\u0000\u00aa"
        + "\r\u0001\u0000\u0000\u0000\u00ab\u00ad\u0005#\u0000\u0000\u00ac\u00ae"
        + "\u0003F#\u0000\u00ad\u00ac\u0001\u0000\u0000\u0000\u00ad\u00ae\u0001\u0000"
        + "\u0000\u0000\u00ae\u00b3\u0001\u0000\u0000\u0000\u00af\u00b0\u0005I\u0000"
        + "\u0000\u00b0\u00b2\u0003F#\u0000\u00b1\u00af\u0001\u0000\u0000\u0000\u00b2"
        + "\u00b5\u0001\u0000\u0000\u0000\u00b3\u00b1\u0001\u0000\u0000\u0000\u00b3"
        + "\u00b4\u0001\u0000\u0000\u0000\u00b4\u00b7\u0001\u0000\u0000\u0000\u00b5"
        + "\u00b3\u0001\u0000\u0000\u0000\u00b6\u00b8\u0005I\u0000\u0000\u00b7\u00b6"
        + "\u0001\u0000\u0000\u0000\u00b7\u00b8\u0001\u0000\u0000\u0000\u00b8\u000f"
        + "\u0001\u0000\u0000\u0000\u00b9\u00bb\u0005\u001b\u0000\u0000\u00ba\u00bc"
        + "\u0003\u0014\n\u0000\u00bb\u00ba\u0001\u0000\u0000\u0000\u00bb\u00bc\u0001"
        + "\u0000\u0000\u0000\u00bc\u00be\u0001\u0000\u0000\u0000\u00bd\u00bf\u0003"
        + "\"\u0011\u0000\u00be\u00bd\u0001\u0000\u0000\u0000\u00be\u00bf\u0001\u0000"
        + "\u0000\u0000\u00bf\u00c0\u0001\u0000\u0000\u0000\u00c0\u00c2\u0005\u0019"
        + "\u0000\u0000\u00c1\u00c3\u0005!\u0000\u0000\u00c2\u00c1\u0001\u0000\u0000"
        + "\u0000\u00c2\u00c3\u0001\u0000\u0000\u0000\u00c3\u00c4\u0001\u0000\u0000"
        + "\u0000\u00c4\u00d2\u0003B!\u0000\u00c5\u00c7\u0005\u0019\u0000\u0000\u00c6"
        + "\u00c8\u0005!\u0000\u0000\u00c7\u00c6\u0001\u0000\u0000\u0000\u00c7\u00c8"
        + "\u0001\u0000\u0000\u0000\u00c8\u00c9\u0001\u0000\u0000\u0000\u00c9\u00ca"
        + "\u0003B!\u0000\u00ca\u00cc\u0005\u001b\u0000\u0000\u00cb\u00cd\u0003\u0014"
        + "\n\u0000\u00cc\u00cb\u0001\u0000\u0000\u0000\u00cc\u00cd\u0001\u0000\u0000"
        + "\u0000\u00cd\u00cf\u0001\u0000\u0000\u0000\u00ce\u00d0\u0003\"\u0011\u0000"
        + "\u00cf\u00ce\u0001\u0000\u0000\u0000\u00cf\u00d0\u0001\u0000\u0000\u0000"
        + "\u00d0\u00d2\u0001\u0000\u0000\u0000\u00d1\u00b9\u0001\u0000\u0000\u0000"
        + "\u00d1\u00c5\u0001\u0000\u0000\u0000\u00d2\u0011\u0001\u0000\u0000\u0000"
        + "\u00d3\u00d5\u0005\u001c\u0000\u0000\u00d4\u00d6\u0003\u0016\u000b\u0000"
        + "\u00d5\u00d4\u0001\u0000\u0000\u0000\u00d5\u00d6\u0001\u0000\u0000\u0000"
        + "\u00d6\u00d8\u0001\u0000\u0000\u0000\u00d7\u00d9\u0003\"\u0011\u0000\u00d8"
        + "\u00d7\u0001\u0000\u0000\u0000\u00d8\u00d9\u0001\u0000\u0000\u0000\u00d9"
        + "\u00da\u0001\u0000\u0000\u0000\u00da\u00dc\u0005\u001a\u0000\u0000\u00db"
        + "\u00dd\u0003H$\u0000\u00dc\u00db\u0001\u0000\u0000\u0000\u00dc\u00dd\u0001"
        + "\u0000\u0000\u0000\u00dd\u00df\u0001\u0000\u0000\u0000\u00de\u00e0\u0005"
        + "!\u0000\u0000\u00df\u00de\u0001\u0000\u0000\u0000\u00df\u00e0\u0001\u0000"
        + "\u0000\u0000\u00e0\u00e1\u0001\u0000\u0000\u0000\u00e1\u00f2\u0003B!\u0000"
        + "\u00e2\u00e4\u0005\u001a\u0000\u0000\u00e3\u00e5\u0003H$\u0000\u00e4\u00e3"
        + "\u0001\u0000\u0000\u0000\u00e4\u00e5\u0001\u0000\u0000\u0000\u00e5\u00e7"
        + "\u0001\u0000\u0000\u0000\u00e6\u00e8\u0005!\u0000\u0000\u00e7\u00e6\u0001"
        + "\u0000\u0000\u0000\u00e7\u00e8\u0001\u0000\u0000\u0000\u00e8\u00e9\u0001"
        + "\u0000\u0000\u0000\u00e9\u00ea\u0003B!\u0000\u00ea\u00ec\u0005\u001c\u0000"
        + "\u0000\u00eb\u00ed\u0003\u0016\u000b\u0000\u00ec\u00eb\u0001\u0000\u0000"
        + "\u0000\u00ec\u00ed\u0001\u0000\u0000\u0000\u00ed\u00ef\u0001\u0000\u0000"
        + "\u0000\u00ee\u00f0\u0003\"\u0011\u0000\u00ef\u00ee\u0001\u0000\u0000\u0000"
        + "\u00ef\u00f0\u0001\u0000\u0000\u0000\u00f0\u00f2\u0001\u0000\u0000\u0000"
        + "\u00f1\u00d3\u0001\u0000\u0000\u0000\u00f1\u00e2\u0001\u0000\u0000\u0000"
        + "\u00f2\u0013\u0001\u0000\u0000\u0000\u00f3\u00f4\u0003\u0018\f\u0000\u00f4"
        + "\u0015\u0001\u0000\u0000\u0000\u00f5\u00f6\u0003\u0018\f\u0000\u00f6\u0017"
        + "\u0001\u0000\u0000\u0000\u00f7\u00fc\u0003\u001a\r\u0000\u00f8\u00f9\u0005"
        + "I\u0000\u0000\u00f9\u00fb\u0003\u001a\r\u0000\u00fa\u00f8\u0001\u0000"
        + "\u0000\u0000\u00fb\u00fe\u0001\u0000\u0000\u0000\u00fc\u00fa\u0001\u0000"
        + "\u0000\u0000\u00fc\u00fd\u0001\u0000\u0000\u0000\u00fd\u0100\u0001\u0000"
        + "\u0000\u0000\u00fe\u00fc\u0001\u0000\u0000\u0000\u00ff\u0101\u0005I\u0000"
        + "\u0000\u0100\u00ff\u0001\u0000\u0000\u0000\u0100\u0101\u0001\u0000\u0000"
        + "\u0000\u0101\u0019\u0001\u0000\u0000\u0000\u0102\u0104\u0003\u001c\u000e"
        + "\u0000\u0103\u0102\u0001\u0000\u0000\u0000\u0103\u0104\u0001\u0000\u0000"
        + "\u0000\u0104\u0105\u0001\u0000\u0000\u0000\u0105\u0107\u0003(\u0014\u0000"
        + "\u0106\u0108\u0003*\u0015\u0000\u0107\u0106\u0001\u0000\u0000\u0000\u0107"
        + "\u0108\u0001\u0000\u0000\u0000\u0108\u010f\u0001\u0000\u0000\u0000\u0109"
        + "\u010b\u0003\u001c\u000e\u0000\u010a\u010c\u0003*\u0015\u0000\u010b\u010a"
        + "\u0001\u0000\u0000\u0000\u010b\u010c\u0001\u0000\u0000\u0000\u010c\u010f"
        + "\u0001\u0000\u0000\u0000\u010d\u010f\u0003*\u0015\u0000\u010e\u0103\u0001"
        + "\u0000\u0000\u0000\u010e\u0109\u0001\u0000\u0000\u0000\u010e\u010d\u0001"
        + "\u0000\u0000\u0000\u010f\u001b\u0001\u0000\u0000\u0000\u0110\u0111\u0003"
        + "\u001e\u000f\u0000\u0111\u0112\u0003 \u0010\u0000\u0112\u0116\u0001\u0000"
        + "\u0000\u0000\u0113\u0116\u0003 \u0010\u0000\u0114\u0116\u0003\u001e\u000f"
        + "\u0000\u0115\u0110\u0001\u0000\u0000\u0000\u0115\u0113\u0001\u0000\u0000"
        + "\u0000\u0115\u0114\u0001\u0000\u0000\u0000\u0116\u001d\u0001\u0000\u0000"
        + "\u0000\u0117\u0119\u0003N\'\u0000\u0118\u011a\u0005!\u0000\u0000\u0119"
        + "\u0118\u0001\u0000\u0000\u0000\u0119\u011a\u0001\u0000\u0000\u0000\u011a"
        + "\u001f\u0001\u0000\u0000\u0000\u011b\u011c\u0005 \u0000\u0000\u011c\u011e"
        + "\u0003N\'\u0000\u011d\u011f\u0005!\u0000\u0000\u011e\u011d\u0001\u0000"
        + "\u0000\u0000\u011e\u011f\u0001\u0000\u0000\u0000\u011f!\u0001\u0000\u0000"
        + "\u0000\u0120\u0121\u0005\"\u0000\u0000\u0121\u0122\u0003&\u0013\u0000"
        + "\u0122#\u0001\u0000\u0000\u0000\u0123\u0134\u0003J%\u0000\u0124\u0126"
        + "\u0005J\u0000\u0000\u0125\u0127\u0003J%\u0000\u0126\u0125\u0001\u0000"
        + "\u0000\u0000\u0126\u0127\u0001\u0000\u0000\u0000\u0127\u0132\u0001\u0000"
        + "\u0000\u0000\u0128\u012a\u0005J\u0000\u0000\u0129\u012b\u0003J%\u0000"
        + "\u012a\u0129\u0001\u0000\u0000\u0000\u012a\u012b\u0001\u0000\u0000\u0000"
        + "\u012b\u0130\u0001\u0000\u0000\u0000\u012c\u012e\u0005J\u0000\u0000\u012d"
        + "\u012f\u0003J%\u0000\u012e\u012d\u0001\u0000\u0000\u0000\u012e\u012f\u0001"
        + "\u0000\u0000\u0000\u012f\u0131\u0001\u0000\u0000\u0000\u0130\u012c\u0001"
        + "\u0000\u0000\u0000\u0130\u0131\u0001\u0000\u0000\u0000\u0131\u0133\u0001"
        + "\u0000\u0000\u0000\u0132\u0128\u0001\u0000\u0000\u0000\u0132\u0133\u0001"
        + "\u0000\u0000\u0000\u0133\u0135\u0001\u0000\u0000\u0000\u0134\u0124\u0001"
        + "\u0000\u0000\u0000\u0134\u0135\u0001\u0000\u0000\u0000\u0135\u0138\u0001"
        + "\u0000\u0000\u0000\u0136\u0138\u0003L&\u0000\u0137\u0123\u0001\u0000\u0000"
        + "\u0000\u0137\u0136\u0001\u0000\u0000\u0000\u0138%\u0001\u0000\u0000\u0000"
        + "\u0139\u013e\u0003$\u0012\u0000\u013a\u013b\u0005I\u0000\u0000\u013b\u013d"
        + "\u0003$\u0012\u0000\u013c\u013a\u0001\u0000\u0000\u0000\u013d\u0140\u0001"
        + "\u0000\u0000\u0000\u013e\u013c\u0001\u0000\u0000\u0000\u013e\u013f\u0001"
        + "\u0000\u0000\u0000\u013f\u0142\u0001\u0000\u0000\u0000\u0140\u013e\u0001"
        + "\u0000\u0000\u0000\u0141\u0143\u0005I\u0000\u0000\u0142\u0141\u0001\u0000"
        + "\u0000\u0000\u0142\u0143\u0001\u0000\u0000\u0000\u0143\'\u0001\u0000\u0000"
        + "\u0000\u0144\u0149\u0003$\u0012\u0000\u0145\u0146\u0005\u000e\u0000\u0000"
        + "\u0146\u0148\u0003$\u0012\u0000\u0147\u0145\u0001\u0000\u0000\u0000\u0148"
        + "\u014b\u0001\u0000\u0000\u0000\u0149\u0147\u0001\u0000\u0000\u0000\u0149"
        + "\u014a\u0001\u0000\u0000\u0000\u014a\u014d\u0001\u0000\u0000\u0000\u014b"
        + "\u0149\u0001\u0000\u0000\u0000\u014c\u014e\u0005\u000e\u0000\u0000\u014d"
        + "\u014c\u0001\u0000\u0000\u0000\u014d\u014e\u0001\u0000\u0000\u0000\u014e"
        + ")\u0001\u0000\u0000\u0000\u014f\u0150\u0005\'\u0000\u0000\u0150\u0154"
        + "\u0003,\u0016\u0000\u0151\u0152\u0005&\u0000\u0000\u0152\u0154\u0003,"
        + "\u0016\u0000\u0153\u014f\u0001\u0000\u0000\u0000\u0153\u0151\u0001\u0000"
        + "\u0000\u0000\u0154+\u0001\u0000\u0000\u0000\u0155\u0156\u0006\u0016\uffff"
        + "\uffff\u0000\u0156\u0157\u0005M\u0000\u0000\u0157\u0158\u0003,\u0016\u0000"
        + "\u0158\u0159\u0005N\u0000\u0000\u0159\u0165\u0001\u0000\u0000\u0000\u015a"
        + "\u015b\u0005\f\u0000\u0000\u015b\u0165\u0003,\u0016\u0004\u015c\u015e"
        + "\u0005(\u0000\u0000\u015d\u015f\u0005)\u0000\u0000\u015e\u015d\u0001\u0000"
        + "\u0000\u0000\u015e\u015f\u0001\u0000\u0000\u0000\u015f\u0162\u0001\u0000"
        + "\u0000\u0000\u0160\u0162\u0005)\u0000\u0000\u0161\u015c\u0001\u0000\u0000"
        + "\u0000\u0161\u0160\u0001\u0000\u0000\u0000\u0162\u0163\u0001\u0000\u0000"
        + "\u0000\u0163\u0165\u0003.\u0017\u0000\u0164\u0155\u0001\u0000\u0000\u0000"
        + "\u0164\u015a\u0001\u0000\u0000\u0000\u0164\u0161\u0001\u0000\u0000\u0000"
        + "\u0165\u016e\u0001\u0000\u0000\u0000\u0166\u0167\n\u0003\u0000\u0000\u0167"
        + "\u0168\u0005\r\u0000\u0000\u0168\u016d\u0003,\u0016\u0004\u0169\u016a"
        + "\n\u0002\u0000\u0000\u016a\u016b\u0005\u000e\u0000\u0000\u016b\u016d\u0003"
        + ",\u0016\u0003\u016c\u0166\u0001\u0000\u0000\u0000\u016c\u0169\u0001\u0000"
        + "\u0000\u0000\u016d\u0170\u0001\u0000\u0000\u0000\u016e\u016c\u0001\u0000"
        + "\u0000\u0000\u016e\u016f\u0001\u0000\u0000\u0000\u016f-\u0001\u0000\u0000"
        + "\u0000\u0170\u016e\u0001\u0000\u0000\u0000\u0171\u0172\u0003J%\u0000\u0172"
        + "\u0173\u0005J\u0000\u0000\u0173\u0178\u0003J%\u0000\u0174\u0175\u0005"
        + "K\u0000\u0000\u0175\u0177\u0003J%\u0000\u0176\u0174\u0001\u0000\u0000"
        + "\u0000\u0177\u017a\u0001\u0000\u0000\u0000\u0178\u0176\u0001\u0000\u0000"
        + "\u0000\u0178\u0179\u0001\u0000\u0000\u0000\u0179\u0184\u0001\u0000\u0000"
        + "\u0000\u017a\u0178\u0001\u0000\u0000\u0000\u017b\u0180\u0003J%\u0000\u017c"
        + "\u017d\u0005K\u0000\u0000\u017d\u017f\u0003J%\u0000\u017e\u017c\u0001"
        + "\u0000\u0000\u0000\u017f\u0182\u0001\u0000\u0000\u0000\u0180\u017e\u0001"
        + "\u0000\u0000\u0000\u0180\u0181\u0001\u0000\u0000\u0000\u0181\u0184\u0001"
        + "\u0000\u0000\u0000\u0182\u0180\u0001\u0000\u0000\u0000\u0183\u0171\u0001"
        + "\u0000\u0000\u0000\u0183\u017b\u0001\u0000\u0000\u0000\u0184/\u0001\u0000"
        + "\u0000\u0000\u0185\u0186\u0005!\u0000\u0000\u0186\u0192\u00055\u0000\u0000"
        + "\u0187\u018c\u00032\u0019\u0000\u0188\u0189\u0005I\u0000\u0000\u0189\u018b"
        + "\u00032\u0019\u0000\u018a\u0188\u0001\u0000\u0000\u0000\u018b\u018e\u0001"
        + "\u0000\u0000\u0000\u018c\u018a\u0001\u0000\u0000\u0000\u018c\u018d\u0001"
        + "\u0000\u0000\u0000\u018d\u018f\u0001\u0000\u0000\u0000\u018e\u018c\u0001"
        + "\u0000\u0000\u0000\u018f\u0190\u00055\u0000\u0000\u0190\u0192\u0001\u0000"
        + "\u0000\u0000\u0191\u0185\u0001\u0000\u0000\u0000\u0191\u0187\u0001\u0000"
        + "\u0000\u0000\u01921\u0001\u0000\u0000\u0000\u0193\u0194\u0007\u0002\u0000"
        + "\u0000\u01943\u0001\u0000\u0000\u0000\u0195\u0196\u0007\u0003\u0000\u0000"
        + "\u0196\u0197\u00036\u001b\u0000\u01975\u0001\u0000\u0000\u0000\u0198\u019d"
        + "\u00038\u001c\u0000\u0199\u019a\u0005I\u0000\u0000\u019a\u019c\u00038"
        + "\u001c\u0000\u019b\u0199\u0001\u0000\u0000\u0000\u019c\u019f\u0001\u0000"
        + "\u0000\u0000\u019d\u019b\u0001\u0000\u0000\u0000\u019d\u019e\u0001\u0000"
        + "\u0000\u0000\u019e7\u0001\u0000\u0000\u0000\u019f\u019d\u0001\u0000\u0000"
        + "\u0000\u01a0\u01a3\u0003N\'\u0000\u01a1\u01a2\u0005L\u0000\u0000\u01a2"
        + "\u01a4\u0003N\'\u0000\u01a3\u01a1\u0001\u0000\u0000\u0000\u01a3\u01a4"
        + "\u0001\u0000\u0000\u0000\u01a49\u0001\u0000\u0000\u0000\u01a5\u01a6\u0005"
        + "\u0001\u0000\u0000\u01a6\u01a7\u0003<\u001e\u0000\u01a7\u01a8\u0005\u0002"
        + "\u0000\u0000\u01a8\u01b1\u0003\b\u0004\u0000\u01a9\u01aa\u0005\u0003\u0000"
        + "\u0000\u01aa\u01ab\u0005\u0001\u0000\u0000\u01ab\u01ac\u0003<\u001e\u0000"
        + "\u01ac\u01ad\u0005\u0002\u0000\u0000\u01ad\u01ae\u0003\b\u0004\u0000\u01ae"
        + "\u01b0\u0001\u0000\u0000\u0000\u01af\u01a9\u0001\u0000\u0000\u0000\u01b0"
        + "\u01b3\u0001\u0000\u0000\u0000\u01b1\u01af\u0001\u0000\u0000\u0000\u01b1"
        + "\u01b2\u0001\u0000\u0000\u0000\u01b2\u01b6\u0001\u0000\u0000\u0000\u01b3"
        + "\u01b1\u0001\u0000\u0000\u0000\u01b4\u01b5\u0005\u0003\u0000\u0000\u01b5"
        + "\u01b7\u0003\b\u0004\u0000\u01b6\u01b4\u0001\u0000\u0000\u0000\u01b6\u01b7"
        + "\u0001\u0000\u0000\u0000\u01b7\u01b8\u0001\u0000\u0000\u0000\u01b8\u01b9"
        + "\u0005F\u0000\u0000\u01b9;\u0001\u0000\u0000\u0000\u01ba\u01bb\u0006\u001e"
        + "\uffff\uffff\u0000\u01bb\u01eb\u0005\n\u0000\u0000\u01bc\u01eb\u0005\u000b"
        + "\u0000\u0000\u01bd\u01be\u0005M\u0000\u0000\u01be\u01bf\u0003<\u001e\u0000"
        + "\u01bf\u01c0\u0005N\u0000\u0000\u01c0\u01eb\u0001\u0000\u0000\u0000\u01c1"
        + "\u01c2\u0005\f\u0000\u0000\u01c2\u01eb\u0003<\u001e\u0006\u01c3\u01c5"
        + "\u0003@ \u0000\u01c4\u01c3\u0001\u0000\u0000\u0000\u01c4\u01c5\u0001\u0000"
        + "\u0000\u0000\u01c5\u01c6\u0001\u0000\u0000\u0000\u01c6\u01c7\u0003B!\u0000"
        + "\u01c7\u01c8\u0005\u0004\u0000\u0000\u01c8\u01c9\u0003>\u001f\u0000\u01c9"
        + "\u01cb\u0003N\'\u0000\u01ca\u01cc\u0003(\u0014\u0000\u01cb\u01ca\u0001"
        + "\u0000\u0000\u0000\u01cb\u01cc\u0001\u0000\u0000\u0000\u01cc\u01ce\u0001"
        + "\u0000\u0000\u0000\u01cd\u01cf\u0003*\u0015\u0000\u01ce\u01cd\u0001\u0000"
        + "\u0000\u0000\u01ce\u01cf\u0001\u0000\u0000\u0000\u01cf\u01d2\u0001\u0000"
        + "\u0000\u0000\u01d0\u01d1\u0005\"\u0000\u0000\u01d1\u01d3\u0003&\u0013"
        + "\u0000\u01d2\u01d0\u0001\u0000\u0000\u0000\u01d2\u01d3\u0001\u0000\u0000"
        + "\u0000\u01d3\u01eb\u0001\u0000\u0000\u0000\u01d4\u01d6\u0005A\u0000\u0000"
        + "\u01d5\u01d7\u0007\u0001\u0000\u0000\u01d6\u01d5\u0001\u0000\u0000\u0000"
        + "\u01d6\u01d7\u0001\u0000\u0000\u0000\u01d7\u01db\u0001\u0000\u0000\u0000"
        + "\u01d8\u01d9\u0003>\u001f\u0000\u01d9\u01da\u0003N\'\u0000\u01da\u01dc"
        + "\u0001\u0000\u0000\u0000\u01db\u01d8\u0001\u0000\u0000\u0000\u01db\u01dc"
        + "\u0001\u0000\u0000\u0000\u01dc\u01eb\u0001\u0000\u0000\u0000\u01dd\u01df"
        + "\u0003@ \u0000\u01de\u01dd\u0001\u0000\u0000\u0000\u01de\u01df\u0001\u0000"
        + "\u0000\u0000\u01df\u01e0\u0001\u0000\u0000\u0000\u01e0\u01e1\u0003B!\u0000"
        + "\u01e1\u01e3\u0005A\u0000\u0000\u01e2\u01e4\u0007\u0001\u0000\u0000\u01e3"
        + "\u01e2\u0001\u0000\u0000\u0000\u01e3\u01e4\u0001\u0000\u0000\u0000\u01e4"
        + "\u01e8\u0001\u0000\u0000\u0000\u01e5\u01e6\u0003>\u001f\u0000\u01e6\u01e7"
        + "\u0003N\'\u0000\u01e7\u01e9\u0001\u0000\u0000\u0000\u01e8\u01e5\u0001"
        + "\u0000\u0000\u0000\u01e8\u01e9\u0001\u0000\u0000\u0000\u01e9\u01eb\u0001"
        + "\u0000\u0000\u0000\u01ea\u01ba\u0001\u0000\u0000\u0000\u01ea\u01bc\u0001"
        + "\u0000\u0000\u0000\u01ea\u01bd\u0001\u0000\u0000\u0000\u01ea\u01c1\u0001"
        + "\u0000\u0000\u0000\u01ea\u01c4\u0001\u0000\u0000\u0000\u01ea\u01d4\u0001"
        + "\u0000\u0000\u0000\u01ea\u01de\u0001\u0000\u0000\u0000\u01eb\u01f4\u0001"
        + "\u0000\u0000\u0000\u01ec\u01ed\n\u0005\u0000\u0000\u01ed\u01ee\u0005\r"
        + "\u0000\u0000\u01ee\u01f3\u0003<\u001e\u0006\u01ef\u01f0\n\u0004\u0000"
        + "\u0000\u01f0\u01f1\u0005\u000e\u0000\u0000\u01f1\u01f3\u0003<\u001e\u0005"
        + "\u01f2\u01ec\u0001\u0000\u0000\u0000\u01f2\u01ef\u0001\u0000\u0000\u0000"
        + "\u01f3\u01f6\u0001\u0000\u0000\u0000\u01f4\u01f2\u0001\u0000\u0000\u0000"
        + "\u01f4\u01f5\u0001\u0000\u0000\u0000\u01f5=\u0001\u0000\u0000\u0000\u01f6"
        + "\u01f4\u0001\u0000\u0000\u0000\u01f7\u01f8\u0007\u0004\u0000\u0000\u01f8"
        + "?\u0001\u0000\u0000\u0000\u01f9\u01fa\u0007\u0005\u0000\u0000\u01faA\u0001"
        + "\u0000\u0000\u0000\u01fb\u0200\u0003F#\u0000\u01fc\u01fd\u0005I\u0000"
        + "\u0000\u01fd\u01ff\u0003F#\u0000\u01fe\u01fc\u0001\u0000\u0000\u0000\u01ff"
        + "\u0202\u0001\u0000\u0000\u0000\u0200\u01fe\u0001\u0000\u0000\u0000\u0200"
        + "\u0201\u0001\u0000\u0000\u0000\u0201\u0204\u0001\u0000\u0000\u0000\u0202"
        + "\u0200\u0001\u0000\u0000\u0000\u0203\u0205\u0003D\"\u0000\u0204\u0203"
        + "\u0001\u0000\u0000\u0000\u0204\u0205\u0001\u0000\u0000\u0000\u0205\u0207"
        + "\u0001\u0000\u0000\u0000\u0206\u0208\u00030\u0018\u0000\u0207\u0206\u0001"
        + "\u0000\u0000\u0000\u0207\u0208\u0001\u0000\u0000\u0000\u0208\u020a\u0001"
        + "\u0000\u0000\u0000\u0209\u020b\u00034\u001a\u0000\u020a\u0209\u0001\u0000"
        + "\u0000\u0000\u020a\u020b\u0001\u0000\u0000\u0000\u020bC\u0001\u0000\u0000"
        + "\u0000\u020c\u020d\u0005*\u0000\u0000\u020d\u020e\u0005+\u0000\u0000\u020e"
        + "\u020f\u0005,\u0000\u0000\u020f\u0210\u0007\u0006\u0000\u0000\u0210E\u0001"
        + "\u0000\u0000\u0000\u0211\u0214\u0003J%\u0000\u0212\u0214\u0003L&\u0000"
        + "\u0213\u0211\u0001\u0000\u0000\u0000\u0213\u0212\u0001\u0000\u0000\u0000"
        + "\u0214G\u0001\u0000\u0000\u0000\u0215\u0216\u0005$\u0000\u0000\u0216\u0217"
        + "\u0007\u0003\u0000\u0000\u0217\u0218\u0005%\u0000\u0000\u0218I\u0001\u0000"
        + "\u0000\u0000\u0219\u021a\u0007\u0007\u0000\u0000\u021aK\u0001\u0000\u0000"
        + "\u0000\u021b\u021c\u0005R\u0000\u0000\u021cM\u0001\u0000\u0000\u0000\u021d"
        + "\u021e\u0005P\u0000\u0000\u021eO\u0001\u0000\u0000\u0000XQVknqu{~\u0083"
        + "\u008b\u008f\u0094\u0098\u009d\u00a2\u00a7\u00a9\u00ad\u00b3\u00b7\u00bb"
        + "\u00be\u00c2\u00c7\u00cc\u00cf\u00d1\u00d5\u00d8\u00dc\u00df\u00e4\u00e7"
        + "\u00ec\u00ef\u00f1\u00fc\u0100\u0103\u0107\u010b\u010e\u0115\u0119\u011e"
        + "\u0126\u012a\u012e\u0130\u0132\u0134\u0137\u013e\u0142\u0149\u014d\u0153"
        + "\u015e\u0161\u0164\u016c\u016e\u0178\u0180\u0183\u018c\u0191\u019d\u01a3"
        + "\u01b1\u01b6\u01c4\u01cb\u01ce\u01d2\u01d6\u01db\u01de\u01e3\u01e8\u01ea"
        + "\u01f2\u01f4\u0200\u0204\u0207\u020a\u0213";
    public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}
