package com.ingsis.printscript.semantic.enforcers;


import com.ingsis.printscript.astnodes.declaration.AscriptionNode;
import com.ingsis.printscript.astnodes.declaration.TypeNode;
import com.ingsis.printscript.astnodes.expression.ExpressionNode;
import com.ingsis.printscript.astnodes.expression.binary.BinaryExpression;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.expression.literal.LiteralNode;
import com.ingsis.printscript.astnodes.statements.LetStatementNode;
import com.ingsis.printscript.astnodes.statements.PrintStatementNode;
import com.ingsis.printscript.environment.Environment;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.semantic.rules.SemanticRule;
import com.ingsis.printscript.semantic.rules.variables.DeclaredVariableSemanticRule;
import com.ingsis.printscript.semantic.rules.variables.NotDeclaredVariableSemanticRule;

public class VariablesExistenceRulesChecker extends SemanticRulesChecker{
    private final SemanticRule notDeclaredVariableSemanticRuleChecker = new NotDeclaredVariableSemanticRule();
    private final SemanticRule declaredVariableSemanticRuleChecker = new DeclaredVariableSemanticRule();
    private SemanticRule variableSemanticRuleChecker;

    @Override
    public Result<String> check(LetStatementNode node) {
        Result<AscriptionNode> getAscriptionResult = node.ascription();
        if (!getAscriptionResult.isSuccessful()) {
            return new IncorrectResult<>(getAscriptionResult.errorMessage());
        }
        AscriptionNode ascription = getAscriptionResult.result();
        Result<IdentifierNode> getIdentifierNodeResult = ascription.identifier();
        if (!getIdentifierNodeResult.isSuccessful()) {
            return new IncorrectResult<>(getIdentifierNodeResult.errorMessage());
        }
        IdentifierNode identifier = getIdentifierNodeResult.result();
        variableSemanticRuleChecker = new NotDeclaredVariableSemanticRule();
        Result<String> checkIdIsNotDeclaredResult = this.check(identifier);
        if (!checkIdIsNotDeclaredResult.isSuccessful()) {
            return checkIdIsNotDeclaredResult;
        }
        ;
        Result<TypeNode> getTypeNodeResult = ascription.type();
        if (!getTypeNodeResult.isSuccessful()) {
            return new IncorrectResult<>(getTypeNodeResult.errorMessage());
        }
        TypeNode type = getTypeNodeResult.result();

        Environment.getInstance().putIdType(identifier.name(), type.type());

        Result<ExpressionNode> getExpressionResult = node.expression();
        if (!getExpressionResult.isSuccessful()) {
            return new IncorrectResult<>(getExpressionResult.errorMessage());
        }
        ExpressionNode expression = getExpressionResult.result();
        return expression.acceptCheck(this);
    }

    @Override
    public Result<String> check(PrintStatementNode node) {
        Result<ExpressionNode> getExpressionResult = node.expression();
        if (!getExpressionResult.isSuccessful()) {
            return  new IncorrectResult<>(getExpressionResult.errorMessage());
        }
        ExpressionNode expression = getExpressionResult.result();
        return expression.acceptCheck(this);
    }


    @Override
    public Result<String> check(BinaryExpression node) {
        Result<ExpressionNode> getLeftChildResult = node.getLeftChild();
        if (!getLeftChildResult.isSuccessful()) {
            return new IncorrectResult<>(getLeftChildResult.errorMessage());
        }
        ExpressionNode leftChild = getLeftChildResult.result();
        Result<String> checkRulesForLeftChild = leftChild.acceptCheck(this);
        if (!checkRulesForLeftChild.isSuccessful()) {
            return checkRulesForLeftChild;
        }

        Result<ExpressionNode> getRightChildResult = node.getRightChild();
        if (!getRightChildResult.isSuccessful()) {
            return new IncorrectResult<>(getRightChildResult.errorMessage());
        }
        ExpressionNode rightChild = getRightChildResult.result();
        Result<String> checkRulesForRightChild = rightChild.acceptCheck(this);
        if (!checkRulesForRightChild.isSuccessful()) {
            return checkRulesForRightChild;
        }

        return new CorrectResult<>("The node passes this check.");
    }

    @Override
    public Result<String> check(IdentifierNode node) {
        if (variableSemanticRuleChecker == null) {
            variableSemanticRuleChecker = new DeclaredVariableSemanticRule();
        }
        return variableSemanticRuleChecker.checkRules(node);
    }
    @Override
    public Result<String> check(LiteralNode node) {
        return new CorrectResult<>("The node passes this check.");
    }
}
