/*
 * My Project
 */

package com.ingsis.utils.nodes.expressions;

import com.ingsis.utils.evalstate.env.semantic.SemanticEnvironment;
import com.ingsis.utils.nodes.visitors.CheckResult;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
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
  public CheckResult acceptChecker(Checker checker, SemanticEnvironment env) {
    return checker.check(this, env);
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
    List<ExpressionNode> args = argumentNodes();
    DefaultRuntime runtime = DefaultRuntime.getInstance();

    Result<FunctionEntry> getFunction = runtime.getCurrentEnvironment().readFunction(identifierNode.name());
    if (!getFunction.isCorrect()) {
      return new IncorrectResult<>(getFunction);
    }
    FunctionEntry functionEntry = getFunction.result();

    try {
      LinkedHashMap<String, Types> paramTypes = functionEntry.arguments();
      List<String> paramNames = new ArrayList<>(paramTypes.keySet());
      for (int i = 0; i < args.size(); i++) {
        String paramName = paramNames.get(i);
        Types paramType = paramTypes.get(paramName);

        Result<Object> argResult = args.get(i).solve();
        if (!argResult.isCorrect()) {
          return new IncorrectResult<>(argResult);
        }
        Object argumentValue = argResult.result();

        Result<VariableEntry> created = functionEntry
            .closure()
            .createVariable(paramName, paramType, argumentValue, true);

        if (!created.isCorrect()) {
          Result<VariableEntry> updateResult = functionEntry.closure().updateVariable(paramName, argumentValue);
          if (!updateResult.isCorrect()) {
            return new IncorrectResult<>(created);
          }
        }
      }

      runtime.pushClosure(functionEntry);
      Result<Object> executeResult = new CorrectResult<>(null);
      for (ExpressionNode expr : functionEntry.body()) {
        executeResult = expr.solve();
        if (!executeResult.isCorrect()) {
          return executeResult;
        }
      }

      return executeResult;
    } finally {
      runtime.pop();
    }
  }
}
