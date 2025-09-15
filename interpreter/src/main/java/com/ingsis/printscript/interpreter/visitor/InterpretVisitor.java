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
import com.ingsis.printscript.astnodes.visitor.InterpretVisitorInterface;
import com.ingsis.printscript.runtime.Runtime;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;

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
        Runtime.getInstance().currentEnv().putIdType(identifier.name(), type.type());
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
        Runtime.getInstance().currentEnv().putIdValue(identifier.name(), result);
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
        return new IncorrectResult<>("Not implemented yet.");
    }
}
