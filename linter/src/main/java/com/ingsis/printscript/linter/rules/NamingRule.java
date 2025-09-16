/*
 * My Project
 */

package com.ingsis.printscript.linter.rules;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.astnodes.expression.binary.BinaryExpression;
import com.ingsis.printscript.astnodes.expression.function.CallFunctionNode;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.expression.literal.LiteralNode;
import com.ingsis.printscript.astnodes.statements.IfStatementNode;
import com.ingsis.printscript.astnodes.statements.LetStatementNode;
import com.ingsis.printscript.astnodes.statements.PrintStatementNode;
import com.ingsis.printscript.linter.api.AnalyzerConfig;
import com.ingsis.printscript.linter.api.Rule;
import com.ingsis.printscript.linter.api.Violation;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.visitor.RuleVisitor;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public final class NamingRule implements Rule, RuleVisitor {
    private static final Pattern CAMEL = Pattern.compile("^[a-z]+[a-z0-9]*(?:[A-Z][a-z0-9]*)*$");
    private static final Pattern SNAKE = Pattern.compile("^[a-z]+(?:_[a-z0-9]+)*$");
    private AnalyzerConfig config;
    private final List<Violation> VIOLATIONS;

    public NamingRule() {
        this.VIOLATIONS = new ArrayList<>();
    }

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
        this.VIOLATIONS.clear();
        return new ArrayList<>(VIOLATIONS);
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
        if (config == null) {
            throw new IllegalStateException(
                    "NamingRule not initialized. Call check(Node, AnalyzerConfig) first.");
        }
        if (!matches(node.name(), config.naming().style())) {
            VIOLATIONS.add(
                    new Violation(
                            id(),
                            "Identifier '"
                                    + node.name()
                                    + "' must be "
                                    + config.naming().style().name().toLowerCase(Locale.US),
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
    public Result<String> check(CallFunctionNode node) {
        return new IncorrectResult<>("Not implemented yet.");
    }

    @Override
    public Result<String> check(PrintStatementNode node) {
        return new CorrectResult<>("");
    }

    @Override
    public Result<String> check(IfStatementNode node) {
        return null;
    }

    private boolean matches(String name, AnalyzerConfig.CaseStyle style) {
        return switch (style) {
            case CAMEL -> CAMEL.matcher(name).matches();
            case SNAKE -> SNAKE.matcher(name).matches();
        };
    }
}
