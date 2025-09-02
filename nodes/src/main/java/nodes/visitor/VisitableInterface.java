package nodes.visitor;

import results.Result;

public interface VisitableInterface {
  Result<String> accept(VisitorInterface visitor);
}
