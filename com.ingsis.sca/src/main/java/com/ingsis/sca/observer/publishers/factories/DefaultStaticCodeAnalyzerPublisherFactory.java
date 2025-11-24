/*
 * My Project
 */

package com.ingsis.sca.observer.publishers.factories;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.rule.observer.handlers.factories.HandlerFactory;
import com.ingsis.rule.observer.publishers.GenericNodeEventPublisher;
import com.ingsis.rule.observer.publishers.factories.PublishersFactory;

public class DefaultStaticCodeAnalyzerPublisherFactory implements PublishersFactory {
    private final HandlerFactory handlerFactory;

    public DefaultStaticCodeAnalyzerPublisherFactory(HandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
    }

    @Override
    public GenericNodeEventPublisher<DeclarationKeywordNode> createLetNodePublisher() {
        return new GenericNodeEventPublisher<>(handlerFactory.createDeclarationHandler());
    }

    @Override
    public GenericNodeEventPublisher<IfKeywordNode> createConditionalNodePublisher() {
        return new GenericNodeEventPublisher<>(handlerFactory.createConditionalHandler());
    }

    @Override
    public GenericNodeEventPublisher<ExpressionNode> createExpressionNodePublisher() {
        return new GenericNodeEventPublisher<>(handlerFactory.createExpressionHandler());
    }
}
