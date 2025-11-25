/*
 * My Project
 */

package com.ingsis.semantic;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ingsis.result.factory.DefaultResultFactory;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.runtime.Runtime;
import com.ingsis.semantic.checkers.handlers.factories.DefaultHandlersFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultHandlersFactoryTest {

    private Runtime runtime;
    private ResultFactory resultFactory;
    private DefaultHandlersFactory factory;

    @BeforeEach
    public void setUp() {
        runtime = DefaultRuntime.getInstance();
        runtime.push();
        resultFactory = new DefaultResultFactory();
        factory = new DefaultHandlersFactory(runtime, resultFactory);
    }

    @AfterEach
    public void tearDown() {
        runtime.pop();
    }

    @Test
    public void constructorShouldInstantiate() {
        assertNotNull(factory);
    }

    @Test
    public void exerciseFactoryMethodsWithoutFailing() {
        try {
            factory.createDeclarationHandler();
        } catch (UnsupportedOperationException ignored) {
        }

        try {
            factory.createConditionalHandler();
        } catch (UnsupportedOperationException ignored) {
        }

        try {
            factory.createExpressionHandler();
        } catch (UnsupportedOperationException ignored) {
        }
    }
}
