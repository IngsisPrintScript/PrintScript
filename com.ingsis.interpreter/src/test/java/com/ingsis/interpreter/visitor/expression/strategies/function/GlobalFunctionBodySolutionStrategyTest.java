/*
 * My Project
 */

package com.ingsis.interpreter.visitor.expression.strategies.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.interpreter.visitor.expression.strategies.ExpressionSolutionStrategy;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.runtime.Runtime;
import com.ingsis.utils.nodes.nodes.expression.function.GlobalFunctionBody;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.type.types.Types;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GlobalFunctionBodySolutionStrategyTest {
    private Runtime runtime;

    @BeforeEach
    void before() {
        runtime = DefaultRuntime.getInstance();
        runtime.push();
        runtime.getCurrentEnvironment().createVariable("a", Types.NUMBER, 21.0);
    }

    @AfterEach
    void after() {
        runtime.pop();
    }

    @Test
    void evaluatesLambdaWithRuntimeParameters() {
        ExpressionSolutionStrategy next =
                (i, e) -> {
                    throw new AssertionError();
                };
        GlobalFunctionBodySolutionStrategy strat =
                new GlobalFunctionBodySolutionStrategy(runtime, next);

        GlobalFunctionBody body =
                new GlobalFunctionBody(List.of("a"), (Object[] arr) -> ((Double) arr[0]) * 2, 0, 0);

        Result<Object> res = strat.solve((Interpreter) null, body);
        assertTrue(res.isCorrect());
        assertEquals(42.0, res.result());
    }
}
