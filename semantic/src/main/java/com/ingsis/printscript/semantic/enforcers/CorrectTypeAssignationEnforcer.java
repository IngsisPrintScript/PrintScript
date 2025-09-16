/*
 * My Project
 */

package com.ingsis.printscript.semantic.enforcers;

import com.ingsis.printscript.astnodes.declaration.AscriptionNode;
import com.ingsis.printscript.astnodes.declaration.TypeNode;
import com.ingsis.printscript.astnodes.expression.ExpressionNode;
import com.ingsis.printscript.astnodes.expression.binary.BinaryExpression;
import com.ingsis.printscript.astnodes.expression.function.CallFunctionNode;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.expression.literal.LiteralNode;
import com.ingsis.printscript.astnodes.statements.LetStatementNode;
import com.ingsis.printscript.astnodes.statements.PrintStatementNode;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.runtime.Runtime;
import com.ingsis.printscript.runtime.functions.PSFunction;
import com.ingsis.printscript.semantic.rules.operations.OperationFormatSemanticRule;
import com.ingsis.printscript.semantic.rules.type.ExpressionTypeGetter;
import com.ingsis.printscript.semantic.rules.type.TypeSemanticRule;

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
    public Result<String> check(CallFunctionNode node) {
        IdentifierNode identifierNode = node.identifier();
        String identifier = identifierNode.name();
        Result<PSFunction> getFunctionResult = Runtime.getInstance().currentEnv().getFunction(identifier);
        if (!getFunctionResult.isSuccessful()) {
            return new IncorrectResult<>(getFunctionResult.errorMessage());
        }
        if (typeRule == null) {
            return new CorrectResult<>("Declaration does not have to check type if typeRule is null.");
        }
        return typeRule.checkRules(node);
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
