package common.nodes;

import common.responses.Result;
import common.visitor.VisitorInterface;

import java.util.List;

public interface Node {
    Result children();
    Result accept(VisitorInterface visitor);
}
