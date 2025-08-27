package visitor;

import common.responses.Result;

public interface VisitableInterface {
    Result accept(VisitorInterface visitor);
}
