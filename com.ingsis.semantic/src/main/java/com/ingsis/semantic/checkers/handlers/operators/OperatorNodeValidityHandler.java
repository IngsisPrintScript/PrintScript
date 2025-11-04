/*
 * My Project
 */

package com.ingsis.semantic.checkers.handlers.operators;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.function.CallFunctionNode;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.nodes.expression.operator.OperatorNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.runtime.Runtime;
import com.ingsis.semantic.checkers.handlers.NodeEventHandler;
import com.ingsis.typer.expression.DefaultExpressionTypeGetter;
import com.ingsis.typer.function.DefaultFunctionTypeGetter;
import com.ingsis.typer.identifier.DefaultIdentifierTypeGetter;
import com.ingsis.typer.literal.DefaultLiteralTypeGetter;
import com.ingsis.types.Types;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class OperatorNodeValidityHandler implements NodeEventHandler<ExpressionNode> {
  private final Runtime runtime;

  public OperatorNodeValidityHandler(Runtime runtime) {
    this.runtime = runtime;
  }

  @Override
  public Result<String> handle(ExpressionNode node) {
    if (node instanceof OperatorNode operatorNode) {
      if (operatorNode.symbol().equals("+")) {
        return new CorrectResult<>(
            "Since it's an addition it will al be casted to string and then operated.");
      }
      Types expectedType = new DefaultExpressionTypeGetter(runtime)
          .getType(operatorNode.children().get(0));
      return recursiveCheck(expectedType, node);
    }

    return new CorrectResult<>("All not operators expression pass this check.");
  }

  private Result<String> recursiveCheck(Types expectedType, ExpressionNode node) {
    if (node instanceof LiteralNode literalNode) {
      boolean checkResult = new DefaultLiteralTypeGetter().getType(literalNode).isCompatibleWith(expectedType);
      if (checkResult) {
        return new CorrectResult<>("Literal matches expected type.");
      } else {
        return new IncorrectResult<>("Literal does not match expected type.");
      }
    } else if (node instanceof IdentifierNode identifierNode) {
      boolean checkResult = new DefaultIdentifierTypeGetter(runtime)
          .getType(identifierNode)
          .isCompatibleWith(expectedType);
      if (checkResult) {
        return new CorrectResult<>("Identifier matches expected type.");
      } else {
        return new IncorrectResult<>("Identifier does not match expected type.");
      }
    } else if (node instanceof CallFunctionNode callFunctionNode) {
      Boolean check = new DefaultFunctionTypeGetter(runtime).getType(callFunctionNode).isCompatibleWith(expectedType);
      if (check) {
        return new CorrectResult<>("Function returns needed type");
      } else {
        return new IncorrectResult<>("Function does not return needed type");
      }
    }
    List<ExpressionNode> children = node.children();

    for (ExpressionNode child : children) {
      Result<String> checkTypeResult = recursiveCheck(expectedType, child);
      if (!checkTypeResult.isCorrect()) {
        return new IncorrectResult<>(checkTypeResult);
      }
    }

    return new CorrectResult<>("Types matched.");
  }
}
