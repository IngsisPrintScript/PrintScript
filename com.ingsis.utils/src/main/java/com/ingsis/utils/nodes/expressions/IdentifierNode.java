/*
 * My Project
 */

package com.ingsis.utils.nodes.expressions;

import com.ingsis.utils.evalstate.env.semantic.SemanticEnvironment;
import com.ingsis.utils.nodes.visitors.CheckResult;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import java.util.List;

public record IdentifierNode(String name, Integer line, Integer column) implements ExpressionNode {

  @Override
  public CheckResult acceptChecker(Checker checker, SemanticEnvironment env) {
    return checker.check(this, env);
  }

  @Override
  public List<ExpressionNode> children() {
    return List.of();
  }

  @Override
  public String symbol() {
    return name();
  }

  @Override
  public Result<String> acceptInterpreter(Interpreter interpreter) {
    Result<Object> interpretResult = interpreter.interpret(this);
    if (!interpretResult.isCorrect()) {
      return new IncorrectResult<>(interpretResult);
    }
    return new CorrectResult<>("Interpreted successfully.");
  }

  @Override
  public Result<Object> solve() {
    Result<VariableEntry> getVariableEntry = DefaultRuntime.getInstance().getCurrentEnvironment().readVariable(name());
    if (!getVariableEntry.isCorrect()) {
      return new IncorrectResult<>(getVariableEntry);
    }
    return new CorrectResult<>(getVariableEntry.result().value());
  }
}
