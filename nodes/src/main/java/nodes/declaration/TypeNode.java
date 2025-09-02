package nodes.declaration;

import java.util.List;
import nodes.common.Node;
import nodes.visitor.VisitorInterface;
import results.Result;

public class TypeNode implements Node {
  private final String type;

  public TypeNode(String type) {
    this.type = type;
  }

  public String type() {
    return this.type;
  }

  @Override
  public Result accept(VisitorInterface visitor) {
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
}
