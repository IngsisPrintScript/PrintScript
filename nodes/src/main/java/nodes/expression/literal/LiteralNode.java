package nodes.expression.literal;

import java.util.List;
import nodes.common.Node;
import nodes.expression.ExpressionNode;
import nodes.visitor.RuleVisitor;
import nodes.visitor.VisitorInterface;
import results.CorrectResult;
import results.Result;

public record LiteralNode(String value) implements Node, ExpressionNode {
  @Override
  public Result<String> accept(VisitorInterface visitor) {
    return visitor.visit(this);
  }

  @Override
  public List<Node> children() {
    return List.of();
  }

  @Override
  public Boolean isNil() {
    return false;
  }

  @Override
  public Result<Object> evaluate() {
    return new CorrectResult<>(this.value);
  }

  @Override
  public Result<String> prettyPrint() {
    return new CorrectResult<>(this.value());
  }

  @Override
  public Result<String> acceptCheck(RuleVisitor checker) {
    return checker.check(this);
  }
}
