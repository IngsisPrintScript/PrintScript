package visitor;

import responses.Result;

public interface SemanticallyCheckable {
    Result<String> acceptCheck(RuleVisitor checker);
}
