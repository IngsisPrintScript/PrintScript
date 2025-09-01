package visitor;

import responses.Result;

public interface VisitableInterface {
    Result<String> accept(VisitorInterface visitor);
}
