/*
 * My Project
 */

package com.ingsis.interpreter.visitor.expression.strategies.identifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.interpreter.visitor.expression.strategies.ExpressionSolutionStrategy;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.runtime.Runtime;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.type.types.Types;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IdentifierSolutionStrategyTest {
    private Runtime runtime;

    @BeforeEach
    void before() {
        runtime = DefaultRuntime.getInstance();
        runtime.push();
        runtime.getCurrentEnvironment().createVariable("x", Types.NUMBER, 42.0);
    }

    @AfterEach
    void after() {
        runtime.pop();
    }

    @Test
    void readsValueFromRuntime() {
        ExpressionSolutionStrategy next =
                (i, e) -> {
                    throw new AssertionError("Should not be called");
                };
        IdentifierSolutionStrategy strat = new IdentifierSolutionStrategy(next, runtime);

        Result<Object> r = strat.solve((Interpreter) null, new IdentifierNode("x", 0, 0));
        assertTrue(r.isCorrect());
        assertEquals(42.0, r.result());
    }
}
