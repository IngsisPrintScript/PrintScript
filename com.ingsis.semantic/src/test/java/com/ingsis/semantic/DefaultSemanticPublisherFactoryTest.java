/*
 * My Project
 */

package com.ingsis.semantic;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ingsis.result.factory.DefaultResultFactory;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.rule.observer.handlers.factories.HandlersFactory;
import com.ingsis.rule.observer.publishers.GenericNodeEventPublisher;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.runtime.Runtime;
import com.ingsis.semantic.checkers.handlers.factories.DefaultHandlersFactory;
import com.ingsis.semantic.checkers.publishers.factories.DefaultSemanticPublisherFactory;
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

        HandlersFactory handlersFactory = new DefaultHandlersFactory(rt, resultFactory);

        factory = new DefaultSemanticPublisherFactory(handlersFactory);
    }

    @AfterEach
    void tearDown() {
        DefaultRuntime.getInstance().pop();
    }

    @Test
    void createExpressionNodePublisher_shouldReturnPublisher() {
        GenericNodeEventPublisher<?> publisher = factory.createExpressionNodePublisher();
        assertNotNull(publisher);
    }

    @Test
    void createLetNodePublisher_shouldReturnPublisher() {
        GenericNodeEventPublisher<?> publisher = factory.createLetNodePublisher();
        assertNotNull(publisher);
    }

    @Test
    void createConditionalNodePublisher_shouldReturnPublisher() {
        GenericNodeEventPublisher<?> publisher = factory.createConditionalNodePublisher();
        assertNotNull(publisher);
    }
}
