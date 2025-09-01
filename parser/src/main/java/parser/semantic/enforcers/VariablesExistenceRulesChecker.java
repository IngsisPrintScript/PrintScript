package parser.semantic.enforcers;

import common.Environment;
import common.Node;
import declaration.AscriptionNode;
import declaration.TypeNode;
import expression.ExpressionNode;
import expression.identifier.IdentifierNode;
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
    public Result<String> check(LetStatementNode node) {
        Result<AscriptionNode> getAscriptionResult = node.ascription();
        if (!getAscriptionResult.isSuccessful()) {
            return new IncorrectResult<>("This tree does not pass this rule.");
        }
        AscriptionNode ascription = getAscriptionResult.result();
        Result<IdentifierNode> getIdentifierNodeResult = ascription.identifier();
        if (!getIdentifierNodeResult.isSuccessful()){
            return new IncorrectResult<>("This tree does not pass this rule.");
        }
        IdentifierNode identifier = getIdentifierNodeResult.result();
        Result<String> checkIdIsNotDeclaredResult = notDeclaredVariableSemanticRuleChecker.checkRules(identifier);
        if (!checkIdIsNotDeclaredResult.isSuccessful()){
            return checkIdIsNotDeclaredResult;
        };
        Result<TypeNode> getTypeNodeResult = ascription.type();
        if (!getTypeNodeResult.isSuccessful()){
            return new IncorrectResult<>("This tree does not pass this rule.");
        }
        TypeNode type = getTypeNodeResult.result();

        Environment.getInstance().putIdType(identifier.name(), type.type());

        Result<ExpressionNode> getExpressionResult = node.expression();
        if (!getExpressionResult.isSuccessful()) {
            return new IncorrectResult<>("This tree does not pass this rule.");
        }
        ExpressionNode expression = getExpressionResult.result();
        if (expression instanceof BinaryExpression binaryExpression) {
            return this.check(binaryExpression);
        } else if (expression instanceof LiteralNode) {
            return new CorrectResult<>("This tree does pass this rule.");
        } else if (expression instanceof IdentifierNode) {
            return new CorrectResult<>("This tree does pass this rule.");
        }

        return new IncorrectResult<>("Unmanaged expression.");    }

    @Override
    public Result<String> check(PrintStatementNode node) {
        Result<ExpressionNode> getExpressionResult = node.expression();
        if (!getExpressionResult.isSuccessful()) {
            return  new IncorrectResult<>("This tree does not pass this rule.");
        }
        ExpressionNode expression = getExpressionResult.result();

        if (expression instanceof BinaryExpression binaryExpression) {
            return this.check(binaryExpression);
        } else if (expression instanceof LiteralNode) {
            return new CorrectResult<>("This tree does pass this rule.");
        } else if (expression instanceof IdentifierNode) {
            return new CorrectResult<>("This tree does pass this rule.");
        }

        return new IncorrectResult<>("Unmanaged expression.");
    }


    @Override
    public Result<String> check(BinaryExpression node) {
        Result<ExpressionNode> getLeftChildResult = node.getLeftChild();
        if (!getLeftChildResult.isSuccessful()) {
            return new IncorrectResult<>("This tree does not pass this rule.");
        }
        ExpressionNode leftChild = getLeftChildResult.result();
        Result<String> checkRulesForLeftChild = checkRulesForChild(leftChild);
        if (!checkRulesForLeftChild.isSuccessful()) {
            return checkRulesForLeftChild;
        }

        Result<ExpressionNode> getRightChildResult = node.getRightChild();
        if (!getRightChildResult.isSuccessful()) {
            return new IncorrectResult<>("This tree does not pass this rule.");
        }
        ExpressionNode rightChild = getRightChildResult.result();
        Result<String> checkRulesForRightChild = checkRulesForChild(rightChild);
        if (!checkRulesForRightChild.isSuccessful()) {
            return checkRulesForRightChild;
        }

        return new CorrectResult<>("The node passes this check.");
    }

    private Result<String> checkRulesForChild(Node child) {
        if (child instanceof IdentifierNode identifierNode) {
            if (!declaredVariableSemanticRuleChecker.checkRules(identifierNode).isSuccessful()){
                return new IncorrectResult<>("The variable " + identifierNode.name() + " has not been declared.");
            };
        } else {
            Result<String> checkLeftChildResult = this.check((BinaryExpression) child);
            if (!checkLeftChildResult.isSuccessful()) return checkLeftChildResult;
        }
        return new CorrectResult<>("The node passes this check.");
    }
}
