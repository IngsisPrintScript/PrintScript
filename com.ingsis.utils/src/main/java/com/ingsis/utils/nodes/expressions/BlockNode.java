package com.ingsis.utils.nodes.expressions;

import java.util.List;

import com.ingsis.utils.evalstate.env.semantic.SemanticEnvironment;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.visitors.CheckResult;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.result.Result;

public record BlockNode(List<Node> statments) implements ExpressionNode {

  @Override
  public Integer line() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'line'");
  }

  @Override
  public Integer column() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'column'");
  }

  @Override
  public CheckResult acceptChecker(Checker checker, SemanticEnvironment env) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'acceptChecker'");
  }

  @Override
  public Result<String> acceptInterpreter(Interpreter interpreter) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'acceptInterpreter'");
  }

  @Override
  public List<ExpressionNode> children() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'children'");
  }

  @Override
  public Result<Object> solve() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'solve'");
  }

  @Override
  public String symbol() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'symbol'");
  }

}
