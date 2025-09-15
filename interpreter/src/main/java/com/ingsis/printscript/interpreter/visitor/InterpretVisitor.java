/*
 * My Project
 */

package com.ingsis.printscript.interpreter.visitor;

import com.ingsis.printscript.astnodes.declaration.AscriptionNode;
import com.ingsis.printscript.astnodes.declaration.TypeNode;
import com.ingsis.printscript.astnodes.expression.ExpressionNode;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.statements.LetStatementNode;
import com.ingsis.printscript.astnodes.statements.PrintStatementNode;
import com.ingsis.printscript.astnodes.statements.function.DeclareFunctionNode;
import com.ingsis.printscript.astnodes.statements.function.argument.DeclarationArgumentNode;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.runtime.Runtime;
import com.ingsis.printscript.runtime.entries.FunctionEntry;
import com.ingsis.printscript.runtime.entries.VariableEntry;
import com.ingsis.printscript.runtime.environment.EnvironmentInterface;
import com.ingsis.printscript.visitor.InterpretVisitorInterface;
import com.ingsis.printscript.visitor.InterpretableNode;

import java.util.Collection;

public class InterpretVisitor implements InterpretVisitorInterface {

    @Override
    public Result<String> interpret(LetStatementNode statement) {
        Result<AscriptionNode> getAscription = statement.ascription();
        if (!getAscription.isSuccessful()) {
            return new IncorrectResult<>(getAscription.errorMessage());
        }
        AscriptionNode ascription = getAscription.result();
        Result<IdentifierNode> getIdentifier = ascription.identifier();
        Result<TypeNode> getType = ascription.type();
        if (!getIdentifier.isSuccessful()) {
            return new IncorrectResult<>(getIdentifier.errorMessage());
        }
        if (!getType.isSuccessful()) {
            return new IncorrectResult<>(getType.errorMessage());
        }
        IdentifierNode identifier = getIdentifier.result();
        TypeNode type = getType.result();
        Runtime.getInstance()
                .currentEnv()
                .putVariable(identifier.name(), new VariableEntry(type.type()));
        Result<ExpressionNode> getExpression = statement.expression();
        if (!getExpression.isSuccessful()) {
            return new CorrectResult<>(
                    "Variable "
                            + identifier.name()
                            + " was declared with type "
                            + type.type()
                            + ".");
        }
        ExpressionNode expression = getExpression.result();
        Result<Object> evaluateExpression = expression.evaluate();
        if (!evaluateExpression.isSuccessful()) {
            return new IncorrectResult<>(evaluateExpression.errorMessage());
        }
        Object result = evaluateExpression.result();
        Runtime.getInstance().currentEnv().modifyVariableValue(identifier.name(), result);
        return new CorrectResult<>(
                "Variable "
                        + identifier.name()
                        + " was declared with type "
                        + type.type()
                        + " and value "
                        + result
                        + ".");
    }

    @Override
    public Result<String> interpret(PrintStatementNode statement) {
        Result<ExpressionNode> getExpression = statement.expression();
        if (!getExpression.isSuccessful()) {
            return new IncorrectResult<>(getExpression.errorMessage());
        }
        ExpressionNode expression = getExpression.result();
        Result<Object> evaluateExpression = expression.evaluate();
        if (!evaluateExpression.isSuccessful()) {
            return new IncorrectResult<>(evaluateExpression.errorMessage());
        }
        Object result = evaluateExpression.result();
        System.out.println(result);
        return new CorrectResult<>(result + " was printed successfully.");
    }

    @Override
    public Result<String> interpret(ExpressionNode expression) {
        Result<Object> evaluateExpression = expression.evaluate();
        if (!evaluateExpression.isSuccessful()) {
            return new IncorrectResult<>(evaluateExpression.errorMessage());
        }
        return new CorrectResult<>("Expression evaluated successfully.");
    }

    @Override
    public Result<String> interpret(DeclareFunctionNode statement) {
        EnvironmentInterface env = Runtime.getInstance().currentEnv();

        Result<Collection<DeclarationArgumentNode>> getArgumentsResult = statement.arguments();
        if (!getArgumentsResult.isSuccessful()) {
            return new IncorrectResult<>(getArgumentsResult.errorMessage());
        }
        Collection<DeclarationArgumentNode> arguments = getArgumentsResult.result();

        Result<Collection<InterpretableNode>> getBodyResult = statement.body();
        if (!getBodyResult.isSuccessful()) {
            return new IncorrectResult<>(getBodyResult.errorMessage());
        }
        Collection<InterpretableNode> body = getBodyResult.result();

        Result<TypeNode> getReturnTypeResult = statement.returnType();
        if (!getReturnTypeResult.isSuccessful()) {
            return new IncorrectResult<>(getReturnTypeResult.errorMessage());
        }
        TypeNode returnType = getReturnTypeResult.result();
        FunctionEntry functionEntry = new FunctionEntry(returnType.type(), arguments, body);

        Result<IdentifierNode> getIdentifierResult = statement.identifier();
        if (!getIdentifierResult.isSuccessful()) {
            return new IncorrectResult<>(getIdentifierResult.errorMessage());
        }
        IdentifierNode identifierNode = getIdentifierResult.result();
        String identifier = identifierNode.name();
        env.putFunction(identifier, functionEntry);

        return new CorrectResult<>(identifier);
    }
}
