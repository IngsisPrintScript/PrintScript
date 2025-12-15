/*
 * My Project
 */

package com.ingsis.sca.observer.publishers.factories;

import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.rule.observer.handlers.factories.HandlerSupplier;
import com.ingsis.utils.rule.observer.publishers.GenericNodeEventPublisher;
import com.ingsis.utils.rule.observer.publishers.factories.PublishersFactory;

public class DefaultStaticCodeAnalyzerPublisherFactory implements PublishersFactory {
  private final HandlerSupplier handlerFactory;

  public DefaultStaticCodeAnalyzerPublisherFactory(HandlerSupplier handlerFactory) {
    this.handlerFactory = handlerFactory;
  }

  @Override
  public GenericNodeEventPublisher<DeclarationKeywordNode> createLetNodePublisher() {
    return new GenericNodeEventPublisher<>(handlerFactory.getDeclarationHandler());
  }

  @Override
  public GenericNodeEventPublisher<IfKeywordNode> createConditionalNodePublisher() {
    return new GenericNodeEventPublisher<>(handlerFactory.getConditionalHandler());
  }

  @Override
  public GenericNodeEventPublisher<ExpressionNode> createExpressionNodePublisher() {
    return new GenericNodeEventPublisher<>(handlerFactory.getExpressionHandler());
  }
}
