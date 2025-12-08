/*
 * My Project
 */

package com.ingsis.utils.nodes.expressions.function;

import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.expressions.atomic.identifier.IdentifierNode;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.runtime.DefaultRuntime;
import com.ingsis.utils.runtime.environment.entries.FunctionEntry;
import com.ingsis.utils.runtime.environment.entries.VariableEntry;
import com.ingsis.utils.type.types.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public record CallFunctionNode(
        IdentifierNode identifierNode,
        List<ExpressionNode> argumentNodes,
        Integer line,
        Integer column)
        implements ExpressionNode {

    public CallFunctionNode {
        argumentNodes = List.copyOf(argumentNodes);
    }

    @Override
    public List<ExpressionNode> argumentNodes() {
        return List.copyOf(argumentNodes);
    }

    @Override
    public Result<String> acceptInterpreter(Interpreter interpreter) {
        Result<Object> interpretResult = interpreter.interpret(this);
        if (!interpretResult.isCorrect()) {
            return new IncorrectResult<>(interpretResult);
        }
        return new CorrectResult<>("Interpreted successfully.");
    }

    @Override
    public Result<String> acceptChecker(Checker checker) {
        return checker.check(this);
    }

    @Override
    public List<ExpressionNode> children() {
        List<ExpressionNode> children = new ArrayList<>();
        children.add(identifierNode);
        children.addAll(argumentNodes);
        return children;
    }

    @Override
    public String symbol() {
        return identifierNode().name();
    }

    @Override
    public Result<Object> solve() {
        Result<FunctionEntry> getFunction =
                DefaultRuntime.getInstance()
                        .getCurrentEnvironment()
                        .readFunction(identifierNode.name());
        if (!getFunction.isCorrect()) {
            return new IncorrectResult<>(getFunction);
        }
        FunctionEntry functionEntry = getFunction.result();
        DefaultRuntime.getInstance().pushClosure(functionEntry);

        try {
            LinkedHashMap<String, Types> paramTypes = functionEntry.arguments();
            List<String> paramNames = new ArrayList<>(paramTypes.keySet());
            for (int i = 0; i < argumentNodes().size(); i++) {
                String paramName = paramNames.get(i);
                Types paramType = paramTypes.get(paramName);

                Result<Object> argResult = argumentNodes().get(i).solve();
                if (!argResult.isCorrect()) {
                    return new IncorrectResult<>(argResult);
                }
                Object argumentValue = argResult.result();

                Result<VariableEntry> created =
                        DefaultRuntime.getInstance()
                                .getCurrentEnvironment()
                                .createVariable(paramName, paramType, argumentValue, false);

                if (!created.isCorrect()) {
                    return new IncorrectResult<>(created);
                }
            }

            Result<Object> executeResult = new CorrectResult<>(null);
            for (ExpressionNode expr : functionEntry.body()) {
                executeResult = expr.solve();
                if (!executeResult.isCorrect()) {
                    return executeResult;
                }
            }

            return executeResult;

        } finally {
            DefaultRuntime.getInstance().pop();
        }
    }
}
