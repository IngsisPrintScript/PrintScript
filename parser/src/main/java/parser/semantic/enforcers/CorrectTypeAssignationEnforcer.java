package parser.semantic.enforcers;

import common.Node;
import declaration.AscriptionNode;
import declaration.TypeNode;
import expression.binary.BinaryExpression;
import parser.semantic.rules.operations.OperationFormatSemanticRule;
import parser.semantic.rules.type.ExpressionTypeGetter;
import parser.semantic.rules.type.TypeSemanticRule;
import responses.CorrectResult;
import responses.IncorrectResult;
import responses.Result;
import statements.LetStatementNode;
import statements.PrintStatementNode;
import visitor.RuleVisitor;

public class CorrectTypeAssignationEnforcer extends SemanticRulesChecker {
    private final ExpressionTypeGetter expressionTypeGetter = new ExpressionTypeGetter();
    private OperationFormatSemanticRule operationFormatRule = new OperationFormatSemanticRule();
    private TypeSemanticRule typeRule;

    @Override
    public Result check(LetStatementNode node) {
        Result getAscriptionNodeResult = node.ascription();
        if (!getAscriptionNodeResult.isSuccessful()) return getAscriptionNodeResult;
        AscriptionNode ascriptionNode = ((CorrectResult<AscriptionNode>) getAscriptionNodeResult).newObject();
        Result getTypeNodeResult = ascriptionNode.type();
        if (!getTypeNodeResult.isSuccessful()) return getTypeNodeResult;
        TypeNode typeNode = ((CorrectResult<TypeNode>) getTypeNodeResult).newObject();
        typeRule = new TypeSemanticRule(typeNode.value());

        Result getExpressionNodeResult = node.expression();
        if (!getExpressionNodeResult.isSuccessful()) return getExpressionNodeResult;
        Node expressionNode = ((CorrectResult<Node>) getExpressionNodeResult).newObject();
        if (expressionNode instanceof BinaryExpression binaryExpression) {
            return check(binaryExpression);
        }

        return typeRule.checkRules(expressionNode);
    }

    @Override
    public Result check(PrintStatementNode node) {
        Result getExpressionNodeResult = node.expression();
        if (!getExpressionNodeResult.isSuccessful()) return getExpressionNodeResult;
        Node expressionNode = ((CorrectResult<Node>) getExpressionNodeResult).newObject();

        typeRule = new TypeSemanticRule("String");

        if (expressionNode instanceof BinaryExpression binaryExpression) {
            return check(binaryExpression);
        }

        return typeRule.checkRules(expressionNode);
    }

    @Override
    public Result check(BinaryExpression node) {
        if (operationFormatRule.checkRules(node).isSuccessful()){
            return typeRule.checkRules(node);
        } else {
            return new IncorrectResult("Expression did not pass the check");
        }
    }
}
