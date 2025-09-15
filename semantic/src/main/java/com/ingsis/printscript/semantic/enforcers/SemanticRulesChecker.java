/*
 * My Project
 */

package com.ingsis.printscript.semantic.enforcers;

import com.ingsis.printscript.astnodes.expression.binary.BinaryExpression;
import com.ingsis.printscript.astnodes.expression.function.CallFunctionNode;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.expression.literal.LiteralNode;
import com.ingsis.printscript.astnodes.statements.LetStatementNode;
import com.ingsis.printscript.astnodes.statements.PrintStatementNode;
import com.ingsis.printscript.astnodes.statements.function.DeclareFunctionNode;
import com.ingsis.printscript.reflections.ClassGraphReflectionsUtils;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.visitor.RuleVisitor;
import java.util.Collection;
import java.util.List;

public class SemanticRulesChecker implements RuleVisitor {
    private final Collection<Class<? extends SemanticRulesChecker>> ENFORCERS;

    public SemanticRulesChecker() {
        ENFORCERS =
                List.copyOf(
                        new ClassGraphReflectionsUtils()
                                .findSubclassesOf(SemanticRulesChecker.class)
                                .find());
    }

    @Override
    public Result<String> check(LetStatementNode node) {
        return checkNode(node, RuleVisitor::check);
    }

    @Override
    public Result<String> check(PrintStatementNode node) {
        return checkNode(node, RuleVisitor::check);
    }

    @Override
    public Result<String> check(BinaryExpression node) {
        return checkNode(node, RuleVisitor::check);
    }

    @Override
    public Result<String> check(IdentifierNode node) {
        return checkNode(node, RuleVisitor::check);
    }

    @Override
    public Result<String> check(LiteralNode node) {
        return checkNode(node, RuleVisitor::check);
    }

    @Override
    public Result<String> check(CallFunctionNode node) {
        return checkNode(node, RuleVisitor::check);
    }

    @Override
    public Result<String> check(DeclareFunctionNode node) {
        return checkNode(node, RuleVisitor::check);
    }

    private <T> Result<String> checkNode(
            T node, ThrowingBiFunction<RuleVisitor, T, Result<String>> checkerFn) {
        try {
            for (Class<? extends SemanticRulesChecker> rule : ENFORCERS) {
                RuleVisitor checker = rule.getDeclaredConstructor().newInstance();
                Result<String> result = checkerFn.apply(checker, node);
                if (!result.isSuccessful()) return result;
            }
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            return new IncorrectResult<>(e.getMessage());
        }
        return new CorrectResult<>("AST passed all semantic rules.");
    }

    @FunctionalInterface
    public interface ThrowingBiFunction<T, U, R> {
        R apply(T t, U u) throws Exception;
    }
}
