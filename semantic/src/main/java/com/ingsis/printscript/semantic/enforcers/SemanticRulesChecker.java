/*
 * My Project
 */

package com.ingsis.printscript.semantic.enforcers;

import com.ingsis.printscript.astnodes.expression.binary.BinaryExpression;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.expression.literal.LiteralNode;
import com.ingsis.printscript.astnodes.statements.LetStatementNode;
import com.ingsis.printscript.astnodes.statements.PrintStatementNode;
import com.ingsis.printscript.astnodes.visitor.RuleVisitor;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import java.util.List;

public class SemanticRulesChecker implements RuleVisitor {
    private final List<Class<? extends SemanticRulesChecker>> rules;

    public SemanticRulesChecker() {
        rules = List.of(CorrectTypeAssignationEnforcer.class, VariablesExistenceRulesChecker.class);
    }

    @Override
    public Result<String> check(LetStatementNode node) {
        try {
            for (Class<? extends SemanticRulesChecker> rule : rules) {
                RuleVisitor checker = rule.getDeclaredConstructor().newInstance();
                Result<String> checkResult = checker.check(node);
                if (!checkResult.isSuccessful()) return checkResult;
            }
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            return new IncorrectResult<>(e.getMessage());
        }
        return new CorrectResult<String>("AST passed all semantic rules.");
    }

    @Override
    public Result<String> check(PrintStatementNode node) {
        try {
            for (Class<? extends SemanticRulesChecker> rule : rules) {
                RuleVisitor checker = rule.getDeclaredConstructor().newInstance();
                Result<String> checkResult = checker.check(node);
                if (!checkResult.isSuccessful()) return checkResult;
            }
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            return new IncorrectResult<>(e.getMessage());
        }
        return new CorrectResult<String>("AST passed all semantic rules.");
    }

    @Override
    public Result<String> check(BinaryExpression node) {
        try {
            for (Class<? extends SemanticRulesChecker> rule : rules) {
                RuleVisitor checker = rule.getDeclaredConstructor().newInstance();
                Result<String> checkResult = checker.check(node);
                if (!checkResult.isSuccessful()) return checkResult;
            }
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            return new IncorrectResult<String>(e.getMessage());
        }
        return new CorrectResult<String>("AST passed all semantic rules.");
    }

    @Override
    public Result<String> check(IdentifierNode node) {
        try {
            for (Class<? extends SemanticRulesChecker> rule : rules) {
                RuleVisitor checker = rule.getDeclaredConstructor().newInstance();
                Result<String> checkResult = checker.check(node);
                if (!checkResult.isSuccessful()) return checkResult;
            }
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            return new IncorrectResult<String>(e.getMessage());
        }
        return new CorrectResult<String>("AST passed all semantic rules.");
    }

    @Override
    public Result<String> check(LiteralNode node) {
        try {
            for (Class<? extends SemanticRulesChecker> rule : rules) {
                RuleVisitor checker = rule.getDeclaredConstructor().newInstance();
                Result<String> checkResult = checker.check(node);
                if (!checkResult.isSuccessful()) return checkResult;
            }
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            return new IncorrectResult<String>(e.getMessage());
        }
        return new CorrectResult<String>("AST passed all semantic rules.");
    }
}
