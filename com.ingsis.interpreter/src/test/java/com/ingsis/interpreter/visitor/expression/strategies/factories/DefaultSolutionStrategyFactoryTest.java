package com.ingsis.interpreter.visitor.expression.strategies.factories;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ingsis.interpreter.visitor.expression.strategies.ExpressionSolutionStrategy;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.runtime.Runtime;
import org.junit.jupiter.api.Test;

class DefaultSolutionStrategyFactoryTest {
    @Test
    void buildsNonNullChain() {
        Runtime runtime = DefaultRuntime.getInstance();
        DefaultSolutionStrategyFactory f = new DefaultSolutionStrategyFactory(runtime);
        ExpressionSolutionStrategy s = f.constructDefaultStrategy();
        assertNotNull(s);
    }
}
