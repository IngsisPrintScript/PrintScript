/*
 * My Project
 */

package com.ingsis.parser.semantic;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ingsis.parser.semantic.checkers.publishers.factories.DefaultSemanticPublisherFactory;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.runtime.Runtime;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import com.ingsis.utils.rule.observer.handlers.factories.HandlerFactory;
import com.ingsis.utils.rule.observer.publishers.GenericNodeEventPublisher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultSemanticPublisherFactoryTest {
    private DefaultSemanticPublisherFactory factory;

    @BeforeEach
    void setUp() {
        Runtime rt = DefaultRuntime.getInstance();
        rt.push();
        ResultFactory resultFactory = new DefaultResultFactory();

        HandlerFactory handlersFactory =
                new HandlerFactory() {
                    @Override
                    public NodeEventHandler<DeclarationKeywordNode> createDeclarationHandler() {
                        return node -> new DefaultResultFactory().createCorrectResult("ok");
                    }

                    @Override
                    public NodeEventHandler<IfKeywordNode> createConditionalHandler() {
                        return node -> new DefaultResultFactory().createCorrectResult("ok");
                    }

                    @Override
                    public NodeEventHandler<ExpressionNode> createExpressionHandler() {
                        return node -> new DefaultResultFactory().createCorrectResult("ok");
                    }
                };

        factory = new DefaultSemanticPublisherFactory(handlersFactory);
    }

    @AfterEach
    void tearDown() {
        DefaultRuntime.getInstance().pop();
    }

    @Test
    void createExpressionNodePublisherShouldReturnPublisher() {
        GenericNodeEventPublisher<?> publisher = factory.createExpressionNodePublisher();
        assertNotNull(publisher);
    }

    @Test
    void createLetNodePublisherShouldReturnPublisher() {
        GenericNodeEventPublisher<?> publisher = factory.createLetNodePublisher();
        assertNotNull(publisher);
    }

    @Test
    void createConditionalNodePublisherShouldReturnPublisher() {
        GenericNodeEventPublisher<?> publisher = factory.createConditionalNodePublisher();
        assertNotNull(publisher);
    }
}
