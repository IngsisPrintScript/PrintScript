/*
 * My Project
 */

package com.ingsis.interpreter.visitor;

import com.ingsis.interpreter.visitor.expression.strategies.ExpressionSolutionStrategy;
import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.nodes.function.CallFunctionNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.nodes.keyword.LetKeywordNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.runtime.Runtime;
import com.ingsis.runtime.environment.entries.VariableEntry;
import com.ingsis.types.Types;
import com.ingsis.visitors.Interpreter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class DefaultInterpreterVisitor implements Interpreter {
  private final Runtime runtime;
  private final ExpressionSolutionStrategy expressionSolutionStrategy;

  public DefaultInterpreterVisitor(
      Runtime runtime, ExpressionSolutionStrategy expressionSolutionStrategy) {
    this.runtime = runtime;
    this.expressionSolutionStrategy = expressionSolutionStrategy;
  }

  @Override
  public Result<String> interpret(IfKeywordNode ifKeywordNode) {
    return new IncorrectResult<>("not implemented yet.");
  }

  private Result<String> interpret(TypeAssignationNode typeAssignationNode) {
    String identifier = typeAssignationNode.identifierNode().name();
    Types type = typeAssignationNode.typeNode().type();
    Result<VariableEntry> declareVarResult = runtime.getCurrentEnvironment().putVariable(identifier, type);
    if (!declareVarResult.isCorrect()) {
      return new IncorrectResult<>(declareVarResult);
    }
    return new CorrectResult<>(
        "Variable " + identifier + " has been declared with type " + type.keyword());
  }

  private Result<String> interpret(ValueAssignationNode valueAssignationNode) {
    String identifier = valueAssignationNode.identifierNode().name();
    Result<Object> valueResult = this.interpret(valueAssignationNode.expressionNode());
    if (!valueResult.isCorrect()) {
      return new IncorrectResult<>(valueResult);
    }
    Object value = valueResult.result();
    Result<VariableEntry> modifyVarResult = runtime.getCurrentEnvironment().modifyVariable(identifier, value);
    if (!modifyVarResult.isCorrect()) {
      return new IncorrectResult<>(modifyVarResult);
    }
    return new CorrectResult<>("Variable " + identifier + " value was updated to " + value);
  }

  @Override
  public Result<String> interpret(LetKeywordNode letKeywordNode) {
    Result<String> typeAssignationResult = interpret(letKeywordNode.typeAssignationNode());
    if (!typeAssignationResult.isCorrect()) {
      return new IncorrectResult<>(typeAssignationResult);
    }
    Result<String> valueAssignationResult = interpret(letKeywordNode.valueAssignationNode());
    if (!valueAssignationResult.isCorrect()) {
      return new IncorrectResult<>(valueAssignationResult);
    }
    return new CorrectResult<>("New variable declared and initialized.");
  }

  @Override
  public Result<Object> interpret(ExpressionNode expressionNode) {
    return expressionSolutionStrategy.solve(this, expressionNode);
  }

  @Override
  public Result<String> interpret(CallFunctionNode callFunctionNode) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'interpret'");
  }

}
