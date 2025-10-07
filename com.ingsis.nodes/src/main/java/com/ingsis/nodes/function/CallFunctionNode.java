package com.ingsis.nodes.function;

import java.util.List;

import com.ingsis.nodes.Node;
import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.result.Result;
import com.ingsis.visitors.Checkable;
import com.ingsis.visitors.Checker;
import com.ingsis.visitors.Interpretable;
import com.ingsis.visitors.Interpreter;
import com.ingsis.visitors.Visitor;

public class CallFunctionNode implements Node, Checkable, Interpretable {
  private final IdentifierNode identifierNode;
  private final List<ExpressionNode> argumentNodes;

  public CallFunctionNode(IdentifierNode identifierNode, List<ExpressionNode> argumentNodes) {
    this.identifierNode = identifierNode;
    this.argumentNodes = argumentNodes;
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
    return interpreter.interpret(this);
  }

  @Override
  public Result<String> acceptChecker(Checker checker) {
    return checker.check(this);
  }

}
