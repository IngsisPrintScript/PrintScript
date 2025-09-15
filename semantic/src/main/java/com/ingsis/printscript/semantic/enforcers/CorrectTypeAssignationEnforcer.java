/*
 * My Project
 */

package com.ingsis.printscript.semantic.enforcers;

import com.ingsis.printscript.astnodes.declaration.AscriptionNode;
import com.ingsis.printscript.astnodes.declaration.TypeNode;
import com.ingsis.printscript.astnodes.expression.ExpressionNode;
import com.ingsis.printscript.astnodes.expression.binary.BinaryExpression;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.expression.literal.LiteralNode;
import com.ingsis.printscript.astnodes.statements.LetStatementNode;
import com.ingsis.printscript.astnodes.statements.PrintStatementNode;
import com.ingsis.printscript.astnodes.statements.function.DeclareFunctionNode;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.runtime.Runtime;
import com.ingsis.printscript.runtime.environment.Environment;
import com.ingsis.printscript.semantic.rules.operations.OperationFormatSemanticRule;
import com.ingsis.printscript.semantic.rules.type.ExpressionTypeGetter;
import com.ingsis.printscript.semantic.rules.type.TypeSemanticRule;
import com.ingsis.printscript.visitor.InterpretableNode;
import java.util.Collection;

public class CorrectTypeAssignationEnforcer extends SemanticRulesChecker {
    private final ExpressionTypeGetter expressionTypeGetter;
    private final OperationFormatSemanticRule operationFormatRule;
    private TypeSemanticRule typeRule;

    public CorrectTypeAssignationEnforcer() {
        this.expressionTypeGetter = new ExpressionTypeGetter();
        this.operationFormatRule = new OperationFormatSemanticRule();
    }

    @Override
    public Result<String> check(LetStatementNode node) {
        Result<AscriptionNode> getAscriptionNodeResult = node.ascription();
        if (!getAscriptionNodeResult.isSuccessful()) {
            return new IncorrectResult<>(getAscriptionNodeResult.errorMessage());
        }
        AscriptionNode ascriptionNode = getAscriptionNodeResult.result();
        Result<TypeNode> getTypeNodeResult = ascriptionNode.type();
        if (!getTypeNodeResult.isSuccessful()) {
            return new IncorrectResult<>(getTypeNodeResult.errorMessage());
        }
        TypeNode typeNode = getTypeNodeResult.result();
        typeRule = new TypeSemanticRule(typeNode.type());

        Result<ExpressionNode> getExpressionNodeResult = node.expression();
        if (!getExpressionNodeResult.isSuccessful()) {
            return new CorrectResult<>("Declaration does not have to check type.");
        }
        ExpressionNode expressionNode = getExpressionNodeResult.result();

        return expressionNode.acceptCheck(this);
    }

    @Override
    public Result<String> check(PrintStatementNode node) {
        Result<ExpressionNode> getExpressionNodeResult = node.expression();
        if (!getExpressionNodeResult.isSuccessful()) {
            return new IncorrectResult<>(getExpressionNodeResult.errorMessage());
        }
        ExpressionNode expressionNode = getExpressionNodeResult.result();

        return expressionNode.acceptCheck(this);
    }

    @Override
    public Result<String> check(DeclareFunctionNode node) {
        Result<Collection<InterpretableNode>> getBodyResult = node.body();
        if (!getBodyResult.isSuccessful()) {
            return new IncorrectResult<>(getBodyResult.errorMessage());
        }
        Collection<InterpretableNode> body = getBodyResult.result();
        Runtime.getInstance().pushEnv(new Environment(Runtime.getInstance().currentEnv()));
        for (InterpretableNode interpretableNode : body) {
            Result<String> subProgramCheckResult = interpretableNode.acceptCheck(this);
            if (!subProgramCheckResult.isSuccessful()) {
                return new IncorrectResult<>(subProgramCheckResult.errorMessage());
            }
        }
        Runtime.getInstance().popEnv();
        return new CorrectResult<>("Function passed type check");
    }

    @Override
    public Result<String> check(BinaryExpression node) {
        if (typeRule == null) {
            typeRule = new TypeSemanticRule(expressionTypeGetter.getType(node));
        }

        Result<String> checkResult = operationFormatRule.checkRules(node);

        if (checkResult.isSuccessful()) {
            Result<ExpressionNode> getLeftExpressionResult = node.getLeftChild();
            if (!getLeftExpressionResult.isSuccessful()) {
                return new IncorrectResult<>(getLeftExpressionResult.errorMessage());
            }
            ExpressionNode leftExpression = getLeftExpressionResult.result();
            return leftExpression.acceptCheck(this);
        } else {
            return new IncorrectResult<>(checkResult.errorMessage());
        }
    }

    @Override
    public Result<String> check(IdentifierNode node) {
        typeRule =
                new TypeSemanticRule(
                        Runtime.getInstance().currentEnv().getVariableType(node.name()).result());
        return typeRule.checkRules(node);
    }

    @Override
    public Result<String> check(LiteralNode node) {
        if (typeRule == null) {
            return new CorrectResult<>("The literal defines the type.");
        }
        return typeRule.checkRules(node);
    }
}
