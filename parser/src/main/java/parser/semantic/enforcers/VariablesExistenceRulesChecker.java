package parser.semantic.enforcers;

import common.Node;
import declaration.AscriptionNode;
import declaration.IdentifierNode;
import expression.binary.BinaryExpression;
import expression.literal.LiteralNode;
import parser.semantic.rules.SemanticRule;
import parser.semantic.rules.variables.DeclaredVariableSemanticRule;
import parser.semantic.rules.variables.NotDeclaredVariableSemanticRule;
import responses.CorrectResult;
import responses.IncorrectResult;
import responses.Result;
import statements.LetStatementNode;
import statements.PrintStatementNode;

public class VariablesExistenceRulesChecker extends SemanticRulesChecker{
    private final SemanticRule notDeclaredVariableSemanticRuleChecker = new NotDeclaredVariableSemanticRule();
    private final SemanticRule declaredVariableSemanticRuleChecker = new DeclaredVariableSemanticRule();

    @Override
    public Result check(LetStatementNode node) {
        Result getAscriptionResult = node.ascription();
        if (!getAscriptionResult.isSuccessful()) return getAscriptionResult;
        AscriptionNode ascription = ((CorrectResult<AscriptionNode>) getAscriptionResult).result();
        Result getIdentifierNodeResult = ascription.identifier();
        if (!getIdentifierNodeResult.isSuccessful()) return getIdentifierNodeResult;
        LiteralNode literal = ((CorrectResult<LiteralNode>) getIdentifierNodeResult).result();
        Result checkIdIsNotDeclaredResult = notDeclaredVariableSemanticRuleChecker.checkRules(literal);
        if (!checkIdIsNotDeclaredResult.isSuccessful()) return checkIdIsNotDeclaredResult;

        Result getExpressionResult = node.expression();
        if (!getExpressionResult.isSuccessful()) return getExpressionResult;
        BinaryExpression expression = ((CorrectResult<BinaryExpression>) getExpressionResult).result();
        return this.check(expression);
    }

    @Override
    public Result check(PrintStatementNode node) {
        Result getExpressionResult = node.expression();
        if (!getExpressionResult.isSuccessful()) return getExpressionResult;
        BinaryExpression expression = ((CorrectResult<BinaryExpression>) getExpressionResult).result();
        return this.check(expression);
    }


    @Override
    public Result check(BinaryExpression node) {
        Result getLeftChildResult = node.leftChild();
        if (!getLeftChildResult.isSuccessful()) return getLeftChildResult;
        Node leftChild = ((CorrectResult<Node>) getLeftChildResult).result();
        Result checkRulesForLeftChild = checkRulesForChild(leftChild);
        if (!checkRulesForLeftChild.isSuccessful()) return checkRulesForLeftChild;

        Result getRightChildResult = node.rightChild();
        if (!getRightChildResult.isSuccessful()) return getRightChildResult;
        Node rightChild = ((CorrectResult<Node>) getRightChildResult).result();
        Result checkRulesForRightChild = checkRulesForChild(rightChild);
        if (!checkRulesForRightChild.isSuccessful()) return checkRulesForRightChild;

        return new CorrectResult<>("The node passes this check.");
    }

    private Result checkRulesForChild(Node child) {
        if (child instanceof IdentifierNode identifierNode) {
            if (!declaredVariableSemanticRuleChecker.checkRules(identifierNode).isSuccessful()){
                return new IncorrectResult("The variable " + identifierNode.value() + " has not been declared.");
            };
        } else {
            Result checkLeftChildResult = this.check((BinaryExpression) child);
            if (!checkLeftChildResult.isSuccessful()) return checkLeftChildResult;
        }
        return new CorrectResult<>("The node passes this check.");
    }
}
