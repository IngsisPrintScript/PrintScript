package com.ingsis.nodes.expression.function;

import java.util.List;
import java.util.function.Function;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.visitors.Checker;
import com.ingsis.visitors.Interpreter;
import com.ingsis.visitors.Visitor;

public record GlobalFunctionBody(Function<Object[], Object> lambda) implements ExpressionNode {

  @Override
  public Result<String> acceptVisitor(Visitor visitor) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'acceptVisitor'");
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
  public Boolean isTerminalNode() {
    return true;
  }

  @Override
  public String symbol() {
    return "";
  }

}
