package parser.semantic;

import nodes.visitor.RuleVisitor;
import nodes.visitor.SemanticallyCheckable;

public record SemanticAnalyzer(RuleVisitor rulesEnforcer) implements SemanticInterface {
  @Override
  public Boolean isSemanticallyValid(SemanticallyCheckable tree) {
    return tree.acceptCheck(rulesEnforcer()).isSuccessful();
  }
}
