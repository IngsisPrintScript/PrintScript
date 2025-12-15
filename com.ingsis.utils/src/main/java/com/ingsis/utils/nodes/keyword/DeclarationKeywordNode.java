/*
 * My Project
 */

package com.ingsis.utils.nodes.keyword;

import com.ingsis.utils.evalstate.env.semantic.SemanticEnvironment;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.expressions.IdentifierNode;
import com.ingsis.utils.nodes.visitors.CheckResult;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.type.types.Types;

public record DeclarationKeywordNode(
    IdentifierNode identifierNode,
    ExpressionNode expressionNode,
    Types declaredType,
    Boolean isMutable,
    Integer line,
    Integer column)
    implements Node {

  @Override
  public CheckResult acceptChecker(Checker checker, SemanticEnvironment env) {
    return checker.check(this, env);
  }

  @Override
  public Result<String> acceptInterpreter(Interpreter interpreter) {
    return interpreter.interpret(this);
  }
}
