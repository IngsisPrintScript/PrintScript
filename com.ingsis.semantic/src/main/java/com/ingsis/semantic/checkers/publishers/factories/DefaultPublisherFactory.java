/*
 * My Project
 */

package com.ingsis.semantic.checkers.publishers.factories;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.function.CallFunctionNode;
import com.ingsis.nodes.keyword.LetKeywordNode;
import com.ingsis.semantic.checkers.handlers.NodeEventHandler;
import com.ingsis.semantic.checkers.handlers.factories.HandlersFactory;
import com.ingsis.semantic.checkers.publishers.GenericNodeEventPublisher;
import java.util.ArrayList;
import java.util.Collection;

public final class DefaultPublisherFactory implements PublishersFactory {
  private final HandlersFactory handlersFactory;

  public DefaultPublisherFactory(HandlersFactory handlersFactory) {
    this.handlersFactory = handlersFactory;
  }

  @Override
  public GenericNodeEventPublisher<LetKeywordNode> createLetNodePublisher() {
    return new GenericNodeEventPublisher<>(createLetNodeEventHandlers());
  }

  private Collection<NodeEventHandler<LetKeywordNode>> createLetNodeEventHandlers() {
    Collection<NodeEventHandler<LetKeywordNode>> handlers = new ArrayList<>();
    handlers.add(handlersFactory.createLetVariableExistenceHandler());
    handlers.add(handlersFactory.createLetCorrectTypeHandler());
    return handlers;
  }

  @Override
  public GenericNodeEventPublisher<ExpressionNode> createExpressionNodePublisher() {
    return new GenericNodeEventPublisher<ExpressionNode>(createExpressionNodeEventHandlers());
  }

  private Collection<NodeEventHandler<ExpressionNode>> createExpressionNodeEventHandlers() {
    Collection<NodeEventHandler<ExpressionNode>> handlers = new ArrayList<>();
    handlers.add(handlersFactory.createExpressionVariableExistenceHandler());
    handlers.add(handlersFactory.createOperatorValidityHandler());
    return handlers;
  }

  @Override
  public GenericNodeEventPublisher<CallFunctionNode> createCallFunctionNodePublisher() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createCallFunctionNodePublisher'");
  }
}
