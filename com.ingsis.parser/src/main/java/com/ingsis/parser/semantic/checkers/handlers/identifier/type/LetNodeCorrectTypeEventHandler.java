/*
 * My Project
 */

package com.ingsis.parser.semantic.checkers.handlers.identifier.type;

import com.ingsis.utils.evalstate.env.semantic.SemanticEnvironment;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.visitors.CheckResult;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import com.ingsis.utils.type.types.Types;
import com.ingsis.utils.typer.expression.DefaultExpressionTypeGetter;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class LetNodeCorrectTypeEventHandler
    implements NodeEventHandler<Node> {

  @Override
  public CheckResult handle(Node node, SemanticEnvironment env) {
    if (!(node instanceof DeclarationKeywordNode declarationKeywordNode)) {
      return new CheckResult.CORRECT(env);
    }
    Types expectedType = declarationKeywordNode.declaredType();
    Types actualType = new DefaultExpressionTypeGetter().getType(declarationKeywordNode.expressionNode(), env);
    if (actualType.equals(Types.NIL)) {
      return new CheckResult.CORRECT(env);
    }
    if (!expectedType.isCompatibleWith(actualType)) {
      return new CheckResult.INCORRECT(
          env,
          String.format(
              "Unexepected type for identifier value on line:%d and column:%d",
              node.line(), node.column()));
    }
    return new CheckResult.CORRECT(env);
  }
}
