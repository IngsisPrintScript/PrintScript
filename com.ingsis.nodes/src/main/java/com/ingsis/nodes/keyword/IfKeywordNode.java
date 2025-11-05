/*
 * My Project
 */

package com.ingsis.nodes.keyword;

import com.ingsis.nodes.Node;
import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.result.Result;
import com.ingsis.visitors.Checkable;
import com.ingsis.visitors.Checker;
import com.ingsis.visitors.Interpretable;
import com.ingsis.visitors.Interpreter;
import com.ingsis.visitors.Visitor;
import java.util.List;

public record IfKeywordNode(
    ExpressionNode condition,
    List<Node> thenBody,
    List<Node> elseBody)
    implements Node, Checkable, Interpretable {

  public IfKeywordNode {
    thenBody = List.copyOf(thenBody);
    elseBody = List.copyOf(elseBody);
  }

  public IfKeywordNode(ExpressionNode condition, List<Node> thenBody) {
    this(condition, thenBody, List.of());
  }

  @Override
  public List<Node> thenBody() {
    return List.copyOf(thenBody);
  }

  @Override
  public List<Node> elseBody() {
    return List.copyOf(elseBody);
  }

  @Override
  public Result<String> acceptChecker(Checker checker) {
    return checker.check(this);
  }

  @Override
  public Result<String> acceptInterpreter(Interpreter interpreter) {
    return interpreter.interpret(this);
  }

  @Override
  public Result<String> acceptVisitor(Visitor visitor) {
    return visitor.visit(this);
  }
}
