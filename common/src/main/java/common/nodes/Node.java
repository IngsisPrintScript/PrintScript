package common.nodes;

import common.responses.Result;
import common.visitor.VisitorInterface;

public interface Node {
    Result children();
    Result accept(VisitorInterface visitor);
    Boolean isNil();
}
