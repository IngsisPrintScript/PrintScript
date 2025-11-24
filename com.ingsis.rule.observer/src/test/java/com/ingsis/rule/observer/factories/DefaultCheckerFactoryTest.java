/*
 * My Project
 */

package com.ingsis.rule.observer.factories;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.rule.observer.handlers.NodeEventHandler;
import com.ingsis.rule.observer.publishers.GenericNodeEventPublisher;
import com.ingsis.rule.observer.publishers.factories.PublishersFactory;
import java.util.List;
import org.junit.jupiter.api.Test;

class DefaultCheckerFactoryTest {

    @Test
    void createsEventsCheckerWithPublishers() {
        PublishersFactory pf =
                new PublishersFactory() {
                    @Override
                    public GenericNodeEventPublisher<DeclarationKeywordNode>
                            createLetNodePublisher() {
                        NodeEventHandler<DeclarationKeywordNode> h = n -> new CorrectResult<>("ok");
                        return new GenericNodeEventPublisher<>(List.of(h));
                    }

                    @Override
                    public GenericNodeEventPublisher<IfKeywordNode>
                            createConditionalNodePublisher() {
                        NodeEventHandler<IfKeywordNode> h = n -> new CorrectResult<>("ok");
                        return new GenericNodeEventPublisher<>(List.of(h));
                    }

                    @Override
                    public GenericNodeEventPublisher<ExpressionNode>
                            createExpressionNodePublisher() {
                        NodeEventHandler<ExpressionNode> h = n -> new CorrectResult<>("ok");
                        return new GenericNodeEventPublisher<>(List.of(h));
                    }
                };

        DefaultCheckerFactory f = new DefaultCheckerFactory();
        assertNotNull(f.createInMemoryEventBasedChecker(pf));
    }
}
