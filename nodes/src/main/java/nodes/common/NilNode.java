package nodes.common;

import java.util.List;
import nodes.expression.ExpressionNode;
import nodes.visitor.RuleVisitor;
import nodes.visitor.VisitorInterface;
import results.Result;

public class NilNode implements Node, ExpressionNode {
  @Override
  public List<Node> children() {
    return List.of();
  }

  @Override
  public Result<String> accept(VisitorInterface visitor) {
    return visitor.visit(this);
  }

  @Override
  public Boolean isNil() {
    return true;
  }

  @Override
  public Result<Object> evaluate() {
    throw new UnsupportedOperationException("Nil node can't be evaluated.");
  }

  @Override
  public Result<String> prettyPrint() {
    throw new UnsupportedOperationException("Nil node can't be printed.");
  }

  @Override
  public Result<String> acceptCheck(RuleVisitor checker) {
    throw new UnsupportedOperationException("Nil node can't be acceptCheck.");
  }
}
