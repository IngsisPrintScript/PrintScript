/*
 * My Project
 */

package com.ingsis.parser.semantic.checkers.handlers.identifier.existance;

import com.ingsis.utils.evalstate.env.semantic.SemanticEnvironment;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.expressions.IdentifierNode;
import com.ingsis.utils.nodes.visitors.CheckResult;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class ExpressionNodeEventVariableExistenceHandler
    implements NodeEventHandler<ExpressionNode> {

  @Override
  public CheckResult handle(ExpressionNode node, SemanticEnvironment env) {
    List<ExpressionNode> children = node.children();

    if (node.symbol().equals("=")) {
      ExpressionNode identifierNode = children.get(0);
      String identifier = identifierNode.symbol();
      if (env.lookup(identifier).isEmpty() || env.lookup(identifier).get().isInitialized()) {
        return new CheckResult.INCORRECT(
            env,
            String.format(
                "Trying to initialize the undeclared variable: %s on line: %d and"
                    + " column: %d",
                identifier, identifierNode.line(), identifierNode.column()));
      }
      children = children.subList(1, children.size());
    }

    if (node instanceof IdentifierNode identifierNode) {
      if (env.lookup(identifierNode.name()).isEmpty()) {
        return new CheckResult.INCORRECT(
            env,
            String.format(
                "Usage of unitialized identifier: %s on line: %d and column: %d",
                identifierNode.name(),
                identifierNode.line(),
                identifierNode.column()));
      }
    }

    for (ExpressionNode child : children) {
      switch (this.handle(child, env)) {
        case CheckResult.INCORRECT I:
          return I;
        default:
          break;
      }
    }

    return new CheckResult.CORRECT(env);
  }
}
