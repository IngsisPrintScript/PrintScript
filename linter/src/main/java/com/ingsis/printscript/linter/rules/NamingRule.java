/*
 * My Project
 */

package com.ingsis.printscript.linter.rules;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.astnodes.expression.binary.BinaryExpression;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.expression.literal.LiteralNode;
import com.ingsis.printscript.astnodes.statements.LetStatementNode;
import com.ingsis.printscript.astnodes.statements.PrintStatementNode;
import com.ingsis.printscript.astnodes.visitor.RuleVisitor;
import com.ingsis.printscript.linter.api.AnalyzerConfig;
import com.ingsis.printscript.linter.api.Rule;
import com.ingsis.printscript.linter.api.SourceRange;
import com.ingsis.printscript.linter.api.Violation;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.Result;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
    public List<Violation> check(Node root, AnalyzerConfig cfg) {
        this.config = cfg;
        this.violations = new ArrayList<>();
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
        return new CorrectResult<>("");
    }

    @Override
    public Result<String> check(IdentifierNode node) {
        if (!matches(node.name(), config.naming().style())) {
            violations.add(
                    new Violation(
                            id(),
                            "Identifier '"
                                    + node.name()
                                    + "' must be "
                                    + config.naming().style().name().toLowerCase(),
                            null));
        }
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

    @Override
    public Result<String> check(PrintStatementNode node) {
        return new CorrectResult<>("");
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
