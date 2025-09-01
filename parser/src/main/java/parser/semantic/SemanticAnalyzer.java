package parser.semantic;

import visitor.RuleVisitor;
import visitor.SemanticallyCheckable;


public record SemanticAnalyzer(RuleVisitor rulesChecker) implements SemanticInterface {
    @Override
    public Boolean isSemanticallyValid(SemanticallyCheckable tree) {
        return tree.acceptCheck(rulesChecker()).isSuccessful();
    }
}

