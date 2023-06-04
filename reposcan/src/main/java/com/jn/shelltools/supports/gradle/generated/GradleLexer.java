// Generated from Gradle.g4 by ANTLR 4.7
package com.jn.shelltools.supports.gradle.generated;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GradleLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		CLRF=1, SPACE=2, NUM=3, BOOL=4, STRING=5, COMMENT=6, NULL=7, COLON=8, 
		EQUALS=9, COMMA=10, MIDDLE_BRACE_START=11, MIDDLE_BRACE_END=12, SYMBOL=13, 
		BIG_BRACE_START=14, BIG_BRACE_END=15, SMALL_BRACE_START=16, SMALL_BRACE_END=17;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"CLRF", "SPACE", "INT", "SIC", "NUM", "TRUE", "FALSE", "BOOL", "DOUBLE_QUOTE", 
		"SINGLE_QUOTE", "HEX_CHAR", "UNICODE", "ESCAPE_CHAR", "SAFE_CODE_POINT", 
		"STRING", "SINGLE_COMMENT", "MULTIPLINE_COMMENT", "COMMENT", "NULL", "COLON", 
		"EQUALS", "COMMA", "MIDDLE_BRACE_START", "MIDDLE_BRACE_END", "SYMBOL", 
		"BIG_BRACE_START", "BIG_BRACE_END", "SMALL_BRACE_START", "SMALL_BRACE_END"
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


	public GradleLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Gradle.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\23\u00d7\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\3\2\6\2?\n\2\r"+
		"\2\16\2@\3\2\3\2\3\3\6\3F\n\3\r\3\16\3G\3\4\3\4\3\4\7\4M\n\4\f\4\16\4"+
		"P\13\4\5\4R\n\4\3\5\3\5\5\5V\n\5\3\5\3\5\3\6\5\6[\n\6\3\6\3\6\3\6\6\6"+
		"`\n\6\r\6\16\6a\5\6d\n\6\3\6\5\6g\n\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\t\3\t\5\tv\n\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\20\3\20\7\20\u008d\n\20"+
		"\f\20\16\20\u0090\13\20\3\20\3\20\3\20\3\20\7\20\u0096\n\20\f\20\16\20"+
		"\u0099\13\20\3\20\3\20\5\20\u009d\n\20\3\21\3\21\3\21\3\21\7\21\u00a3"+
		"\n\21\f\21\16\21\u00a6\13\21\3\22\3\22\3\22\3\22\7\22\u00ac\n\22\f\22"+
		"\16\22\u00af\13\22\3\22\3\22\3\22\3\23\3\23\5\23\u00b6\n\23\3\23\3\23"+
		"\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31"+
		"\3\31\3\32\3\32\7\32\u00cb\n\32\f\32\16\32\u00ce\13\32\3\33\3\33\3\34"+
		"\3\34\3\35\3\35\3\36\3\36\3\u00ad\2\37\3\3\5\4\7\2\t\2\13\5\r\2\17\2\21"+
		"\6\23\2\25\2\27\2\31\2\33\2\35\2\37\7!\2#\2%\b\'\t)\n+\13-\f/\r\61\16"+
		"\63\17\65\20\67\219\22;\23\3\2\20\4\2\f\f\16\17\4\2\13\13\"\"\3\2\63;"+
		"\3\2\62;\4\2GGgg\4\2--//\5\2\62;CHch\n\2$$\61\61^^ddhhppttvv\4\2\2!^^"+
		"\3\2$$\3\2))\4\2\f\f\17\17\4\2C\\c|\6\2\62;C\\aac|\2\u00db\2\3\3\2\2\2"+
		"\2\5\3\2\2\2\2\13\3\2\2\2\2\21\3\2\2\2\2\37\3\2\2\2\2%\3\2\2\2\2\'\3\2"+
		"\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2"+
		"\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\3>\3\2\2\2\5E\3\2"+
		"\2\2\7Q\3\2\2\2\tS\3\2\2\2\13Z\3\2\2\2\rh\3\2\2\2\17m\3\2\2\2\21u\3\2"+
		"\2\2\23w\3\2\2\2\25y\3\2\2\2\27{\3\2\2\2\31}\3\2\2\2\33\u0085\3\2\2\2"+
		"\35\u0088\3\2\2\2\37\u009c\3\2\2\2!\u009e\3\2\2\2#\u00a7\3\2\2\2%\u00b5"+
		"\3\2\2\2\'\u00b9\3\2\2\2)\u00be\3\2\2\2+\u00c0\3\2\2\2-\u00c2\3\2\2\2"+
		"/\u00c4\3\2\2\2\61\u00c6\3\2\2\2\63\u00c8\3\2\2\2\65\u00cf\3\2\2\2\67"+
		"\u00d1\3\2\2\29\u00d3\3\2\2\2;\u00d5\3\2\2\2=?\t\2\2\2>=\3\2\2\2?@\3\2"+
		"\2\2@>\3\2\2\2@A\3\2\2\2AB\3\2\2\2BC\b\2\2\2C\4\3\2\2\2DF\t\3\2\2ED\3"+
		"\2\2\2FG\3\2\2\2GE\3\2\2\2GH\3\2\2\2H\6\3\2\2\2IR\7\62\2\2JN\t\4\2\2K"+
		"M\t\5\2\2LK\3\2\2\2MP\3\2\2\2NL\3\2\2\2NO\3\2\2\2OR\3\2\2\2PN\3\2\2\2"+
		"QI\3\2\2\2QJ\3\2\2\2R\b\3\2\2\2SU\t\6\2\2TV\t\7\2\2UT\3\2\2\2UV\3\2\2"+
		"\2VW\3\2\2\2WX\5\7\4\2X\n\3\2\2\2Y[\7/\2\2ZY\3\2\2\2Z[\3\2\2\2[\\\3\2"+
		"\2\2\\c\5\7\4\2]_\7\60\2\2^`\t\5\2\2_^\3\2\2\2`a\3\2\2\2a_\3\2\2\2ab\3"+
		"\2\2\2bd\3\2\2\2c]\3\2\2\2cd\3\2\2\2df\3\2\2\2eg\5\t\5\2fe\3\2\2\2fg\3"+
		"\2\2\2g\f\3\2\2\2hi\7v\2\2ij\7t\2\2jk\7w\2\2kl\7g\2\2l\16\3\2\2\2mn\7"+
		"h\2\2no\7c\2\2op\7n\2\2pq\7u\2\2qr\7g\2\2r\20\3\2\2\2sv\5\r\7\2tv\5\17"+
		"\b\2us\3\2\2\2ut\3\2\2\2v\22\3\2\2\2wx\7$\2\2x\24\3\2\2\2yz\7)\2\2z\26"+
		"\3\2\2\2{|\t\b\2\2|\30\3\2\2\2}~\7^\2\2~\177\7w\2\2\177\u0080\3\2\2\2"+
		"\u0080\u0081\5\27\f\2\u0081\u0082\5\27\f\2\u0082\u0083\5\27\f\2\u0083"+
		"\u0084\5\27\f\2\u0084\32\3\2\2\2\u0085\u0086\7^\2\2\u0086\u0087\t\t\2"+
		"\2\u0087\34\3\2\2\2\u0088\u0089\n\n\2\2\u0089\36\3\2\2\2\u008a\u008e\5"+
		"\23\n\2\u008b\u008d\n\13\2\2\u008c\u008b\3\2\2\2\u008d\u0090\3\2\2\2\u008e"+
		"\u008c\3\2\2\2\u008e\u008f\3\2\2\2\u008f\u0091\3\2\2\2\u0090\u008e\3\2"+
		"\2\2\u0091\u0092\5\23\n\2\u0092\u009d\3\2\2\2\u0093\u0097\5\25\13\2\u0094"+
		"\u0096\n\f\2\2\u0095\u0094\3\2\2\2\u0096\u0099\3\2\2\2\u0097\u0095\3\2"+
		"\2\2\u0097\u0098\3\2\2\2\u0098\u009a\3\2\2\2\u0099\u0097\3\2\2\2\u009a"+
		"\u009b\5\25\13\2\u009b\u009d\3\2\2\2\u009c\u008a\3\2\2\2\u009c\u0093\3"+
		"\2\2\2\u009d \3\2\2\2\u009e\u009f\7\61\2\2\u009f\u00a0\7\61\2\2\u00a0"+
		"\u00a4\3\2\2\2\u00a1\u00a3\n\r\2\2\u00a2\u00a1\3\2\2\2\u00a3\u00a6\3\2"+
		"\2\2\u00a4\u00a2\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5\"\3\2\2\2\u00a6\u00a4"+
		"\3\2\2\2\u00a7\u00a8\7\61\2\2\u00a8\u00a9\7,\2\2\u00a9\u00ad\3\2\2\2\u00aa"+
		"\u00ac\13\2\2\2\u00ab\u00aa\3\2\2\2\u00ac\u00af\3\2\2\2\u00ad\u00ae\3"+
		"\2\2\2\u00ad\u00ab\3\2\2\2\u00ae\u00b0\3\2\2\2\u00af\u00ad\3\2\2\2\u00b0"+
		"\u00b1\7,\2\2\u00b1\u00b2\7\61\2\2\u00b2$\3\2\2\2\u00b3\u00b6\5!\21\2"+
		"\u00b4\u00b6\5#\22\2\u00b5\u00b3\3\2\2\2\u00b5\u00b4\3\2\2\2\u00b6\u00b7"+
		"\3\2\2\2\u00b7\u00b8\b\23\2\2\u00b8&\3\2\2\2\u00b9\u00ba\7p\2\2\u00ba"+
		"\u00bb\7w\2\2\u00bb\u00bc\7n\2\2\u00bc\u00bd\7n\2\2\u00bd(\3\2\2\2\u00be"+
		"\u00bf\7<\2\2\u00bf*\3\2\2\2\u00c0\u00c1\7?\2\2\u00c1,\3\2\2\2\u00c2\u00c3"+
		"\7.\2\2\u00c3.\3\2\2\2\u00c4\u00c5\7]\2\2\u00c5\60\3\2\2\2\u00c6\u00c7"+
		"\7_\2\2\u00c7\62\3\2\2\2\u00c8\u00cc\t\16\2\2\u00c9\u00cb\t\17\2\2\u00ca"+
		"\u00c9\3\2\2\2\u00cb\u00ce\3\2\2\2\u00cc\u00ca\3\2\2\2\u00cc\u00cd\3\2"+
		"\2\2\u00cd\64\3\2\2\2\u00ce\u00cc\3\2\2\2\u00cf\u00d0\7}\2\2\u00d0\66"+
		"\3\2\2\2\u00d1\u00d2\7\177\2\2\u00d28\3\2\2\2\u00d3\u00d4\7*\2\2\u00d4"+
		":\3\2\2\2\u00d5\u00d6\7+\2\2\u00d6<\3\2\2\2\24\2@GNQUZacfu\u008e\u0097"+
		"\u009c\u00a4\u00ad\u00b5\u00cc\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}