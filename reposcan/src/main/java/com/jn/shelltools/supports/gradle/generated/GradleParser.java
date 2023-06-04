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
		RULE_funcInvokeWithoutClosure = 11, RULE_groovyStatement = 12, RULE_closureBody = 13, 
		RULE_closure = 14, RULE_funcInvoke = 15, RULE_program = 16;
	public static final String[] ruleNames = {
		"simpleKey", "simpleKeyValuePair", "simpleKeyValuePairs", "stringToClosurePair", 
		"simpleValue", "sequence", "value", "array", "funcName", "var", "defineVariable", 
		"funcInvokeWithoutClosure", "groovyStatement", "closureBody", "closure", 
		"funcInvoke", "program"
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
			setState(34);
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
			setState(36);
			simpleKey();
			setState(38);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SPACE) {
				{
				setState(37);
				match(SPACE);
				}
			}

			setState(40);
			match(COLON);
			setState(42);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SPACE) {
				{
				setState(41);
				match(SPACE);
				}
			}

			setState(44);
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
			setState(47);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SPACE) {
				{
				setState(46);
				match(SPACE);
				}
			}

			setState(49);
			simpleKeyValuePair();
			setState(60);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(51);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==SPACE) {
						{
						setState(50);
						match(SPACE);
						}
					}

					setState(53);
					match(COMMA);
					setState(55);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==SPACE) {
						{
						setState(54);
						match(SPACE);
						}
					}

					setState(57);
					simpleKeyValuePair();
					}
					} 
				}
				setState(62);
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
			setState(63);
			simpleKey();
			setState(64);
			match(SPACE);
			setState(65);
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
			setState(67);
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
			setState(70);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				{
				setState(69);
				match(SPACE);
				}
				break;
			}
			setState(74);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				{
				setState(72);
				value();
				}
				break;
			case 2:
				{
				setState(73);
				stringToClosurePair();
				}
				break;
			}
			setState(89);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(77);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==SPACE) {
						{
						setState(76);
						match(SPACE);
						}
					}

					setState(79);
					match(COMMA);
					setState(81);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
					case 1:
						{
						setState(80);
						match(SPACE);
						}
						break;
					}
					setState(85);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
					case 1:
						{
						setState(83);
						value();
						}
						break;
					case 2:
						{
						setState(84);
						stringToClosurePair();
						}
						break;
					}
					}
					} 
				}
				setState(91);
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
			setState(96);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(92);
				simpleValue();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(93);
				array();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(94);
				stringToClosurePair();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(95);
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
			setState(98);
			match(MIDDLE_BRACE_START);
			setState(100);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				{
				setState(99);
				match(SPACE);
				}
				break;
			}
			setState(103);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				{
				setState(102);
				sequence();
				}
				break;
			}
			setState(106);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SPACE) {
				{
				setState(105);
				match(SPACE);
				}
			}

			setState(108);
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
			setState(112);
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
			setState(115);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SPACE) {
				{
				setState(114);
				match(SPACE);
				}
			}

			setState(117);
			var();
			setState(119);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SPACE) {
				{
				setState(118);
				match(SPACE);
				}
			}

			setState(121);
			match(EQUALS);
			setState(123);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				setState(122);
				match(SPACE);
				}
				break;
			}
			setState(125);
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

	public static class FuncInvokeWithoutClosureContext extends ParserRuleContext {
		public FuncNameContext funcName() {
			return getRuleContext(FuncNameContext.class,0);
		}
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public List<TerminalNode> SPACE() { return getTokens(GradleParser.SPACE); }
		public TerminalNode SPACE(int i) {
			return getToken(GradleParser.SPACE, i);
		}
		public TerminalNode SMALL_BRACE_START() { return getToken(GradleParser.SMALL_BRACE_START, 0); }
		public TerminalNode SMALL_BRACE_END() { return getToken(GradleParser.SMALL_BRACE_END, 0); }
		public SequenceContext sequence() {
			return getRuleContext(SequenceContext.class,0);
		}
		public FuncInvokeWithoutClosureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcInvokeWithoutClosure; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).enterFuncInvokeWithoutClosure(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).exitFuncInvokeWithoutClosure(this);
		}
	}

	public final FuncInvokeWithoutClosureContext funcInvokeWithoutClosure() throws RecognitionException {
		FuncInvokeWithoutClosureContext _localctx = new FuncInvokeWithoutClosureContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_funcInvokeWithoutClosure);
		int _la;
		try {
			setState(160);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(128);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SPACE) {
					{
					setState(127);
					match(SPACE);
					}
				}

				setState(130);
				funcName();
				setState(132);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
				case 1:
					{
					setState(131);
					match(SPACE);
					}
					break;
				}
				setState(134);
				value();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(137);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SPACE) {
					{
					setState(136);
					match(SPACE);
					}
				}

				setState(139);
				funcName();
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
				match(SMALL_BRACE_START);
				setState(145);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
				case 1:
					{
					setState(144);
					match(SPACE);
					}
					break;
				}
				setState(149);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
				case 1:
					{
					setState(147);
					value();
					}
					break;
				case 2:
					{
					setState(148);
					sequence();
					}
					break;
				}
				setState(152);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SPACE) {
					{
					setState(151);
					match(SPACE);
					}
				}

				setState(154);
				match(SMALL_BRACE_END);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(157);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SPACE) {
					{
					setState(156);
					match(SPACE);
					}
				}

				setState(159);
				funcName();
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

	public static class GroovyStatementContext extends ParserRuleContext {
		public DefineVariableContext defineVariable() {
			return getRuleContext(DefineVariableContext.class,0);
		}
		public FuncInvokeContext funcInvoke() {
			return getRuleContext(FuncInvokeContext.class,0);
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
		enterRule(_localctx, 24, RULE_groovyStatement);
		try {
			setState(164);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(162);
				defineVariable();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(163);
				funcInvoke();
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
		enterRule(_localctx, 26, RULE_closureBody);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(169);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(166);
					groovyStatement();
					}
					} 
				}
				setState(171);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
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
		enterRule(_localctx, 28, RULE_closure);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(172);
			match(BIG_BRACE_START);
			setState(174);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				{
				setState(173);
				match(SPACE);
				}
				break;
			}
			setState(176);
			closureBody();
			setState(178);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SPACE) {
				{
				setState(177);
				match(SPACE);
				}
			}

			setState(180);
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

	public static class FuncInvokeContext extends ParserRuleContext {
		public FuncInvokeWithoutClosureContext funcInvokeWithoutClosure() {
			return getRuleContext(FuncInvokeWithoutClosureContext.class,0);
		}
		public TerminalNode SPACE() { return getToken(GradleParser.SPACE, 0); }
		public ClosureContext closure() {
			return getRuleContext(ClosureContext.class,0);
		}
		public FuncInvokeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcInvoke; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).enterFuncInvoke(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GradleListener ) ((GradleListener)listener).exitFuncInvoke(this);
		}
	}

	public final FuncInvokeContext funcInvoke() throws RecognitionException {
		FuncInvokeContext _localctx = new FuncInvokeContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_funcInvoke);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(182);
			funcInvokeWithoutClosure();
			setState(184);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
			case 1:
				{
				setState(183);
				match(SPACE);
				}
				break;
			}
			setState(187);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==BIG_BRACE_START) {
				{
				setState(186);
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
		public List<FuncInvokeContext> funcInvoke() {
			return getRuleContexts(FuncInvokeContext.class);
		}
		public FuncInvokeContext funcInvoke(int i) {
			return getRuleContext(FuncInvokeContext.class,i);
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
		enterRule(_localctx, 32, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(191); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(191);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
				case 1:
					{
					setState(189);
					defineVariable();
					}
					break;
				case 2:
					{
					setState(190);
					funcInvoke();
					}
					break;
				}
				}
				setState(193); 
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\23\u00c6\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\3\2\3\2\3\3\3\3\5\3)\n\3\3\3\3\3\5\3-\n\3\3\3\3\3\3\4\5\4\62\n\4\3\4"+
		"\3\4\5\4\66\n\4\3\4\3\4\5\4:\n\4\3\4\7\4=\n\4\f\4\16\4@\13\4\3\5\3\5\3"+
		"\5\3\5\3\6\3\6\3\7\5\7I\n\7\3\7\3\7\5\7M\n\7\3\7\5\7P\n\7\3\7\3\7\5\7"+
		"T\n\7\3\7\3\7\5\7X\n\7\7\7Z\n\7\f\7\16\7]\13\7\3\b\3\b\3\b\3\b\5\bc\n"+
		"\b\3\t\3\t\5\tg\n\t\3\t\5\tj\n\t\3\t\5\tm\n\t\3\t\3\t\3\n\3\n\3\13\3\13"+
		"\3\f\5\fv\n\f\3\f\3\f\5\fz\n\f\3\f\3\f\5\f~\n\f\3\f\3\f\3\r\5\r\u0083"+
		"\n\r\3\r\3\r\5\r\u0087\n\r\3\r\3\r\3\r\5\r\u008c\n\r\3\r\3\r\5\r\u0090"+
		"\n\r\3\r\3\r\5\r\u0094\n\r\3\r\3\r\5\r\u0098\n\r\3\r\5\r\u009b\n\r\3\r"+
		"\3\r\3\r\5\r\u00a0\n\r\3\r\5\r\u00a3\n\r\3\16\3\16\5\16\u00a7\n\16\3\17"+
		"\7\17\u00aa\n\17\f\17\16\17\u00ad\13\17\3\20\3\20\5\20\u00b1\n\20\3\20"+
		"\3\20\5\20\u00b5\n\20\3\20\3\20\3\21\3\21\5\21\u00bb\n\21\3\21\5\21\u00be"+
		"\n\21\3\22\3\22\6\22\u00c2\n\22\r\22\16\22\u00c3\3\22\3>\2\23\2\4\6\b"+
		"\n\f\16\20\22\24\26\30\32\34\36 \"\2\4\4\2\7\7\17\17\4\2\5\7\t\t\2\u00dc"+
		"\2$\3\2\2\2\4&\3\2\2\2\6\61\3\2\2\2\bA\3\2\2\2\nE\3\2\2\2\fH\3\2\2\2\16"+
		"b\3\2\2\2\20d\3\2\2\2\22p\3\2\2\2\24r\3\2\2\2\26u\3\2\2\2\30\u00a2\3\2"+
		"\2\2\32\u00a6\3\2\2\2\34\u00ab\3\2\2\2\36\u00ae\3\2\2\2 \u00b8\3\2\2\2"+
		"\"\u00c1\3\2\2\2$%\t\2\2\2%\3\3\2\2\2&(\5\2\2\2\')\7\4\2\2(\'\3\2\2\2"+
		"()\3\2\2\2)*\3\2\2\2*,\7\n\2\2+-\7\4\2\2,+\3\2\2\2,-\3\2\2\2-.\3\2\2\2"+
		"./\5\n\6\2/\5\3\2\2\2\60\62\7\4\2\2\61\60\3\2\2\2\61\62\3\2\2\2\62\63"+
		"\3\2\2\2\63>\5\4\3\2\64\66\7\4\2\2\65\64\3\2\2\2\65\66\3\2\2\2\66\67\3"+
		"\2\2\2\679\7\f\2\28:\7\4\2\298\3\2\2\29:\3\2\2\2:;\3\2\2\2;=\5\4\3\2<"+
		"\65\3\2\2\2=@\3\2\2\2>?\3\2\2\2><\3\2\2\2?\7\3\2\2\2@>\3\2\2\2AB\5\2\2"+
		"\2BC\7\4\2\2CD\5\36\20\2D\t\3\2\2\2EF\t\3\2\2F\13\3\2\2\2GI\7\4\2\2HG"+
		"\3\2\2\2HI\3\2\2\2IL\3\2\2\2JM\5\16\b\2KM\5\b\5\2LJ\3\2\2\2LK\3\2\2\2"+
		"M[\3\2\2\2NP\7\4\2\2ON\3\2\2\2OP\3\2\2\2PQ\3\2\2\2QS\7\f\2\2RT\7\4\2\2"+
		"SR\3\2\2\2ST\3\2\2\2TW\3\2\2\2UX\5\16\b\2VX\5\b\5\2WU\3\2\2\2WV\3\2\2"+
		"\2XZ\3\2\2\2YO\3\2\2\2Z]\3\2\2\2[Y\3\2\2\2[\\\3\2\2\2\\\r\3\2\2\2][\3"+
		"\2\2\2^c\5\n\6\2_c\5\20\t\2`c\5\b\5\2ac\5\6\4\2b^\3\2\2\2b_\3\2\2\2b`"+
		"\3\2\2\2ba\3\2\2\2c\17\3\2\2\2df\7\r\2\2eg\7\4\2\2fe\3\2\2\2fg\3\2\2\2"+
		"gi\3\2\2\2hj\5\f\7\2ih\3\2\2\2ij\3\2\2\2jl\3\2\2\2km\7\4\2\2lk\3\2\2\2"+
		"lm\3\2\2\2mn\3\2\2\2no\7\16\2\2o\21\3\2\2\2pq\7\17\2\2q\23\3\2\2\2rs\7"+
		"\17\2\2s\25\3\2\2\2tv\7\4\2\2ut\3\2\2\2uv\3\2\2\2vw\3\2\2\2wy\5\24\13"+
		"\2xz\7\4\2\2yx\3\2\2\2yz\3\2\2\2z{\3\2\2\2{}\7\13\2\2|~\7\4\2\2}|\3\2"+
		"\2\2}~\3\2\2\2~\177\3\2\2\2\177\u0080\5\16\b\2\u0080\27\3\2\2\2\u0081"+
		"\u0083\7\4\2\2\u0082\u0081\3\2\2\2\u0082\u0083\3\2\2\2\u0083\u0084\3\2"+
		"\2\2\u0084\u0086\5\22\n\2\u0085\u0087\7\4\2\2\u0086\u0085\3\2\2\2\u0086"+
		"\u0087\3\2\2\2\u0087\u0088\3\2\2\2\u0088\u0089\5\16\b\2\u0089\u00a3\3"+
		"\2\2\2\u008a\u008c\7\4\2\2\u008b\u008a\3\2\2\2\u008b\u008c\3\2\2\2\u008c"+
		"\u008d\3\2\2\2\u008d\u008f\5\22\n\2\u008e\u0090\7\4\2\2\u008f\u008e\3"+
		"\2\2\2\u008f\u0090\3\2\2\2\u0090\u0091\3\2\2\2\u0091\u0093\7\22\2\2\u0092"+
		"\u0094\7\4\2\2\u0093\u0092\3\2\2\2\u0093\u0094\3\2\2\2\u0094\u0097\3\2"+
		"\2\2\u0095\u0098\5\16\b\2\u0096\u0098\5\f\7\2\u0097\u0095\3\2\2\2\u0097"+
		"\u0096\3\2\2\2\u0097\u0098\3\2\2\2\u0098\u009a\3\2\2\2\u0099\u009b\7\4"+
		"\2\2\u009a\u0099\3\2\2\2\u009a\u009b\3\2\2\2\u009b\u009c\3\2\2\2\u009c"+
		"\u009d\7\23\2\2\u009d\u00a3\3\2\2\2\u009e\u00a0\7\4\2\2\u009f\u009e\3"+
		"\2\2\2\u009f\u00a0\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1\u00a3\5\22\n\2\u00a2"+
		"\u0082\3\2\2\2\u00a2\u008b\3\2\2\2\u00a2\u009f\3\2\2\2\u00a3\31\3\2\2"+
		"\2\u00a4\u00a7\5\26\f\2\u00a5\u00a7\5 \21\2\u00a6\u00a4\3\2\2\2\u00a6"+
		"\u00a5\3\2\2\2\u00a7\33\3\2\2\2\u00a8\u00aa\5\32\16\2\u00a9\u00a8\3\2"+
		"\2\2\u00aa\u00ad\3\2\2\2\u00ab\u00a9\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac"+
		"\35\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ae\u00b0\7\20\2\2\u00af\u00b1\7\4\2"+
		"\2\u00b0\u00af\3\2\2\2\u00b0\u00b1\3\2\2\2\u00b1\u00b2\3\2\2\2\u00b2\u00b4"+
		"\5\34\17\2\u00b3\u00b5\7\4\2\2\u00b4\u00b3\3\2\2\2\u00b4\u00b5\3\2\2\2"+
		"\u00b5\u00b6\3\2\2\2\u00b6\u00b7\7\21\2\2\u00b7\37\3\2\2\2\u00b8\u00ba"+
		"\5\30\r\2\u00b9\u00bb\7\4\2\2\u00ba\u00b9\3\2\2\2\u00ba\u00bb\3\2\2\2"+
		"\u00bb\u00bd\3\2\2\2\u00bc\u00be\5\36\20\2\u00bd\u00bc\3\2\2\2\u00bd\u00be"+
		"\3\2\2\2\u00be!\3\2\2\2\u00bf\u00c2\5\26\f\2\u00c0\u00c2\5 \21\2\u00c1"+
		"\u00bf\3\2\2\2\u00c1\u00c0\3\2\2\2\u00c2\u00c3\3\2\2\2\u00c3\u00c1\3\2"+
		"\2\2\u00c3\u00c4\3\2\2\2\u00c4#\3\2\2\2&(,\61\659>HLOSW[bfiluy}\u0082"+
		"\u0086\u008b\u008f\u0093\u0097\u009a\u009f\u00a2\u00a6\u00ab\u00b0\u00b4"+
		"\u00ba\u00bd\u00c1\u00c3";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}