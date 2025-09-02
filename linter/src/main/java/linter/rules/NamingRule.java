package linter.rules;

import java.util.regex.Pattern;
import linter.api.AnalyzerConfig;
import linter.api.Rule;
import linter.api.SourceRange;
import linter.api.Violation;
import nodes.statements.LetStatementNode;
import nodes.expression.identifier.IdentifierNode;
import nodes.visitor.RuleVisitor;
import results.Result;
import java.util.List;
import java.util.ArrayList;

public final class NamingRule implements Rule, RuleVisitor {
  private static final Pattern CAMEL = Pattern.compile("^[a-z]+[a-z0-9]*(?:[A-Z][a-z0-9]*)*$");
  private static final Pattern SNAKE = Pattern.compile("^[a-z]+(?:_[a-z0-9]+)*$");
  private AnalyzerConfig config;
  private List<Violation> violations;

  @Override
  public String id() {
    return "PS-NAMING";
  }

  @Override
  public String description() {
    return "Identifiers must match selected case style.";
  }

  @Override
  public boolean enabled(AnalyzerConfig cfg) {
    return cfg.naming().enabled();
  }

  @Override
  public List<Violation> check(nodes.common.Node root, AnalyzerConfig cfg) {
    this.config = cfg;
    this.violations = new ArrayList<>();
    root.acceptCheck(this);
    return violations;
  }

  @Override
  public Result<String> check(LetStatementNode node) {
    var ascriptionResult = node.ascription();
    if (ascriptionResult.isSuccessful()) {
      var ascription = ascriptionResult.result();
      var identifierResult = ascription.identifier();
      if (identifierResult.isSuccessful()) {
        var identifier = identifierResult.result();
        check(identifier);
      }
    }
    return new results.CorrectResult<>("");
  }

  @Override
  public Result<String> check(IdentifierNode node) {
    if (!matches(node.name(), config.naming().style())) {
      violations.add(new Violation(
        id(),
        "Identifier '" + node.name() + "' must be " + config.naming().style().name().toLowerCase(),
        null // Aquí podrías usar el rango si lo tienes
      ));
    }
    return new results.CorrectResult<>("");
  }

  @Override
  public Result<String> check(nodes.expression.binary.BinaryExpression node) {
    return new results.CorrectResult<>("");
  }

  @Override
  public Result<String> check(nodes.expression.literal.LiteralNode node) {
    return new results.CorrectResult<>("");
  }

  @Override
  public Result<String> check(nodes.statements.PrintStatementNode node) {
    return new results.CorrectResult<>("");
  }

  private boolean matches(String name, AnalyzerConfig.CaseStyle style) {
    return switch (style) {
      case CAMEL -> CAMEL.matcher(name).matches();
      case SNAKE -> SNAKE.matcher(name).matches();
    };
  }

  private static SourceRange toRange(Object r) {
    return null;
  }
}
