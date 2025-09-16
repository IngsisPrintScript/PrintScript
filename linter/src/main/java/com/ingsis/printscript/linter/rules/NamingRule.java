/*
 * My Project
 */

package com.ingsis.printscript.linter.rules;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.astnodes.NilNode;
import com.ingsis.printscript.astnodes.declaration.AscriptionNode;
import com.ingsis.printscript.astnodes.declaration.TypeNode;
import com.ingsis.printscript.astnodes.expression.binary.AdditionNode;
import com.ingsis.printscript.astnodes.expression.binary.AssignationNode;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.expression.literal.LiteralNode;
import com.ingsis.printscript.astnodes.statements.LetStatementNode;
import com.ingsis.printscript.astnodes.statements.PrintStatementNode;
import com.ingsis.printscript.astnodes.visitor.VisitorInterface;
import com.ingsis.printscript.linter.api.AnalyzerConfig;
import com.ingsis.printscript.linter.api.Rule;
import com.ingsis.printscript.linter.api.Violation;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.Result;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public final class NamingRule implements Rule, VisitorInterface {
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
        root.accept(this);
        return new ArrayList<>(VIOLATIONS);
    }

    @Override
    public Result<String> visit(LetStatementNode node) {
        var ascriptionResult = node.ascription();
        if (ascriptionResult.isSuccessful()) {
            var ascription = ascriptionResult.result();
            var identifierResult = ascription.identifier();
            if (identifierResult.isSuccessful()) {
                var identifier = identifierResult.result();
                visit(identifier);
            }
        }
        return new CorrectResult<>("");
    }

    @Override
    public Result<String> visit(IdentifierNode node) {
        System.out.println("NamingRule: Visiting identifier '" + node.name() + "'");
        if (config == null) {
            throw new IllegalStateException(
                    "NamingRule not initialized. Call check(Node, AnalyzerConfig) first.");
        }
        if (!matches(node.name(), config.naming().style())) {
            System.out.println("NamingRule: Adding violation for '" + node.name() + "'");
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
    public Result<String> visit(PrintStatementNode node) {
        return new CorrectResult<>("");
    }

    @Override
    public Result<String> visit(AscriptionNode node) {
        return new CorrectResult<>("");
    }

    @Override
    public Result<String> visit(AdditionNode node) {
        return new CorrectResult<>("");
    }

    @Override
    public Result<String> visit(AssignationNode node) {
        return new CorrectResult<>("");
    }

    @Override
    public Result<String> visit(LiteralNode node) {
        return new CorrectResult<>("");
    }

    @Override
    public Result<String> visit(TypeNode node) {
        return new CorrectResult<>("");
    }

    @Override
    public Result<String> visit(NilNode node) {
        return new CorrectResult<>("");
    }

    private boolean matches(String name, AnalyzerConfig.CaseStyle style) {
        return switch (style) {
            case CAMEL -> CAMEL.matcher(name).matches();
            case SNAKE -> SNAKE.matcher(name).matches();
        };
    }
}
