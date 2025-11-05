/*
 * My Project
 */

package com.ingsis.interpreter.visitor.expression.strategies.function;

import com.ingsis.interpreter.visitor.expression.strategies.ExpressionSolutionStrategy;
import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.function.CallFunctionNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.runtime.Runtime;
import com.ingsis.runtime.environment.entries.FunctionEntry;
import com.ingsis.types.Types;
import com.ingsis.visitors.Interpreter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@SuppressFBWarnings(
        value = "EI2",
        justification = "Runtime is intentionally passed and stored; design needs mutability.")
public final class FunctionCallSolutionStrategy implements ExpressionSolutionStrategy {
    private final Runtime RUNTIME;
    private final ExpressionSolutionStrategy NEXT_STRATEGY;

    public FunctionCallSolutionStrategy(Runtime runtime, ExpressionSolutionStrategy nextStrategy) {
        this.RUNTIME = runtime;
        this.NEXT_STRATEGY = nextStrategy;
    }

    @Override
    public Result<Object> solve(Interpreter interpreter, ExpressionNode expressionNode) {
        if (!(expressionNode instanceof CallFunctionNode callFunctionNode)) {
            return NEXT_STRATEGY.solve(interpreter, expressionNode);
        }

        Result<FunctionEntry> getFunResult = readFunction(callFunctionNode);
        if (!getFunResult.isCorrect()) {
            return new IncorrectResult<>(getFunResult);
        }

        FunctionEntry functionEntry = getFunResult.result();
        return callFunction(interpreter, callFunctionNode, functionEntry);
    }

    private Result<Object> callFunction(
            Interpreter interpreter,
            CallFunctionNode callFunctionNode,
            FunctionEntry functionEntry) {

        Result<List<Object>> getArgValuesResult = evaluateArguments(interpreter, callFunctionNode);
        if (!getArgValuesResult.isCorrect()) {
            return new IncorrectResult<>(getArgValuesResult);
        }

        List<Object> argValues = getArgValuesResult.result();

        try {
            RUNTIME.pushClosure(functionEntry);
            Result<Void> bindArgsResult = bindArguments(argValues, functionEntry);
            if (!bindArgsResult.isCorrect()) {
                return new IncorrectResult<>(bindArgsResult);
            }
            return executeBody(interpreter, functionEntry);
        } finally {
            RUNTIME.pop();
        }
    }

    private Result<FunctionEntry> readFunction(CallFunctionNode node) {
        return RUNTIME.getCurrentEnvironment().readFunction(node.symbol());
    }

    private Result<List<Object>> evaluateArguments(
            Interpreter interpreter, CallFunctionNode callFunctionNode) {
        List<Object> argValues = new ArrayList<>();
        for (ExpressionNode param : callFunctionNode.argumentNodes()) {
            Result<Object> paramResult = interpreter.interpret(param);
            if (!paramResult.isCorrect()) {
                return new IncorrectResult<>(paramResult);
            }
            argValues.add(paramResult.result());
        }
        return new CorrectResult<>(argValues);
    }

    private Result<Void> bindArguments(List<Object> argValues, FunctionEntry funEntry) {
        Map<String, Types> args = funEntry.arguments();
        int index = 0;
        for (Entry<String, Types> arg : args.entrySet()) {
            String name = arg.getKey();
            Types type = arg.getValue();
            RUNTIME.getCurrentEnvironment().createVariable(name, type);
            RUNTIME.getCurrentEnvironment().updateVariable(name, argValues.get(index++));
        }
        return new CorrectResult<>(null);
    }

    private Result<Object> executeBody(Interpreter interpreter, FunctionEntry funEntry) {
        for (ExpressionNode child : funEntry.body()) {
            Result<Object> execResult = interpreter.interpret(child);
            if (!execResult.isCorrect()) {
                return new IncorrectResult<>(execResult);
            }
            if (execResult.result() != null) {
                return execResult;
            }
        }
        return new CorrectResult<>(null);
    }
}
