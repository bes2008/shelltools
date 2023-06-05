// Generated from Gradle.g4 by ANTLR 4.7
package com.jn.shelltools.supports.gradle.generated;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GradleParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		CLRF=1, SPACE=2, NUM=3, BOOL=4, STRING=5, COMMENT=6, NULL=7, COLON=8, 
		EQUALS=9, COMMA=10, MIDDLE_BRACE_START=11, MIDDLE_BRACE_END=12, SYMBOL=13, 
		BIG_BRACE_START=14, BIG_BRACE_END=15, SMALL_BRACE_START=16, SMALL_BRACE_END=17;
	public static final int
		RULE_simpleKey = 0, RULE_simpleKeyValuePair = 1, RULE_simpleKeyValuePairs = 2, 
		RULE_stringToClosurePair = 3, RULE_simpleValue = 4, RULE_sequence = 5, 
		RULE_value = 6, RULE_array = 7, RULE_funcName = 8, RULE_var = 9, RULE_defineVariable = 10, 
		RULE_groovyStatement = 11, RULE_closureBody = 12, RULE_closure = 13, RULE_funcInvocation = 14, 
		RULE_program = 15;
	public static final String[] ruleNames = {
		"simpleKey", "simpleKeyValuePair", "simpleKeyValuePairs", "stringToClosurePair", 
		"simpleValue", "sequence", "value", "array", "funcName", "var", "defineVariable", 
		"groovyStatement", "closureBody", "closure", "funcInvocation", "program"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, null, null, null, null, "'null'", "':'", "'='", "','", 
		"'['", "']'", null, "'{'", "'}'", "'('", "')'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "CLRF", "SPACE", "NUM", "BOOL", "STRING", "COMMENT", "NULL", "COLON", 
		"EQUALS", "COMMA", "MIDDLE_BRACE_START", "MIDDLE_BRACE_END", "SYMBOL", 
		"BIG_BRACE_START", "BIG_BRACE_END", "SMALL_BRACE_START", "SMALL_BRACE_END"
	};
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
	public String getGrammarFileName() { return "Gradle.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public GradleParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class SimpleKeyContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(GradleParser.STRING, 0); }
		public TerminalNode SYMBOL() { return getToken(GradleParser.SYMBOL, 0); }
		public SimpleKeyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleKey; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).enterSimpleKey(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).exitSimpleKey(this);
		}
	}

	public final SimpleKeyContext simpleKey() throws RecognitionException {
		SimpleKeyContext _localctx = new SimpleKeyContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_simpleKey);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(32);
			_la = _input.LA(1);
			if ( !(_la==STRING || _la==SYMBOL) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SimpleKeyValuePairContext extends ParserRuleContext {
		public SimpleKeyContext simpleKey() {
			return getRuleContext(SimpleKeyContext.class,0);
		}
		public TerminalNode COLON() { return getToken(GradleParser.COLON, 0); }
		public SimpleValueContext simpleValue() {
			return getRuleContext(SimpleValueContext.class,0);
		}
		public List<TerminalNode> SPACE() { return getTokens(GradleParser.SPACE); }
		public TerminalNode SPACE(int i) {
			return getToken(GradleParser.SPACE, i);
		}
		public SimpleKeyValuePairContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleKeyValuePair; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).enterSimpleKeyValuePair(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).exitSimpleKeyValuePair(this);
		}
	}

	public final SimpleKeyValuePairContext simpleKeyValuePair() throws RecognitionException {
		SimpleKeyValuePairContext _localctx = new SimpleKeyValuePairContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_simpleKeyValuePair);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			simpleKey();
			setState(36);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SPACE) {
				{
				setState(35);
				match(SPACE);
				}
			}

			setState(38);
			match(COLON);
			setState(40);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SPACE) {
				{
				setState(39);
				match(SPACE);
				}
			}

			setState(42);
			simpleValue();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SimpleKeyValuePairsContext extends ParserRuleContext {
		public List<SimpleKeyValuePairContext> simpleKeyValuePair() {
			return getRuleContexts(SimpleKeyValuePairContext.class);
		}
		public SimpleKeyValuePairContext simpleKeyValuePair(int i) {
			return getRuleContext(SimpleKeyValuePairContext.class,i);
		}
		public List<TerminalNode> SPACE() { return getTokens(GradleParser.SPACE); }
		public TerminalNode SPACE(int i) {
			return getToken(GradleParser.SPACE, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GradleParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GradleParser.COMMA, i);
		}
		public SimpleKeyValuePairsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleKeyValuePairs; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).enterSimpleKeyValuePairs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).exitSimpleKeyValuePairs(this);
		}
	}

	public final SimpleKeyValuePairsContext simpleKeyValuePairs() throws RecognitionException {
		SimpleKeyValuePairsContext _localctx = new SimpleKeyValuePairsContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_simpleKeyValuePairs);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(45);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SPACE) {
				{
				setState(44);
				match(SPACE);
				}
			}

			setState(47);
			simpleKeyValuePair();
			setState(58);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(49);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==SPACE) {
						{
						setState(48);
						match(SPACE);
						}
					}

					setState(51);
					match(COMMA);
					setState(53);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==SPACE) {
						{
						setState(52);
						match(SPACE);
						}
					}

					setState(55);
					simpleKeyValuePair();
					}
					} 
				}
				setState(60);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StringToClosurePairContext extends ParserRuleContext {
		public SimpleKeyContext simpleKey() {
			return getRuleContext(SimpleKeyContext.class,0);
		}
		public TerminalNode SPACE() { return getToken(GradleParser.SPACE, 0); }
		public ClosureContext closure() {
			return getRuleContext(ClosureContext.class,0);
		}
		public StringToClosurePairContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stringToClosurePair; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).enterStringToClosurePair(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).exitStringToClosurePair(this);
		}
	}

	public final StringToClosurePairContext stringToClosurePair() throws RecognitionException {
		StringToClosurePairContext _localctx = new StringToClosurePairContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_stringToClosurePair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(61);
			simpleKey();
			setState(62);
			match(SPACE);
			setState(63);
			closure();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SimpleValueContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(GradleParser.STRING, 0); }
		public TerminalNode NUM() { return getToken(GradleParser.NUM, 0); }
		public TerminalNode BOOL() { return getToken(GradleParser.BOOL, 0); }
		public TerminalNode NULL() { return getToken(GradleParser.NULL, 0); }
		public SimpleValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).enterSimpleValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).exitSimpleValue(this);
		}
	}

	public final SimpleValueContext simpleValue() throws RecognitionException {
		SimpleValueContext _localctx = new SimpleValueContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_simpleValue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(65);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NUM) | (1L << BOOL) | (1L << STRING) | (1L << NULL))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SequenceContext extends ParserRuleContext {
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public List<StringToClosurePairContext> stringToClosurePair() {
			return getRuleContexts(StringToClosurePairContext.class);
		}
		public StringToClosurePairContext stringToClosurePair(int i) {
			return getRuleContext(StringToClosurePairContext.class,i);
		}
		public List<TerminalNode> SPACE() { return getTokens(GradleParser.SPACE); }
		public TerminalNode SPACE(int i) {
			return getToken(GradleParser.SPACE, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GradleParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GradleParser.COMMA, i);
		}
		public SequenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sequence; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).enterSequence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).exitSequence(this);
		}
	}

	public final SequenceContext sequence() throws RecognitionException {
		SequenceContext _localctx = new SequenceContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_sequence);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(68);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				{
				setState(67);
				match(SPACE);
				}
				break;
			}
			setState(72);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				{
				setState(70);
				value();
				}
				break;
			case 2:
				{
				setState(71);
				stringToClosurePair();
				}
				break;
			}
			setState(87);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(75);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==SPACE) {
						{
						setState(74);
						match(SPACE);
						}
					}

					setState(77);
					match(COMMA);
					setState(79);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
					case 1:
						{
						setState(78);
						match(SPACE);
						}
						break;
					}
					setState(83);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
					case 1:
						{
						setState(81);
						value();
						}
						break;
					case 2:
						{
						setState(82);
						stringToClosurePair();
						}
						break;
					}
					}
					} 
				}
				setState(89);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValueContext extends ParserRuleContext {
		public SimpleValueContext simpleValue() {
			return getRuleContext(SimpleValueContext.class,0);
		}
		public ArrayContext array() {
			return getRuleContext(ArrayContext.class,0);
		}
		public StringToClosurePairContext stringToClosurePair() {
			return getRuleContext(StringToClosurePairContext.class,0);
		}
		public SimpleKeyValuePairsContext simpleKeyValuePairs() {
			return getRuleContext(SimpleKeyValuePairsContext.class,0);
		}
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).exitValue(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_value);
		try {
			setState(94);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(90);
				simpleValue();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(91);
				array();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(92);
				stringToClosurePair();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(93);
				simpleKeyValuePairs();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayContext extends ParserRuleContext {
		public TerminalNode MIDDLE_BRACE_START() { return getToken(GradleParser.MIDDLE_BRACE_START, 0); }
		public TerminalNode MIDDLE_BRACE_END() { return getToken(GradleParser.MIDDLE_BRACE_END, 0); }
		public List<TerminalNode> SPACE() { return getTokens(GradleParser.SPACE); }
		public TerminalNode SPACE(int i) {
			return getToken(GradleParser.SPACE, i);
		}
		public SequenceContext sequence() {
			return getRuleContext(SequenceContext.class,0);
		}
		public ArrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_array; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).enterArray(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).exitArray(this);
		}
	}

	public final ArrayContext array() throws RecognitionException {
		ArrayContext _localctx = new ArrayContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_array);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
			match(MIDDLE_BRACE_START);
			setState(98);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				{
				setState(97);
				match(SPACE);
				}
				break;
			}
			setState(101);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				{
				setState(100);
				sequence();
				}
				break;
			}
			setState(104);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SPACE) {
				{
				setState(103);
				match(SPACE);
				}
			}

			setState(106);
			match(MIDDLE_BRACE_END);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FuncNameContext extends ParserRuleContext {
		public TerminalNode SYMBOL() { return getToken(GradleParser.SYMBOL, 0); }
		public FuncNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).enterFuncName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).exitFuncName(this);
		}
	}

	public final FuncNameContext funcName() throws RecognitionException {
		FuncNameContext _localctx = new FuncNameContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_funcName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(108);
			match(SYMBOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarContext extends ParserRuleContext {
		public TerminalNode SYMBOL() { return getToken(GradleParser.SYMBOL, 0); }
		public VarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).enterVar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).exitVar(this);
		}
	}

	public final VarContext var() throws RecognitionException {
		VarContext _localctx = new VarContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_var);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(110);
			match(SYMBOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DefineVariableContext extends ParserRuleContext {
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public TerminalNode EQUALS() { return getToken(GradleParser.EQUALS, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public List<TerminalNode> SPACE() { return getTokens(GradleParser.SPACE); }
		public TerminalNode SPACE(int i) {
			return getToken(GradleParser.SPACE, i);
		}
		public DefineVariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_defineVariable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).enterDefineVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).exitDefineVariable(this);
		}
	}

	public final DefineVariableContext defineVariable() throws RecognitionException {
		DefineVariableContext _localctx = new DefineVariableContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_defineVariable);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SPACE) {
				{
				setState(112);
				match(SPACE);
				}
			}

			setState(115);
			var();
			setState(117);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SPACE) {
				{
				setState(116);
				match(SPACE);
				}
			}

			setState(119);
			match(EQUALS);
			setState(121);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				setState(120);
				match(SPACE);
				}
				break;
			}
			setState(123);
			value();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GroovyStatementContext extends ParserRuleContext {
		public DefineVariableContext defineVariable() {
			return getRuleContext(DefineVariableContext.class,0);
		}
		public FuncInvocationContext funcInvocation() {
			return getRuleContext(FuncInvocationContext.class,0);
		}
		public GroovyStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groovyStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).enterGroovyStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).exitGroovyStatement(this);
		}
	}

	public final GroovyStatementContext groovyStatement() throws RecognitionException {
		GroovyStatementContext _localctx = new GroovyStatementContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_groovyStatement);
		try {
			setState(127);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(125);
				defineVariable();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(126);
				funcInvocation();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClosureBodyContext extends ParserRuleContext {
		public List<GroovyStatementContext> groovyStatement() {
			return getRuleContexts(GroovyStatementContext.class);
		}
		public GroovyStatementContext groovyStatement(int i) {
			return getRuleContext(GroovyStatementContext.class,i);
		}
		public ClosureBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_closureBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).enterClosureBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).exitClosureBody(this);
		}
	}

	public final ClosureBodyContext closureBody() throws RecognitionException {
		ClosureBodyContext _localctx = new ClosureBodyContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_closureBody);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(132);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(129);
					groovyStatement();
					}
					} 
				}
				setState(134);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClosureContext extends ParserRuleContext {
		public TerminalNode BIG_BRACE_START() { return getToken(GradleParser.BIG_BRACE_START, 0); }
		public ClosureBodyContext closureBody() {
			return getRuleContext(ClosureBodyContext.class,0);
		}
		public TerminalNode BIG_BRACE_END() { return getToken(GradleParser.BIG_BRACE_END, 0); }
		public List<TerminalNode> SPACE() { return getTokens(GradleParser.SPACE); }
		public TerminalNode SPACE(int i) {
			return getToken(GradleParser.SPACE, i);
		}
		public ClosureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_closure; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).enterClosure(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).exitClosure(this);
		}
	}

	public final ClosureContext closure() throws RecognitionException {
		ClosureContext _localctx = new ClosureContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_closure);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(135);
			match(BIG_BRACE_START);
			setState(137);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				{
				setState(136);
				match(SPACE);
				}
				break;
			}
			setState(139);
			closureBody();
			setState(141);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SPACE) {
				{
				setState(140);
				match(SPACE);
				}
			}

			setState(143);
			match(BIG_BRACE_END);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FuncInvocationContext extends ParserRuleContext {
		public FuncNameContext funcName() {
			return getRuleContext(FuncNameContext.class,0);
		}
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public TerminalNode SMALL_BRACE_START() { return getToken(GradleParser.SMALL_BRACE_START, 0); }
		public TerminalNode SMALL_BRACE_END() { return getToken(GradleParser.SMALL_BRACE_END, 0); }
		public List<TerminalNode> SPACE() { return getTokens(GradleParser.SPACE); }
		public TerminalNode SPACE(int i) {
			return getToken(GradleParser.SPACE, i);
		}
		public ClosureContext closure() {
			return getRuleContext(ClosureContext.class,0);
		}
		public SequenceContext sequence() {
			return getRuleContext(SequenceContext.class,0);
		}
		public FuncInvocationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcInvocation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).enterFuncInvocation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).exitFuncInvocation(this);
		}
	}

	public final FuncInvocationContext funcInvocation() throws RecognitionException {
		FuncInvocationContext _localctx = new FuncInvocationContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_funcInvocation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(178);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				{
				setState(146);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SPACE) {
					{
					setState(145);
					match(SPACE);
					}
				}

				setState(148);
				funcName();
				setState(150);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
				case 1:
					{
					setState(149);
					match(SPACE);
					}
					break;
				}
				setState(152);
				value();
				}
				break;
			case 2:
				{
				setState(155);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SPACE) {
					{
					setState(154);
					match(SPACE);
					}
				}

				setState(157);
				funcName();
				setState(159);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SPACE) {
					{
					setState(158);
					match(SPACE);
					}
				}

				setState(161);
				match(SMALL_BRACE_START);
				setState(163);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
				case 1:
					{
					setState(162);
					match(SPACE);
					}
					break;
				}
				setState(167);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
				case 1:
					{
					setState(165);
					value();
					}
					break;
				case 2:
					{
					setState(166);
					sequence();
					}
					break;
				}
				setState(170);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SPACE) {
					{
					setState(169);
					match(SPACE);
					}
				}

				setState(172);
				match(SMALL_BRACE_END);
				}
				break;
			case 3:
				{
				setState(175);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SPACE) {
					{
					setState(174);
					match(SPACE);
					}
				}

				setState(177);
				funcName();
				}
				break;
			}
			setState(181);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
			case 1:
				{
				setState(180);
				match(SPACE);
				}
				break;
			}
			setState(184);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==BIG_BRACE_START) {
				{
				setState(183);
				closure();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProgramContext extends ParserRuleContext {
		public List<DefineVariableContext> defineVariable() {
			return getRuleContexts(DefineVariableContext.class);
		}
		public DefineVariableContext defineVariable(int i) {
			return getRuleContext(DefineVariableContext.class,i);
		}
		public List<FuncInvocationContext> funcInvocation() {
			return getRuleContexts(FuncInvocationContext.class);
		}
		public FuncInvocationContext funcInvocation(int i) {
			return getRuleContext(FuncInvocationContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).exitProgram(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(188); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(188);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
				case 1:
					{
					setState(186);
					defineVariable();
					}
					break;
				case 2:
					{
					setState(187);
					funcInvocation();
					}
					break;
				}
				}
				setState(190); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SPACE || _la==SYMBOL );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\23\u00c3\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\3\2\3\2"+
		"\3\3\3\3\5\3\'\n\3\3\3\3\3\5\3+\n\3\3\3\3\3\3\4\5\4\60\n\4\3\4\3\4\5\4"+
		"\64\n\4\3\4\3\4\5\48\n\4\3\4\7\4;\n\4\f\4\16\4>\13\4\3\5\3\5\3\5\3\5\3"+
		"\6\3\6\3\7\5\7G\n\7\3\7\3\7\5\7K\n\7\3\7\5\7N\n\7\3\7\3\7\5\7R\n\7\3\7"+
		"\3\7\5\7V\n\7\7\7X\n\7\f\7\16\7[\13\7\3\b\3\b\3\b\3\b\5\ba\n\b\3\t\3\t"+
		"\5\te\n\t\3\t\5\th\n\t\3\t\5\tk\n\t\3\t\3\t\3\n\3\n\3\13\3\13\3\f\5\f"+
		"t\n\f\3\f\3\f\5\fx\n\f\3\f\3\f\5\f|\n\f\3\f\3\f\3\r\3\r\5\r\u0082\n\r"+
		"\3\16\7\16\u0085\n\16\f\16\16\16\u0088\13\16\3\17\3\17\5\17\u008c\n\17"+
		"\3\17\3\17\5\17\u0090\n\17\3\17\3\17\3\20\5\20\u0095\n\20\3\20\3\20\5"+
		"\20\u0099\n\20\3\20\3\20\3\20\5\20\u009e\n\20\3\20\3\20\5\20\u00a2\n\20"+
		"\3\20\3\20\5\20\u00a6\n\20\3\20\3\20\5\20\u00aa\n\20\3\20\5\20\u00ad\n"+
		"\20\3\20\3\20\3\20\5\20\u00b2\n\20\3\20\5\20\u00b5\n\20\3\20\5\20\u00b8"+
		"\n\20\3\20\5\20\u00bb\n\20\3\21\3\21\6\21\u00bf\n\21\r\21\16\21\u00c0"+
		"\3\21\3<\2\22\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \2\4\4\2\7\7\17\17"+
		"\4\2\5\7\t\t\2\u00da\2\"\3\2\2\2\4$\3\2\2\2\6/\3\2\2\2\b?\3\2\2\2\nC\3"+
		"\2\2\2\fF\3\2\2\2\16`\3\2\2\2\20b\3\2\2\2\22n\3\2\2\2\24p\3\2\2\2\26s"+
		"\3\2\2\2\30\u0081\3\2\2\2\32\u0086\3\2\2\2\34\u0089\3\2\2\2\36\u00b4\3"+
		"\2\2\2 \u00be\3\2\2\2\"#\t\2\2\2#\3\3\2\2\2$&\5\2\2\2%\'\7\4\2\2&%\3\2"+
		"\2\2&\'\3\2\2\2\'(\3\2\2\2(*\7\n\2\2)+\7\4\2\2*)\3\2\2\2*+\3\2\2\2+,\3"+
		"\2\2\2,-\5\n\6\2-\5\3\2\2\2.\60\7\4\2\2/.\3\2\2\2/\60\3\2\2\2\60\61\3"+
		"\2\2\2\61<\5\4\3\2\62\64\7\4\2\2\63\62\3\2\2\2\63\64\3\2\2\2\64\65\3\2"+
		"\2\2\65\67\7\f\2\2\668\7\4\2\2\67\66\3\2\2\2\678\3\2\2\289\3\2\2\29;\5"+
		"\4\3\2:\63\3\2\2\2;>\3\2\2\2<=\3\2\2\2<:\3\2\2\2=\7\3\2\2\2><\3\2\2\2"+
		"?@\5\2\2\2@A\7\4\2\2AB\5\34\17\2B\t\3\2\2\2CD\t\3\2\2D\13\3\2\2\2EG\7"+
		"\4\2\2FE\3\2\2\2FG\3\2\2\2GJ\3\2\2\2HK\5\16\b\2IK\5\b\5\2JH\3\2\2\2JI"+
		"\3\2\2\2KY\3\2\2\2LN\7\4\2\2ML\3\2\2\2MN\3\2\2\2NO\3\2\2\2OQ\7\f\2\2P"+
		"R\7\4\2\2QP\3\2\2\2QR\3\2\2\2RU\3\2\2\2SV\5\16\b\2TV\5\b\5\2US\3\2\2\2"+
		"UT\3\2\2\2VX\3\2\2\2WM\3\2\2\2X[\3\2\2\2YW\3\2\2\2YZ\3\2\2\2Z\r\3\2\2"+
		"\2[Y\3\2\2\2\\a\5\n\6\2]a\5\20\t\2^a\5\b\5\2_a\5\6\4\2`\\\3\2\2\2`]\3"+
		"\2\2\2`^\3\2\2\2`_\3\2\2\2a\17\3\2\2\2bd\7\r\2\2ce\7\4\2\2dc\3\2\2\2d"+
		"e\3\2\2\2eg\3\2\2\2fh\5\f\7\2gf\3\2\2\2gh\3\2\2\2hj\3\2\2\2ik\7\4\2\2"+
		"ji\3\2\2\2jk\3\2\2\2kl\3\2\2\2lm\7\16\2\2m\21\3\2\2\2no\7\17\2\2o\23\3"+
		"\2\2\2pq\7\17\2\2q\25\3\2\2\2rt\7\4\2\2sr\3\2\2\2st\3\2\2\2tu\3\2\2\2"+
		"uw\5\24\13\2vx\7\4\2\2wv\3\2\2\2wx\3\2\2\2xy\3\2\2\2y{\7\13\2\2z|\7\4"+
		"\2\2{z\3\2\2\2{|\3\2\2\2|}\3\2\2\2}~\5\16\b\2~\27\3\2\2\2\177\u0082\5"+
		"\26\f\2\u0080\u0082\5\36\20\2\u0081\177\3\2\2\2\u0081\u0080\3\2\2\2\u0082"+
		"\31\3\2\2\2\u0083\u0085\5\30\r\2\u0084\u0083\3\2\2\2\u0085\u0088\3\2\2"+
		"\2\u0086\u0084\3\2\2\2\u0086\u0087\3\2\2\2\u0087\33\3\2\2\2\u0088\u0086"+
		"\3\2\2\2\u0089\u008b\7\20\2\2\u008a\u008c\7\4\2\2\u008b\u008a\3\2\2\2"+
		"\u008b\u008c\3\2\2\2\u008c\u008d\3\2\2\2\u008d\u008f\5\32\16\2\u008e\u0090"+
		"\7\4\2\2\u008f\u008e\3\2\2\2\u008f\u0090\3\2\2\2\u0090\u0091\3\2\2\2\u0091"+
		"\u0092\7\21\2\2\u0092\35\3\2\2\2\u0093\u0095\7\4\2\2\u0094\u0093\3\2\2"+
		"\2\u0094\u0095\3\2\2\2\u0095\u0096\3\2\2\2\u0096\u0098\5\22\n\2\u0097"+
		"\u0099\7\4\2\2\u0098\u0097\3\2\2\2\u0098\u0099\3\2\2\2\u0099\u009a\3\2"+
		"\2\2\u009a\u009b\5\16\b\2\u009b\u00b5\3\2\2\2\u009c\u009e\7\4\2\2\u009d"+
		"\u009c\3\2\2\2\u009d\u009e\3\2\2\2\u009e\u009f\3\2\2\2\u009f\u00a1\5\22"+
		"\n\2\u00a0\u00a2\7\4\2\2\u00a1\u00a0\3\2\2\2\u00a1\u00a2\3\2\2\2\u00a2"+
		"\u00a3\3\2\2\2\u00a3\u00a5\7\22\2\2\u00a4\u00a6\7\4\2\2\u00a5\u00a4\3"+
		"\2\2\2\u00a5\u00a6\3\2\2\2\u00a6\u00a9\3\2\2\2\u00a7\u00aa\5\16\b\2\u00a8"+
		"\u00aa\5\f\7\2\u00a9\u00a7\3\2\2\2\u00a9\u00a8\3\2\2\2\u00a9\u00aa\3\2"+
		"\2\2\u00aa\u00ac\3\2\2\2\u00ab\u00ad\7\4\2\2\u00ac\u00ab\3\2\2\2\u00ac"+
		"\u00ad\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae\u00af\7\23\2\2\u00af\u00b5\3"+
		"\2\2\2\u00b0\u00b2\7\4\2\2\u00b1\u00b0\3\2\2\2\u00b1\u00b2\3\2\2\2\u00b2"+
		"\u00b3\3\2\2\2\u00b3\u00b5\5\22\n\2\u00b4\u0094\3\2\2\2\u00b4\u009d\3"+
		"\2\2\2\u00b4\u00b1\3\2\2\2\u00b5\u00b7\3\2\2\2\u00b6\u00b8\7\4\2\2\u00b7"+
		"\u00b6\3\2\2\2\u00b7\u00b8\3\2\2\2\u00b8\u00ba\3\2\2\2\u00b9\u00bb\5\34"+
		"\17\2\u00ba\u00b9\3\2\2\2\u00ba\u00bb\3\2\2\2\u00bb\37\3\2\2\2\u00bc\u00bf"+
		"\5\26\f\2\u00bd\u00bf\5\36\20\2\u00be\u00bc\3\2\2\2\u00be\u00bd\3\2\2"+
		"\2\u00bf\u00c0\3\2\2\2\u00c0\u00be\3\2\2\2\u00c0\u00c1\3\2\2\2\u00c1!"+
		"\3\2\2\2&&*/\63\67<FJMQUY`dgjsw{\u0081\u0086\u008b\u008f\u0094\u0098\u009d"+
		"\u00a1\u00a5\u00a9\u00ac\u00b1\u00b4\u00b7\u00ba\u00be\u00c0";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}