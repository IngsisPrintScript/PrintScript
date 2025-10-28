/*
 * My Project
 */

package com.ingsis.semantic.checkers;

import com.ingsis.nodes.Node;
import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.nodes.keyword.LetKeywordNode;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.semantic.checkers.publishers.GenericNodeEventPublisher;
import com.ingsis.semantic.checkers.publishers.factories.PublishersFactory;
import com.ingsis.visitors.Checker;
import java.util.LinkedHashMap;
import java.util.Map;

public final class EventsChecker implements Checker {
  private final Map<Class<? extends Node>, GenericNodeEventPublisher<? extends Node>> eventPublishers;

  public EventsChecker(PublishersFactory publishersFactory) {
    this.eventPublishers = new LinkedHashMap<>();
    eventPublishers.put(LetKeywordNode.class, publishersFactory.createLetNodePublisher());
    eventPublishers.put(
        ExpressionNode.class, publishersFactory.createExpressionNodePublisher());
  }

  @Override
  public Result<String> check(IfKeywordNode ifKeywordNode) {
    return dispatch(ifKeywordNode, IfKeywordNode.class);
  }

  @Override
  public Result<String> check(LetKeywordNode letKeywordNode) {
    return dispatch(letKeywordNode, LetKeywordNode.class);
  }

  @Override
  public Result<String> check(ExpressionNode expressionNode) {
    return dispatch(expressionNode, ExpressionNode.class);
  }

  @SuppressWarnings("unchecked")
  private <T extends Node> Result<String> dispatch(T node, Class<?> mapClass) {
    if (!eventPublishers.containsKey(mapClass)) {
      return new IncorrectResult<>("No publisher for node type: " + node.getClass());
    }
    return ((GenericNodeEventPublisher<T>) eventPublishers.get(mapClass)).notify(node);
  }
}
