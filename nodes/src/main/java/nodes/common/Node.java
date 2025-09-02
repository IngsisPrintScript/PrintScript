package nodes.common;

import java.util.List;
import nodes.visitor.VisitableInterface;

public interface Node extends VisitableInterface {
  List<Node> children();

  Boolean isNil();
}
