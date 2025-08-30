package parser.semantic.enforcers;

import common.Node;
import declaration.AscriptionNode;
import declaration.TypeNode;
import expression.ExpressionNode;
import expression.binary.BinaryExpression;
import parser.semantic.rules.operations.OperationFormatSemanticRule;
import parser.semantic.rules.type.ExpressionTypeGetter;
import parser.semantic.rules.type.TypeSemanticRule;
import responses.CorrectResult;
import responses.IncorrectResult;
import responses.Result;
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

        return typeRule.checkRules(expressionNode);
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

        return typeRule.checkRules(expressionNode);
    }

    @Override
    public Result<String> check(BinaryExpression node) {
        if (operationFormatRule.checkRules(node).isSuccessful()){
            return typeRule.checkRules(node);
        } else {
            return new IncorrectResult<>("Expression did not pass the check");
        }
    }
}
