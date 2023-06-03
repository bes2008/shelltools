// Generated from Gradle.g4 by ANTLR 4.5.3
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
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		CLRF=1, SPACE=2, Number=3, Bool=4, String=5, COMMENT=6, Null=7, Array=8, 
		Symbol=9, KeyValue=10, KeyValues=11, Value=12, FUNC_ARGS=13, FUNC_NAME=14, 
		Closure=15, FUNC_INVOKE_WITHOUT_CLOSURE=16, FUNC_INVOKE_WITH_CLOSURE=17, 
		FUNC_INVOKE=18, DEF_VAR=19;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"CLRF", "SPACE", "INT", "SIC", "Number", "TRUE", "FALSE", "Bool", "HEX_CHAR", 
		"UNICODE", "ESCAPE_CHAR", "SAFE_CODE_POINT", "String", "SINGLE_COMMENT", 
		"MULTIPLINE_COMMENT", "COMMENT", "Null", "Array", "Symbol", "KeyValue", 
		"KeyValues", "Value", "FUNC_ARGS", "FUNC_NAME", "Closure", "FUNC_INVOKE_WITHOUT_CLOSURE", 
		"FUNC_INVOKE_WITH_CLOSURE", "FUNC_INVOKE", "DEF_VAR"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, null, null, null, null, "'null'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "CLRF", "SPACE", "Number", "Bool", "String", "COMMENT", "Null", 
		"Array", "Symbol", "KeyValue", "KeyValues", "Value", "FUNC_ARGS", "FUNC_NAME", 
		"Closure", "FUNC_INVOKE_WITHOUT_CLOSURE", "FUNC_INVOKE_WITH_CLOSURE", 
		"FUNC_INVOKE", "DEF_VAR"
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
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\25\u00fc\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\3\2\6\2?\n\2\r"+
		"\2\16\2@\3\3\6\3D\n\3\r\3\16\3E\3\3\3\3\3\4\3\4\3\4\7\4M\n\4\f\4\16\4"+
		"P\13\4\5\4R\n\4\3\5\3\5\5\5V\n\5\3\5\3\5\3\6\5\6[\n\6\3\6\3\6\3\6\6\6"+
		"`\n\6\r\6\16\6a\5\6d\n\6\3\6\5\6g\n\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\t\3\t\5\tv\n\t\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3"+
		"\13\3\13\3\f\3\f\3\f\3\r\3\r\3\16\3\16\3\16\3\16\7\16\u008b\n\16\f\16"+
		"\16\16\u008e\13\16\3\16\3\16\3\17\3\17\3\17\3\17\7\17\u0096\n\17\f\17"+
		"\16\17\u0099\13\17\3\20\3\20\3\20\3\20\7\20\u009f\n\20\f\20\16\20\u00a2"+
		"\13\20\3\20\3\20\3\20\3\21\3\21\5\21\u00a9\n\21\3\21\3\21\3\22\3\22\3"+
		"\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\7\23\u00b8\n\23\f\23\16\23"+
		"\u00bb\13\23\3\23\3\23\5\23\u00bf\n\23\3\24\3\24\6\24\u00c3\n\24\r\24"+
		"\16\24\u00c4\3\25\3\25\3\25\3\25\3\26\3\26\3\26\7\26\u00ce\n\26\f\26\16"+
		"\26\u00d1\13\26\3\27\3\27\3\27\3\27\3\27\3\27\5\27\u00d9\n\27\3\30\3\30"+
		"\3\31\3\31\3\32\3\32\7\32\u00e1\n\32\f\32\16\32\u00e4\13\32\3\32\3\32"+
		"\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\5\33\u00f1\n\33\3\34\3\34"+
		"\3\35\3\35\5\35\u00f7\n\35\3\36\3\36\3\36\3\36\6\u00a0\u00c4\u00cf\u00e2"+
		"\2\37\3\3\5\4\7\2\t\2\13\5\r\2\17\2\21\6\23\2\25\2\27\2\31\2\33\7\35\2"+
		"\37\2!\b#\t%\n\'\13)\f+\r-\16/\17\61\20\63\21\65\22\67\239\24;\25\3\2"+
		"\16\4\2\f\f\16\17\4\2\13\13\"\"\3\2\63;\3\2\62;\4\2GGgg\4\2--//\5\2\62"+
		";CHch\n\2$$\61\61^^ddhhppttvv\5\2\2!$$^^\4\2\f\f\17\17\4\2C\\c|\6\2\62"+
		";C\\aac|\u010e\2\3\3\2\2\2\2\5\3\2\2\2\2\13\3\2\2\2\2\21\3\2\2\2\2\33"+
		"\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3"+
		"\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2"+
		"\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\3>\3\2\2\2\5C\3\2\2\2\7Q\3\2\2\2\tS"+
		"\3\2\2\2\13Z\3\2\2\2\rh\3\2\2\2\17m\3\2\2\2\21u\3\2\2\2\23w\3\2\2\2\25"+
		"y\3\2\2\2\27\u0081\3\2\2\2\31\u0084\3\2\2\2\33\u0086\3\2\2\2\35\u0091"+
		"\3\2\2\2\37\u009a\3\2\2\2!\u00a8\3\2\2\2#\u00ac\3\2\2\2%\u00be\3\2\2\2"+
		"\'\u00c0\3\2\2\2)\u00c6\3\2\2\2+\u00ca\3\2\2\2-\u00d8\3\2\2\2/\u00da\3"+
		"\2\2\2\61\u00dc\3\2\2\2\63\u00de\3\2\2\2\65\u00f0\3\2\2\2\67\u00f2\3\2"+
		"\2\29\u00f6\3\2\2\2;\u00f8\3\2\2\2=?\t\2\2\2>=\3\2\2\2?@\3\2\2\2@>\3\2"+
		"\2\2@A\3\2\2\2A\4\3\2\2\2BD\t\3\2\2CB\3\2\2\2DE\3\2\2\2EC\3\2\2\2EF\3"+
		"\2\2\2FG\3\2\2\2GH\b\3\2\2H\6\3\2\2\2IR\7\62\2\2JN\t\4\2\2KM\t\5\2\2L"+
		"K\3\2\2\2MP\3\2\2\2NL\3\2\2\2NO\3\2\2\2OR\3\2\2\2PN\3\2\2\2QI\3\2\2\2"+
		"QJ\3\2\2\2R\b\3\2\2\2SU\t\6\2\2TV\t\7\2\2UT\3\2\2\2UV\3\2\2\2VW\3\2\2"+
		"\2WX\5\7\4\2X\n\3\2\2\2Y[\7/\2\2ZY\3\2\2\2Z[\3\2\2\2[\\\3\2\2\2\\c\5\7"+
		"\4\2]_\7\60\2\2^`\t\5\2\2_^\3\2\2\2`a\3\2\2\2a_\3\2\2\2ab\3\2\2\2bd\3"+
		"\2\2\2c]\3\2\2\2cd\3\2\2\2df\3\2\2\2eg\5\t\5\2fe\3\2\2\2fg\3\2\2\2g\f"+
		"\3\2\2\2hi\7v\2\2ij\7t\2\2jk\7w\2\2kl\7g\2\2l\16\3\2\2\2mn\7h\2\2no\7"+
		"c\2\2op\7n\2\2pq\7u\2\2qr\7g\2\2r\20\3\2\2\2sv\5\r\7\2tv\5\17\b\2us\3"+
		"\2\2\2ut\3\2\2\2v\22\3\2\2\2wx\t\b\2\2x\24\3\2\2\2yz\7^\2\2z{\7w\2\2{"+
		"|\3\2\2\2|}\5\23\n\2}~\5\23\n\2~\177\5\23\n\2\177\u0080\5\23\n\2\u0080"+
		"\26\3\2\2\2\u0081\u0082\7^\2\2\u0082\u0083\t\t\2\2\u0083\30\3\2\2\2\u0084"+
		"\u0085\n\n\2\2\u0085\32\3\2\2\2\u0086\u008c\7$\2\2\u0087\u008b\5\27\f"+
		"\2\u0088\u008b\5\25\13\2\u0089\u008b\5\31\r\2\u008a\u0087\3\2\2\2\u008a"+
		"\u0088\3\2\2\2\u008a\u0089\3\2\2\2\u008b\u008e\3\2\2\2\u008c\u008a\3\2"+
		"\2\2\u008c\u008d\3\2\2\2\u008d\u008f\3\2\2\2\u008e\u008c\3\2\2\2\u008f"+
		"\u0090\7$\2\2\u0090\34\3\2\2\2\u0091\u0092\7\61\2\2\u0092\u0093\7\61\2"+
		"\2\u0093\u0097\3\2\2\2\u0094\u0096\n\13\2\2\u0095\u0094\3\2\2\2\u0096"+
		"\u0099\3\2\2\2\u0097\u0095\3\2\2\2\u0097\u0098\3\2\2\2\u0098\36\3\2\2"+
		"\2\u0099\u0097\3\2\2\2\u009a\u009b\7\61\2\2\u009b\u009c\7,\2\2\u009c\u00a0"+
		"\3\2\2\2\u009d\u009f\13\2\2\2\u009e\u009d\3\2\2\2\u009f\u00a2\3\2\2\2"+
		"\u00a0\u00a1\3\2\2\2\u00a0\u009e\3\2\2\2\u00a1\u00a3\3\2\2\2\u00a2\u00a0"+
		"\3\2\2\2\u00a3\u00a4\7,\2\2\u00a4\u00a5\7\61\2\2\u00a5 \3\2\2\2\u00a6"+
		"\u00a9\5\35\17\2\u00a7\u00a9\5\37\20\2\u00a8\u00a6\3\2\2\2\u00a8\u00a7"+
		"\3\2\2\2\u00a9\u00aa\3\2\2\2\u00aa\u00ab\b\21\2\2\u00ab\"\3\2\2\2\u00ac"+
		"\u00ad\7p\2\2\u00ad\u00ae\7w\2\2\u00ae\u00af\7n\2\2\u00af\u00b0\7n\2\2"+
		"\u00b0$\3\2\2\2\u00b1\u00b2\7]\2\2\u00b2\u00bf\7_\2\2\u00b3\u00b4\7]\2"+
		"\2\u00b4\u00b9\5-\27\2\u00b5\u00b6\7.\2\2\u00b6\u00b8\5-\27\2\u00b7\u00b5"+
		"\3\2\2\2\u00b8\u00bb\3\2\2\2\u00b9\u00b7\3\2\2\2\u00b9\u00ba\3\2\2\2\u00ba"+
		"\u00bc\3\2\2\2\u00bb\u00b9\3\2\2\2\u00bc\u00bd\7_\2\2\u00bd\u00bf\3\2"+
		"\2\2\u00be\u00b1\3\2\2\2\u00be\u00b3\3\2\2\2\u00bf&\3\2\2\2\u00c0\u00c2"+
		"\t\f\2\2\u00c1\u00c3\t\r\2\2\u00c2\u00c1\3\2\2\2\u00c3\u00c4\3\2\2\2\u00c4"+
		"\u00c5\3\2\2\2\u00c4\u00c2\3\2\2\2\u00c5(\3\2\2\2\u00c6\u00c7\5\'\24\2"+
		"\u00c7\u00c8\7<\2\2\u00c8\u00c9\5-\27\2\u00c9*\3\2\2\2\u00ca\u00cf\5)"+
		"\25\2\u00cb\u00cc\7.\2\2\u00cc\u00ce\5)\25\2\u00cd\u00cb\3\2\2\2\u00ce"+
		"\u00d1\3\2\2\2\u00cf\u00d0\3\2\2\2\u00cf\u00cd\3\2\2\2\u00d0,\3\2\2\2"+
		"\u00d1\u00cf\3\2\2\2\u00d2\u00d9\5+\26\2\u00d3\u00d9\5%\23\2\u00d4\u00d9"+
		"\5\33\16\2\u00d5\u00d9\5\13\6\2\u00d6\u00d9\5\21\t\2\u00d7\u00d9\5#\22"+
		"\2\u00d8\u00d2\3\2\2\2\u00d8\u00d3\3\2\2\2\u00d8\u00d4\3\2\2\2\u00d8\u00d5"+
		"\3\2\2\2\u00d8\u00d6\3\2\2\2\u00d8\u00d7\3\2\2\2\u00d9.\3\2\2\2\u00da"+
		"\u00db\5-\27\2\u00db\60\3\2\2\2\u00dc\u00dd\5\'\24\2\u00dd\62\3\2\2\2"+
		"\u00de\u00e2\7}\2\2\u00df\u00e1\59\35\2\u00e0\u00df\3\2\2\2\u00e1\u00e4"+
		"\3\2\2\2\u00e2\u00e3\3\2\2\2\u00e2\u00e0\3\2\2\2\u00e3\u00e5\3\2\2\2\u00e4"+
		"\u00e2\3\2\2\2\u00e5\u00e6\7\177\2\2\u00e6\64\3\2\2\2\u00e7\u00e8\5\61"+
		"\31\2\u00e8\u00e9\5/\30\2\u00e9\u00f1\3\2\2\2\u00ea\u00eb\5\61\31\2\u00eb"+
		"\u00ec\7*\2\2\u00ec\u00ed\5/\30\2\u00ed\u00ee\7+\2\2\u00ee\u00f1\3\2\2"+
		"\2\u00ef\u00f1\5\61\31\2\u00f0\u00e7\3\2\2\2\u00f0\u00ea\3\2\2\2\u00f0"+
		"\u00ef\3\2\2\2\u00f1\66\3\2\2\2\u00f2\u00f3\5\65\33\2\u00f38\3\2\2\2\u00f4"+
		"\u00f7\5\65\33\2\u00f5\u00f7\5\67\34\2\u00f6\u00f4\3\2\2\2\u00f6\u00f5"+
		"\3\2\2\2\u00f7:\3\2\2\2\u00f8\u00f9\5\'\24\2\u00f9\u00fa\7?\2\2\u00fa"+
		"\u00fb\5-\27\2\u00fb<\3\2\2\2\32\2@ENQUZacfu\u008a\u008c\u0097\u00a0\u00a8"+
		"\u00b9\u00be\u00c4\u00cf\u00d8\u00e2\u00f0\u00f6\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}