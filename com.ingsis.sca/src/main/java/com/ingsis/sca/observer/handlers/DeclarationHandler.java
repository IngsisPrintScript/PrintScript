/*
 * My Project
 */

package com.ingsis.sca.observer.handlers;

import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.expressions.atomic.identifier.IdentifierNode;
import com.ingsis.utils.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;

public class DeclarationHandler implements NodeEventHandler<DeclarationKeywordNode> {
  private final NodeEventHandler<IdentifierNode> identifierChecker;
  private final NodeEventHandler<ExpressionNode> expressionChecker;

  public DeclarationHandler(
      NodeEventHandler<IdentifierNode> identifierChecker,
      NodeEventHandler<ExpressionNode> expressionChecker) {
    this.identifierChecker = identifierChecker;
    this.expressionChecker = expressionChecker;
  }

  @Override
  public Result<String> handle(DeclarationKeywordNode node) {
    IdentifierNode identifierNode = node.identifierNode();
    Result<String> identifierCheckResult = identifierChecker.handle(identifierNode);
    if (!identifierCheckResult.isCorrect()) {
      return identifierCheckResult;
    }
    ExpressionNode expressionNode = node.expressionNode();
    return expressionChecker.handle(expressionNode);
  }
}
