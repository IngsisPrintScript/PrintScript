/*
 * My Project
 */

package com.ingsis.utils.nodes.expressions.function;

import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import java.util.List;
import java.util.function.Function;

public record GlobalFunctionBody(
    List<String> argNames, Function<Object[], Object> lambda, Integer line, Integer column)
    implements ExpressionNode {

  public GlobalFunctionBody {
    argNames = List.copyOf(argNames);
  }

  @Override
  public List<String> argNames() {
    return List.copyOf(argNames);
  }

  @Override
  public Result<String> acceptChecker(Checker checker) {
    return checker.check(this);
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
  public List<ExpressionNode> children() {
    return List.of();
  }

  @Override
  public String symbol() {
    return "";
  }

  @Override
  public Result<Object> solve() {
    return new CorrectResult<>(null);
  }
}
