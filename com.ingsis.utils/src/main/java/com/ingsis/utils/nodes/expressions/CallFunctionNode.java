/*
 * My Project
 */

package com.ingsis.utils.nodes.expressions;

import com.ingsis.utils.evalstate.EvalState;
import com.ingsis.utils.evalstate.env.semantic.SemanticEnvironment;
import com.ingsis.utils.nodes.visitors.CheckResult;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.InterpretResult;
import com.ingsis.utils.nodes.visitors.Interpreter;

import java.util.ArrayList;
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
    // TODO
    throw new UnsupportedOperationException("Not implemented");
  }
}
