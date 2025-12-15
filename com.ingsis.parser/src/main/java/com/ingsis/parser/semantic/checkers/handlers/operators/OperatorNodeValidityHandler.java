/*
 * My Project
 */

package com.ingsis.parser.semantic.checkers.handlers.operators;

import com.ingsis.utils.evalstate.env.semantic.SemanticEnvironment;
import com.ingsis.utils.nodes.expressions.CallFunctionNode;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.expressions.IdentifierNode;
import com.ingsis.utils.nodes.expressions.LiteralNode;
import com.ingsis.utils.nodes.expressions.OperatorNode;
import com.ingsis.utils.nodes.visitors.CheckResult;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import com.ingsis.utils.type.typer.literal.DefaultLiteralTypeGetter;
import com.ingsis.utils.type.types.Types;
import com.ingsis.utils.typer.expression.DefaultExpressionTypeGetter;
import com.ingsis.utils.typer.function.DefaultFunctionTypeGetter;
import com.ingsis.utils.typer.identifier.DefaultIdentifierTypeGetter;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class OperatorNodeValidityHandler implements NodeEventHandler<ExpressionNode> {
  @Override
  public CheckResult handle(ExpressionNode node, SemanticEnvironment env) {
    if (node instanceof OperatorNode operatorNode) {
      if (operatorNode.symbol().equals("+")) {
        return new CheckResult.CORRECT(env);
      }
      Types expectedType = new DefaultExpressionTypeGetter()
          .getType(operatorNode.children().get(0), env);
      return recursiveCheck(expectedType, node, env);
    }

    return new CheckResult.CORRECT(env);
  }

  private CheckResult recursiveCheck(Types expectedType, ExpressionNode node, SemanticEnvironment env) {
    if (node instanceof LiteralNode literalNode) {
      return checkLiteral(expectedType, literalNode, env);
    } else if (node instanceof IdentifierNode identifierNode) {
      return checkIdentifier(expectedType, identifierNode, env);
    } else if (node instanceof CallFunctionNode callFunctionNode) {
      return checkFunction(expectedType, callFunctionNode, env);
    }

    for (ExpressionNode child : node.children()) {
      CheckResult checkTypeResult = recursiveCheck(expectedType, child, env);
      switch (checkTypeResult) {
        case CheckResult.INCORRECT I:
          return I;
        case CheckResult.CORRECT C:
          break;
      }
    }

    return new CheckResult.CORRECT(env);
  }

  private CheckResult checkLiteral(Types expectedType, LiteralNode literalNode, SemanticEnvironment env) {
    boolean checkResult = new DefaultLiteralTypeGetter().getType(literalNode, env).isCompatibleWith(expectedType);
    if (checkResult) {
      return new CheckResult.CORRECT(env);
    }
    return new CheckResult.INCORRECT(env,
        String.format(
            "Literal:%s has an unexpected type on line:%d and column:%d",
            literalNode.value(), literalNode.line(), literalNode.column()));
  }

  private CheckResult checkIdentifier(Types expectedType, IdentifierNode identifierNode, SemanticEnvironment env) {
    boolean checkResult = new DefaultIdentifierTypeGetter()
        .getType(identifierNode, env)
        .isCompatibleWith(expectedType);
    if (checkResult) {
      return new CheckResult.CORRECT(env);
    }
    return new CheckResult.INCORRECT(env,
        String.format(
            "Identifier:%s has an unexpected type on line:%d and column:%d",
            identifierNode.name(), identifierNode.line(), identifierNode.column()));
  }

  private CheckResult checkFunction(Types expectedType, CallFunctionNode callFunctionNode, SemanticEnvironment env) {
    boolean check = new DefaultFunctionTypeGetter()
        .getType(callFunctionNode, env)
        .isCompatibleWith(expectedType);
    if (check) {
      return new CheckResult.CORRECT(env);
    }
    return new CheckResult.INCORRECT(env,
        String.format(
            "Function: %s does not return correct type on line:%d and column:%d",
            callFunctionNode.identifierNode().name(),
            callFunctionNode.line(),
            callFunctionNode.column()));
  }
}
