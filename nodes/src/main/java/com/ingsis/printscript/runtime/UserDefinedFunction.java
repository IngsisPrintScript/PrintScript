package com.ingsis.printscript.runtime;

import com.ingsis.printscript.astnodes.expression.function.CallFunctionNode;
import com.ingsis.printscript.astnodes.expression.function.argument.CallArgumentNode;
import com.ingsis.printscript.astnodes.statements.function.argument.DeclarationArgumentNode;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.runtime.entries.VariableEntry;
import com.ingsis.printscript.runtime.environment.Environment;
import com.ingsis.printscript.runtime.environment.EnvironmentInterface;
import com.ingsis.printscript.runtime.functions.PSFunction;
import com.ingsis.printscript.visitor.InterpretVisitorInterface;
import com.ingsis.printscript.visitor.InterpretableNode;

import java.util.Collection;
import java.util.List;

public final class UserDefinedFunction implements PSFunction {
    private final CallFunctionNode node;

    public UserDefinedFunction(CallFunctionNode node) {
        this.node = node;
    }

    @Override
    public Object call(List<Object> args, InterpretVisitorInterface interpreter) {
        String functionIdentifier = node.identifier().name();
        Result<Collection<DeclarationArgumentNode>> getDeclaredArgumentsResults =
                Runtime.getInstance().currentEnv().getFunctionArguments(functionIdentifier);
        Result<Collection<InterpretableNode>> getBodyResult =
                Runtime.getInstance().currentEnv().getFunctionBody(functionIdentifier);
        if (!getBodyResult.isSuccessful()){
            return new IncorrectResult<>(getBodyResult.errorMessage());
        }
        Collection<InterpretableNode> body = getBodyResult.result();

        if (!getDeclaredArgumentsResults.isSuccessful()){
            return new IncorrectResult<>(getDeclaredArgumentsResults.errorMessage());
        }
        Collection<DeclarationArgumentNode> declaredArguments = getDeclaredArgumentsResults.result();

        Runtime.getInstance().pushEnv(new Environment(Runtime.getInstance().currentEnv()));

        Result<String> loadArgumentsTypeResult = loadArgumentsType(declaredArguments);
        if (!loadArgumentsTypeResult.isSuccessful()){
            return new IncorrectResult<>(loadArgumentsTypeResult.errorMessage());
        }

        Result<String> loadArgumentsValuesResult = loadArgumentsPassedValues();
        if (!loadArgumentsValuesResult.isSuccessful()){
            return new IncorrectResult<>(loadArgumentsValuesResult.errorMessage());
        }

        for (InterpretableNode interpretableNode : body) {
            Result<String> interpretResult = interpretableNode.acceptInterpreter(interpreter);
            if (!interpretResult.isSuccessful()){
                return new IncorrectResult<>(interpretResult.errorMessage());
            }
        }

        Runtime.getInstance().popEnv();

        return new CorrectResult<>("Function " + functionIdentifier + " executed successfully.");
    }

    private static Result<String> loadArgumentsType(Collection<DeclarationArgumentNode> declaredArguments) {
        EnvironmentInterface localEnv = Runtime.getInstance().currentEnv();
        for (DeclarationArgumentNode declaredArgument : declaredArguments) {
            VariableEntry newEntry = new VariableEntry(declaredArgument.type().type());
            Result<VariableEntry> result = localEnv.putVariable(declaredArgument.identifier().name(), newEntry);
            if (!result.isSuccessful()){
                return new IncorrectResult<>(result.errorMessage());
            }
        }
        return new CorrectResult<>("Arguments loaded successfully.");
    }

    private Result<String> loadArgumentsPassedValues() {
        EnvironmentInterface localEnv = Runtime.getInstance().currentEnv();
        Result<Collection<CallArgumentNode>> getPassedArgumentsResult = node.arguments();
        if (!getPassedArgumentsResult.isSuccessful()) {
            return new IncorrectResult<>(getPassedArgumentsResult.errorMessage());
        }
        Collection<CallArgumentNode> passedArguments = getPassedArgumentsResult.result();
        for (CallArgumentNode passedArgument : passedArguments) {
            String identifier = passedArgument.identifier().name();
            Object value = passedArgument.value().value();
            localEnv.modifyVariableValue(identifier, value);
        }
        return new CorrectResult<>("All arguments loaded successfully.");
    }

    @Override
    public Class<?> returnType() {
        return null;
    }

    @Override
    public List<Class<?>> parameterTypes() {
        return null;
    }
}
