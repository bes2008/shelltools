// Generated from Gradle.g4 by ANTLR 4.7
package com.jn.shelltools.supports.gradle.generated;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GradleParser}.
 */
public interface GradleListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GradleParser#simpleKey}.
	 * @param ctx the parse tree
	 */
	void enterSimpleKey(GradleParser.SimpleKeyContext ctx);
	/**
	 * Exit a parse tree produced by {@link GradleParser#simpleKey}.
	 * @param ctx the parse tree
	 */
	void exitSimpleKey(GradleParser.SimpleKeyContext ctx);
	/**
	 * Enter a parse tree produced by {@link GradleParser#simpleKeyValuePair}.
	 * @param ctx the parse tree
	 */
	void enterSimpleKeyValuePair(GradleParser.SimpleKeyValuePairContext ctx);
	/**
	 * Exit a parse tree produced by {@link GradleParser#simpleKeyValuePair}.
	 * @param ctx the parse tree
	 */
	void exitSimpleKeyValuePair(GradleParser.SimpleKeyValuePairContext ctx);
	/**
	 * Enter a parse tree produced by {@link GradleParser#simpleKeyValuePairs}.
	 * @param ctx the parse tree
	 */
	void enterSimpleKeyValuePairs(GradleParser.SimpleKeyValuePairsContext ctx);
	/**
	 * Exit a parse tree produced by {@link GradleParser#simpleKeyValuePairs}.
	 * @param ctx the parse tree
	 */
	void exitSimpleKeyValuePairs(GradleParser.SimpleKeyValuePairsContext ctx);
	/**
	 * Enter a parse tree produced by {@link GradleParser#stringToClosurePair}.
	 * @param ctx the parse tree
	 */
	void enterStringToClosurePair(GradleParser.StringToClosurePairContext ctx);
	/**
	 * Exit a parse tree produced by {@link GradleParser#stringToClosurePair}.
	 * @param ctx the parse tree
	 */
	void exitStringToClosurePair(GradleParser.StringToClosurePairContext ctx);
	/**
	 * Enter a parse tree produced by {@link GradleParser#simpleValue}.
	 * @param ctx the parse tree
	 */
	void enterSimpleValue(GradleParser.SimpleValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link GradleParser#simpleValue}.
	 * @param ctx the parse tree
	 */
	void exitSimpleValue(GradleParser.SimpleValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link GradleParser#sequence}.
	 * @param ctx the parse tree
	 */
	void enterSequence(GradleParser.SequenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link GradleParser#sequence}.
	 * @param ctx the parse tree
	 */
	void exitSequence(GradleParser.SequenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link GradleParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(GradleParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link GradleParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(GradleParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link GradleParser#array}.
	 * @param ctx the parse tree
	 */
	void enterArray(GradleParser.ArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link GradleParser#array}.
	 * @param ctx the parse tree
	 */
	void exitArray(GradleParser.ArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link GradleParser#funcName}.
	 * @param ctx the parse tree
	 */
	void enterFuncName(GradleParser.FuncNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link GradleParser#funcName}.
	 * @param ctx the parse tree
	 */
	void exitFuncName(GradleParser.FuncNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link GradleParser#var}.
	 * @param ctx the parse tree
	 */
	void enterVar(GradleParser.VarContext ctx);
	/**
	 * Exit a parse tree produced by {@link GradleParser#var}.
	 * @param ctx the parse tree
	 */
	void exitVar(GradleParser.VarContext ctx);
	/**
	 * Enter a parse tree produced by {@link GradleParser#defineVariable}.
	 * @param ctx the parse tree
	 */
	void enterDefineVariable(GradleParser.DefineVariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link GradleParser#defineVariable}.
	 * @param ctx the parse tree
	 */
	void exitDefineVariable(GradleParser.DefineVariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link GradleParser#funcInvokeWithoutClosure}.
	 * @param ctx the parse tree
	 */
	void enterFuncInvokeWithoutClosure(GradleParser.FuncInvokeWithoutClosureContext ctx);
	/**
	 * Exit a parse tree produced by {@link GradleParser#funcInvokeWithoutClosure}.
	 * @param ctx the parse tree
	 */
	void exitFuncInvokeWithoutClosure(GradleParser.FuncInvokeWithoutClosureContext ctx);
	/**
	 * Enter a parse tree produced by {@link GradleParser#groovyStatement}.
	 * @param ctx the parse tree
	 */
	void enterGroovyStatement(GradleParser.GroovyStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GradleParser#groovyStatement}.
	 * @param ctx the parse tree
	 */
	void exitGroovyStatement(GradleParser.GroovyStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GradleParser#closureBody}.
	 * @param ctx the parse tree
	 */
	void enterClosureBody(GradleParser.ClosureBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link GradleParser#closureBody}.
	 * @param ctx the parse tree
	 */
	void exitClosureBody(GradleParser.ClosureBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link GradleParser#closure}.
	 * @param ctx the parse tree
	 */
	void enterClosure(GradleParser.ClosureContext ctx);
	/**
	 * Exit a parse tree produced by {@link GradleParser#closure}.
	 * @param ctx the parse tree
	 */
	void exitClosure(GradleParser.ClosureContext ctx);
	/**
	 * Enter a parse tree produced by {@link GradleParser#funcInvoke}.
	 * @param ctx the parse tree
	 */
	void enterFuncInvoke(GradleParser.FuncInvokeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GradleParser#funcInvoke}.
	 * @param ctx the parse tree
	 */
	void exitFuncInvoke(GradleParser.FuncInvokeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GradleParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(GradleParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link GradleParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(GradleParser.ProgramContext ctx);
}