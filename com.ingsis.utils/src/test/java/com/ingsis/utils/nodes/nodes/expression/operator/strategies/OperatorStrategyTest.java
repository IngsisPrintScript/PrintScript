/*
 * My Project
 */

package com.ingsis.utils.nodes.nodes.expression.operator.strategies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.Result;
import java.util.List;
import org.junit.jupiter.api.Test;

public class OperatorStrategyTest {
    @Test
    public void executeIsCalled() {
        OperatorStrategy strat = args -> new CorrectResult<>("done");
        Result<Object> r = strat.execute(List.of());
        assertEquals("done", r.result());
    }
}
