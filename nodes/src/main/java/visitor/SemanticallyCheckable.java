package visitor;

import responses.Result;

public interface SemanticallyCheckable {
    Result acceptCheck(RuleVisitor checker);
}
