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

public final class PrintlnSimpleArgRule implements Rule, VisitorInterface {
    private AnalyzerConfig config;
    private final List<Violation> VIOLATIONS;

    public PrintlnSimpleArgRule() {
        VIOLATIONS = new ArrayList<>();
    }

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
        this.VIOLATIONS.clear();
        root.accept(this);
        return new ArrayList<>(VIOLATIONS);
    }

    @Override
    public Result<String> visit(PrintStatementNode node) {
        var exprResult = node.expression();
        if (exprResult.isSuccessful()) {
            var expr = exprResult.result();
            if (!(expr instanceof IdentifierNode) && !(expr instanceof LiteralNode)) {
                VIOLATIONS.add(
                        new Violation(
                                id(),
                                "println argument must be an identifier or literal (rule enabled).",
                                null // Aquí podrías usar el rango si lo tienes
                                ));
            }
        }
        return new CorrectResult<>("");
    }

    @Override
    public Result<String> visit(LetStatementNode node) {
        return new CorrectResult<>("");
    }

    @Override
    public Result<String> visit(IdentifierNode node) {
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

    public AnalyzerConfig getConfig() {
        return config;
    }
}
