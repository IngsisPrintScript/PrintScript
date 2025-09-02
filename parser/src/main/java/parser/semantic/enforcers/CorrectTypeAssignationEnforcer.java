package parser.semantic.enforcers;

import common.Environment;
import nodes.declaration.AscriptionNode;
import nodes.declaration.TypeNode;
import nodes.expression.ExpressionNode;
import nodes.expression.binary.BinaryExpression;
import nodes.expression.identifier.IdentifierNode;
import nodes.expression.literal.LiteralNode;
import parser.semantic.rules.operations.OperationFormatSemanticRule;
import parser.semantic.rules.type.ExpressionTypeGetter;
import parser.semantic.rules.type.TypeSemanticRule;
import results.IncorrectResult;
import results.Result;
import nodes.statements.LetStatementNode;
import nodes.statements.PrintStatementNode;

public class CorrectTypeAssignationEnforcer extends SemanticRulesChecker {
    private final ExpressionTypeGetter expressionTypeGetter = new ExpressionTypeGetter();
    private final OperationFormatSemanticRule operationFormatRule = new OperationFormatSemanticRule();
    private TypeSemanticRule typeRule;

    @Override
    public Result<String> check(LetStatementNode node) {
        Result<AscriptionNode> getAscriptionNodeResult = node.ascription();
        if (!getAscriptionNodeResult.isSuccessful()) {
            return new IncorrectResult<>("This rule does not apply to the received node.");
        }
        AscriptionNode ascriptionNode = getAscriptionNodeResult.result();
        Result<TypeNode> getTypeNodeResult = ascriptionNode.type();
        if (!getTypeNodeResult.isSuccessful()) {
            return new IncorrectResult<>("This rule does not apply to the received node.");
        }
        TypeNode typeNode = getTypeNodeResult.result();
        typeRule = new TypeSemanticRule(typeNode.type());

        Result<ExpressionNode> getExpressionNodeResult = node.expression();
        if (!getExpressionNodeResult.isSuccessful()) {
            return new IncorrectResult<>("This rule does not apply to the received node.");
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
            return new IncorrectResult<>("This rule does not apply to the received node.");
        }
        ExpressionNode expressionNode = getExpressionNodeResult.result();

        typeRule = new TypeSemanticRule("String");

        if (expressionNode instanceof BinaryExpression binaryExpression) {
            return check(binaryExpression);
        }

        return expressionNode.acceptCheck(this);
    }

    @Override
    public Result<String> check(BinaryExpression node) {
        if (typeRule == null) {
            typeRule = new TypeSemanticRule(expressionTypeGetter.getType(node));
        }

        if (operationFormatRule.checkRules(node).isSuccessful()) {
            Result<ExpressionNode> getLeftExpressionResult = node.getLeftChild();
            if (!getLeftExpressionResult.isSuccessful()) {
                return new IncorrectResult<>("This rule does not apply to the received node.");
            }
            ExpressionNode leftExpression = getLeftExpressionResult.result();
            return leftExpression.acceptCheck(this);
        } else {
            return new IncorrectResult<>("Expression did not pass the check");
        }
    }

    @Override
    public Result<String> check(IdentifierNode node) {
        typeRule = new TypeSemanticRule(Environment.getInstance().getIdType(node.name()).result());
        return typeRule.checkRules(node);
    }
    @Override
    public Result<String> check(LiteralNode node) {
        return typeRule.checkRules(node);
    }
}
