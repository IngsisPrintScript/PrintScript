/*
 * My Project
 */

package com.ingsis.utils.nodes.expressions;

import com.ingsis.utils.evalstate.EvalState;
import com.ingsis.utils.evalstate.env.bindings.Binding;
import com.ingsis.utils.evalstate.env.semantic.SemanticEnvironment;
import com.ingsis.utils.nodes.visitors.CheckResult;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.InterpretResult;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.value.Value;
import com.ingsis.utils.value.Value.GlobalFunctionValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
  public InterpretResult acceptInterpreter(Interpreter interpreter, EvalState evalState) {
    return interpreter.interpret(this, evalState);
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
  public InterpretResult solve(EvalState evalState) {
    Optional<Binding> getFunctionBinding = evalState.env().lookup(identifierNode().name());
    if (getFunctionBinding.isEmpty()) {
      return new InterpretResult.INCORRECT(evalState, "Function not defined");
    }
    return switch (getFunctionBinding.get()) {
      case Binding.FunctionBinding GF -> {
        GlobalFunctionValue value = GF.value();
        List<Value> arguments = new ArrayList<>();
        for (ExpressionNode expressionNode : argumentNodes) {
          switch (expressionNode.solve(evalState)) {
            case InterpretResult.CORRECT C:
              arguments.add(C.value());
              evalState = C.evalState();
              break;
            case InterpretResult.INCORRECT I:
              yield I;
          }
        }
        yield new InterpretResult.CORRECT(evalState, value.impl().apply(arguments));
      }
      default -> null;
    };
  }
}
