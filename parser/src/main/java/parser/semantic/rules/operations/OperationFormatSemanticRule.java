package parser.semantic.rules.operations;

import java.util.List;
import nodes.common.Node;
import parser.semantic.rules.SemanticRule;
import results.IncorrectResult;
import results.Result;

public class OperationFormatSemanticRule implements SemanticRule {
  private final List<Class<? extends OperationFormatSemanticRule>> specificOperationRules =
      List.of(BinaryOperationFormatSemanticSemanticRule.class);

  @Override
  public boolean match(Node node) {
    try {
      for (Class<? extends OperationFormatSemanticRule> specificOperationRule :
          specificOperationRules) {
        SemanticRule semanticRule = specificOperationRule.getDeclaredConstructor().newInstance();
        if (semanticRule.match(node)) {
          return true;
        }
      }
      return false;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public Result<String> checkRules(Node nodeToCheck) {
    try {
      for (Class<? extends OperationFormatSemanticRule> specificOperationRule :
          specificOperationRules) {
        SemanticRule semanticRule = specificOperationRule.getDeclaredConstructor().newInstance();
        if (semanticRule.match(nodeToCheck)) {
          return semanticRule.checkRules(nodeToCheck);
        }
      }
      return new IncorrectResult<>("There was no specific rule for that operator.");
    } catch (Exception e) {
      return new IncorrectResult<>(e.getMessage());
    }
  }
}
