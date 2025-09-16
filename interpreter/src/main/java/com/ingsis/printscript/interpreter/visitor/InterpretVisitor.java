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
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.runtime.Runtime;
import com.ingsis.printscript.runtime.entries.VariableEntry;
import com.ingsis.printscript.visitor.InterpretVisitorInterface;
import java.util.Locale;

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
        Result<Object> convertResult = converter(result, type.type());
        if (!convertResult.isSuccessful()) {
            return new IncorrectResult<>(convertResult.errorMessage());
        }
        Object convertedResult = convertResult.result();
        Runtime.getInstance().currentEnv().modifyVariableValue(identifier.name(), convertedResult);
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

    private Result<Object> converter(Object value, String type) {
        if (value == null) {
            return new IncorrectResult<>("Could not convert value to type " + type);
        }

        try {
            switch (type.toLowerCase(Locale.US)) {
                case "string":
                    if (value.toString().startsWith("\"") && value.toString().endsWith("\"")) {
                        return new CorrectResult<>(String.valueOf(value));
                    } else if (value.toString().startsWith("'") && value.toString().endsWith("'")) {
                        return new CorrectResult<>(String.valueOf(value));
                    }
                    return new IncorrectResult<>("Could not convert value to type " + type);

                case "boolean":
                    if (value instanceof Boolean) {
                        return new CorrectResult<>((Boolean) value);
                    }
                    String strVal = value.toString().toLowerCase(Locale.US);
                    if (strVal.equals("true") || strVal.equals("1")) {
                        return new CorrectResult<>(true);
                    } else if (strVal.equals("false") || strVal.equals("0")) {
                        return new CorrectResult<>(false);
                    } else {
                        throw new IllegalArgumentException(
                                "Cannot convert " + value + " to Boolean");
                    }

                case "number":
                    if (value instanceof Number) {
                        return new CorrectResult<>((Number) value);
                    }
                    return new CorrectResult<>(Double.valueOf(value.toString()));

                default:
                    throw new IllegalArgumentException("Unsupported type: " + type);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error converting value: " + value + " to type: " + type, e);
        }
    }
}
