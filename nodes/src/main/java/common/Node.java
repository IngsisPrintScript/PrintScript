package common;

import common.responses.Result;

public interface Node extends VisitableInterface {
    Result children();
    Boolean isNil();
}
