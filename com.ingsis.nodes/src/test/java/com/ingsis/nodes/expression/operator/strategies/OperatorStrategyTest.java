package com.ingsis.nodes.expression.operator.strategies;

import com.ingsis.result.CorrectResult;
import com.ingsis.result.Result;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OperatorStrategyTest {
    @Test
    public void executeIsCalled() {
        OperatorStrategy strat = args -> new CorrectResult<>("done");
        Result<Object> r = strat.execute(List.of());
        assertEquals("done", r.result());
    }
}
