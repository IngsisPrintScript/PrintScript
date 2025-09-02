package parser.semantic;

import common.Environment;
import visitor.RuleVisitor;
import visitor.SemanticallyCheckable;


public record SemanticAnalyzer(RuleVisitor rulesEnforcer) implements parser.semantic.SemanticInterface {
    @Override
    public Boolean isSemanticallyValid(SemanticallyCheckable tree) {
        Boolean valid = tree.acceptCheck(rulesEnforcer()).isSuccessful();
        return valid;
    }
}

