/*
 * My Project
 */

package com.ingsis.utils.rule.observer;

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.rule.observer.publishers.factories.PublishersFactory;

public final class EventsChecker implements Checker {
  private final PublishersFactory publishersFactory;

  public EventsChecker(PublishersFactory publishersFactory) {
    this.publishersFactory = publishersFactory;
  }

  @Override
  public Result<String> check(IfKeywordNode ifKeywordNode) {
    for (Node child : ifKeywordNode.thenBody()) {
      Result<String> checkChildResult = ((Checkable) child).acceptChecker(this);
      if (!checkChildResult.isCorrect()) {
        return checkChildResult;
      }
    }
    for (Node child : ifKeywordNode.elseBody()) {
      Result<String> checkChildResult = ((Checkable) child).acceptChecker(this);
      if (!checkChildResult.isCorrect()) {
        return checkChildResult;
      }
    }
    return publishersFactory.createConditionalNodePublisher().notify(ifKeywordNode);
  }

  @Override
  public Result<String> check(DeclarationKeywordNode letKeywordNode) {
    return publishersFactory.createLetNodePublisher().notify(letKeywordNode);
  }

  @Override
  public Result<String> check(ExpressionNode expressionNode) {
    return publishersFactory.createExpressionNodePublisher().notify(expressionNode);
  }

}
