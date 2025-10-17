/*
 * My Project
 */

package com.ingsis.nodes.expression.function;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.visitors.Checker;
import com.ingsis.visitors.Interpreter;
import com.ingsis.visitors.Visitor;

import java.util.ArrayList;
import java.util.List;

public class CallFunctionNode implements ExpressionNode {
  private final IdentifierNode identifierNode;
  private final List<ExpressionNode> argumentNodes;

  public CallFunctionNode(IdentifierNode identifierNode, List<ExpressionNode> argumentNodes) {
    this.identifierNode = identifierNode;
    this.argumentNodes = List.copyOf(argumentNodes);
  }

  public IdentifierNode identifierNode() {
    return identifierNode;
  }

  public List<ExpressionNode> argumentNodes() {
    return List.copyOf(argumentNodes);
  }

  @Override
  public Result<String> acceptVisitor(Visitor visitor) {
    return visitor.visit(this);
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
  public Result<String> acceptChecker(Checker checker) {
    return checker.check(this);
  }

  @Override
  public List<ExpressionNode> children() {
    List<ExpressionNode> children = new ArrayList<>();
    children.add(identifierNode);
    children.addAll(argumentNodes);
    return children;
  }

  @Override
  public Boolean isTerminalNode() {
    return true;
  }

  @Override
  public String symbol() {
    return identifierNode().name();
  }
}
