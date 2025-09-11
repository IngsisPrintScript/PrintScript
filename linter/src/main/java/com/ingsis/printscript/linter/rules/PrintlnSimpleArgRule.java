package com.ingsis.printscript.linter.rules;

import java.util.List;
import java.util.ArrayList;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.astnodes.expression.binary.BinaryExpression;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.expression.literal.LiteralNode;
import com.ingsis.printscript.linter.api.AnalyzerConfig;
import com.ingsis.printscript.linter.api.Rule;
import com.ingsis.printscript.linter.api.Violation;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.astnodes.statements.LetStatementNode;
import com.ingsis.printscript.astnodes.statements.PrintStatementNode;
import com.ingsis.printscript.astnodes.visitor.RuleVisitor;


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
  public List<Violation> check(Node root, AnalyzerConfig cfg) {
    this.config = cfg;
    this.violations = new ArrayList<>();
    return violations;
  }

  @Override
  public Result<String> check(PrintStatementNode node) {
    var exprResult = node.expression();
    if (exprResult.isSuccessful()) {
      var expr = exprResult.result();
      if (!(expr instanceof IdentifierNode) && !(expr instanceof LiteralNode)) {
        violations.add(new Violation(
          id(),
          "println argument must be an identifier or literal (rule enabled).",
          null // Aquí podrías usar el rango si lo tienes
        ));
      }
    }
    return new CorrectResult<>("");
  }

  @Override
  public Result<String> check(LetStatementNode node) {
    return new CorrectResult<>("");
  }

  @Override
  public Result<String> check(IdentifierNode node) {
    return new CorrectResult<>("");
  }

  @Override
  public Result<String> check(BinaryExpression node) {
    return new CorrectResult<>("");
  }

  @Override
  public Result<String> check(LiteralNode node) {
    return new CorrectResult<>("");
  }
}
