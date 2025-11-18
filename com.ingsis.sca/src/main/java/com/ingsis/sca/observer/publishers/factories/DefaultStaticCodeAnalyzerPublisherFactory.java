/*
 * My Project
 */

package com.ingsis.sca.observer.publishers.factories;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.rule.observer.handlers.NodeEventHandler;
import com.ingsis.rule.observer.publishers.GenericNodeEventPublisher;
import com.ingsis.rule.observer.publishers.factories.PublishersFactory;
import com.ingsis.sca.observer.handlers.factories.HandlerFactory;
import java.util.List;

public class DefaultStaticCodeAnalyzerPublisherFactory implements PublishersFactory {
    private final HandlerFactory handlerFactory;

    public DefaultStaticCodeAnalyzerPublisherFactory(HandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
    }

    @Override
    public GenericNodeEventPublisher<DeclarationKeywordNode> createLetNodePublisher() {
        List<NodeEventHandler<DeclarationKeywordNode>> handlers =
                List.of(handlerFactory.createDeclarationHandler());
        return new GenericNodeEventPublisher<>(handlers);
    }

    @Override
    public GenericNodeEventPublisher<IfKeywordNode> createConditionalNodePublisher() {
        List<NodeEventHandler<IfKeywordNode>> handlers =
                List.of(handlerFactory.createConditionalHandler());
        return new GenericNodeEventPublisher<>(handlers);
    }

    @Override
    public GenericNodeEventPublisher<ExpressionNode> createExpressionNodePublisher() {
        List<NodeEventHandler<ExpressionNode>> handlers =
                List.of(handlerFactory.createExpressionHandler());
        return new GenericNodeEventPublisher<>(handlers);
    }
}
