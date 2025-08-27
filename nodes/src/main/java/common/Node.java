package common;

import common.responses.Result;
import visitor.VisitableInterface;

public interface Node extends VisitableInterface {
    Result children();
    Boolean isNil();
}
