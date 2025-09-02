package parser.semantic;

import visitor.RuleVisitor;
import visitor.SemanticallyCheckable;


public record SemanticAnalyzer(RuleVisitor rulesEnforcer) implements parser.semantic.SemanticInterface {
    @Override
    public Boolean isSemanticallyValid(SemanticallyCheckable tree) {
        return tree.acceptCheck(rulesEnforcer()).isSuccessful();
    }
}

