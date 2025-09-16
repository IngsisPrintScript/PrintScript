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
import com.ingsis.printscript.astnodes.statements.IfStatementNode;
import com.ingsis.printscript.astnodes.statements.LetStatementNode;
import com.ingsis.printscript.astnodes.statements.PrintStatementNode;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.runtime.Runtime;
import com.ingsis.printscript.runtime.entries.VariableEntry;
import com.ingsis.printscript.semantic.rules.SemanticRule;
import com.ingsis.printscript.semantic.rules.variables.DeclaredVariableSemanticRule;
import com.ingsis.printscript.semantic.rules.variables.NotDeclaredVariableSemanticRule;
import com.ingsis.printscript.visitor.InterpretableNode;

import java.util.Collection;

public class VariablesExistenceRulesChecker extends SemanticRulesChecker {
    private SemanticRule variableSemanticRuleChecker;

    public VariablesExistenceRulesChecker() {}


    @Override
    public Result<String> check(IfStatementNode node) {
        Collection<InterpretableNode> thenBode = node.thenBody();
        for (InterpretableNode bodyNode : thenBode) {
            Result<String> checkResult = bodyNode.acceptCheck(this);
            if (!checkResult.isSuccessful()){
                return new IncorrectResult<>(checkResult.errorMessage());
            }
        }
        Collection<InterpretableNode> elseBody = node.thenBody();
        for (InterpretableNode bodyNode : elseBody) {
            Result<String> checkResult = bodyNode.acceptCheck(this);
            if (!checkResult.isSuccessful()){
                return new IncorrectResult<>(checkResult.errorMessage());
            }
        }
        return new CorrectResult<>("If passes this check.");
    }

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

        Runtime.getInstance()
                .currentEnv()
                .putVariable(identifier.name(), new VariableEntry(type.type()));

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
            return new IncorrectResult<>(getExpressionResult.errorMessage());
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
    public Result<String> check(CallFunctionNode node) {
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
