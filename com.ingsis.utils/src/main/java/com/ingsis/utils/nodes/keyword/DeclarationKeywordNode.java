/*
 * My Project
 */

package com.ingsis.utils.nodes.keyword;

import com.ingsis.utils.evalstate.EvalState;
import com.ingsis.utils.evalstate.env.semantic.SemanticEnvironment;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.expressions.IdentifierNode;
import com.ingsis.utils.nodes.visitors.CheckResult;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.InterpretResult;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.type.types.Types;

public record DeclarationKeywordNode(
    IdentifierNode identifierNode,
    ExpressionNode expressionNode,
    Types declaredType,
    boolean isMutable,
    Integer line,
    Integer column)
    implements Node {

  @Override
  public CheckResult acceptChecker(Checker checker, SemanticEnvironment env) {
    return checker.check(this, env);
  }

  @Override
  public InterpretResult acceptInterpreter(Interpreter interpreter, EvalState evalState) {
    return interpreter.interpret(this, evalState);
  }
}
