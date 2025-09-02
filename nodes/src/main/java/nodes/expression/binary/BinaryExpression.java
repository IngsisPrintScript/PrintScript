package nodes.expression.binary;

import java.util.List;
import nodes.common.NilNode;
import nodes.common.Node;
import nodes.expression.ExpressionNode;
import nodes.visitor.RuleVisitor;
import nodes.visitor.SemanticallyCheckable;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;

public abstract class BinaryExpression implements Node, ExpressionNode, SemanticallyCheckable {
  private ExpressionNode leftChild;
  private ExpressionNode rightChild;

  public BinaryExpression() {
    this.leftChild = new NilNode();
    this.rightChild = new NilNode();
  }

  public Boolean hasLeftChild() {
    return !leftChild.isNil();
  }

  public Boolean hasRightChild() {
    return !rightChild.isNil();
  }

  public Result<ExpressionNode> getLeftChild() {
    if (hasLeftChild()) {
      return new CorrectResult<>(leftChild);
    } else {
      return new IncorrectResult<>("It has no left child.");
    }
  }

  public Result<ExpressionNode> getRightChild() {
    if (hasRightChild()) {
      return new CorrectResult<>(rightChild);
    } else {
      return new IncorrectResult<>("It has no right child.");
    }
  }

  public Result<ExpressionNode> setLeftChild(ExpressionNode leftChild) {
    this.leftChild = leftChild;
    return new CorrectResult<>(leftChild);
  }

  public Result<ExpressionNode> setRightChild(ExpressionNode rightChild) {
    this.rightChild = rightChild;
    return new CorrectResult<>(rightChild);
  }

  @Override
  public List<Node> children() {
    return List.of(leftChild, rightChild);
  }

  @Override
  public Boolean isNil() {
    return false;
  }

  @Override
  public Result<String> acceptCheck(RuleVisitor checker) {
    return checker.check(this);
  }
}
