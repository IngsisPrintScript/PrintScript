package parser.semantic.enforcers;

import common.Environment;
import declaration.AscriptionNode;
import declaration.TypeNode;
import expression.ExpressionNode;
import expression.binary.BinaryExpression;
import expression.identifier.IdentifierNode;
import expression.literal.LiteralNode;
import parser.semantic.rules.operations.OperationFormatSemanticRule;
import parser.semantic.rules.type.ExpressionTypeGetter;
import parser.semantic.rules.type.TypeSemanticRule;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;
import statements.LetStatementNode;
import statements.PrintStatementNode;

public class CorrectTypeAssignationEnforcer extends SemanticRulesChecker {
    private final ExpressionTypeGetter expressionTypeGetter = new ExpressionTypeGetter();
    private final OperationFormatSemanticRule operationFormatRule = new OperationFormatSemanticRule();
    private TypeSemanticRule typeRule;

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
            return new IncorrectResult<>(getExpressionNodeResult.errorMessage());
        }
        ExpressionNode expressionNode = getExpressionNodeResult.result();
        if (expressionNode instanceof BinaryExpression binaryExpression) {
            return check(binaryExpression);
        }

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
    public Result<String> check(IdentifierNode node) {
        typeRule = new TypeSemanticRule(Environment.getInstance().getIdType(node.name()).result());
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
