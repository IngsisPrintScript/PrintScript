package nodes.common;

import nodes.visitor.VisitableInterface;

import java.util.List;

public interface Node extends VisitableInterface {
    List<Node> children();
    Boolean isNil();
}
