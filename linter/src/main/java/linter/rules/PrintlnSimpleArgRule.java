package linter.rules;

import java.util.ArrayList;
import java.util.List;
import linter.api.AnalyzerConfig;
import linter.api.Rule;
import linter.api.Violation;
import nodes.expression.identifier.IdentifierNode;
import nodes.expression.literal.LiteralNode;
import nodes.statements.PrintStatementNode;
import nodes.visitor.RuleVisitor;
import results.Result;

public final class PrintlnSimpleArgRule implements Rule, RuleVisitor {
  private AnalyzerConfig config;
  private List<Violation> violations;

  @Override
  public String id() {
    return "PS-PRINTLN-ARG";
  }

  @Override
  public String description() {
    return "println must receive only an identifier or a literal when enabled.";
  }

  @Override
  public boolean enabled(AnalyzerConfig cfg) {
    return cfg.println().enabled() && cfg.println().onlyIdentifierOrLiteral();
  }

  @Override
  public List<Violation> check(nodes.common.Node root, AnalyzerConfig cfg) {
    this.config = cfg;
    this.violations = new ArrayList<>();
    root.acceptCheck(this);
    return violations;
  }

  @Override
  public Result<String> check(PrintStatementNode node) {
    var exprResult = node.expression();
    if (exprResult.isSuccessful()) {
      var expr = exprResult.result();
      if (!(expr instanceof IdentifierNode) && !(expr instanceof LiteralNode)) {
        violations.add(
            new Violation(
                id(),
                "println argument must be an identifier or literal (rule enabled).",
                null // Aquí podrías usar el rango si lo tienes
                ));
      }
    }
    return new results.CorrectResult<>("");
  }

  @Override
  public Result<String> check(nodes.statements.LetStatementNode node) {
    return new results.CorrectResult<>("");
  }

  @Override
  public Result<String> check(IdentifierNode node) {
    return new results.CorrectResult<>("");
  }

  @Override
  public Result<String> check(nodes.expression.binary.BinaryExpression node) {
    return new results.CorrectResult<>("");
  }

  @Override
  public Result<String> check(LiteralNode node) {
    return new results.CorrectResult<>("");
  }
}
