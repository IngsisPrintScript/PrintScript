package visitor;

import responses.Result;

public interface VisitableInterface {
    Result accept(VisitorInterface visitor);
}
