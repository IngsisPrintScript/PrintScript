/*
 * My Project
 */

package com.ingsis.semantic.checkers;

import java.util.LinkedHashMap;
import java.util.Map;

import com.ingsis.nodes.Node;
import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.function.CallFunctionNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.nodes.keyword.LetKeywordNode;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.semantic.checkers.publishers.GenericNodeEventPublisher;
import com.ingsis.semantic.checkers.publishers.factories.PublishersFactory;
import com.ingsis.visitors.Checker;

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
    return dispatch(ifKeywordNode);
  }

  @Override
  public Result<String> check(LetKeywordNode letKeywordNode) {
    return dispatch(letKeywordNode);
  }

  @Override
  public Result<String> check(ExpressionNode expressionNode) {
    return dispatch(expressionNode);
  }

  @SuppressWarnings("unchecked")
  private <T extends Node> Result<String> dispatch(T node) {
    Class<T> nodeClass = (Class<T>) node.getClass();
    GenericNodeEventPublisher<T> eventPublisher = (GenericNodeEventPublisher<T>) eventPublishers.get(nodeClass);
    if (eventPublisher == null) {
      return new IncorrectResult<>("No publisher for node type: " + nodeClass);
    }
    return eventPublisher.notify(node);
  }

  @Override
  public Result<String> check(CallFunctionNode callFunctionNode) {
    return dispatch(callFunctionNode);
  }
}
