package nodes.visitor;

import results.Result;

public interface SemanticallyCheckable {
    Result<String> acceptCheck(RuleVisitor checker);
}
