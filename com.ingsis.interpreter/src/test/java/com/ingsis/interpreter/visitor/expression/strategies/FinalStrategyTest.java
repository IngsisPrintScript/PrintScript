/*
 * My Project
 */

package com.ingsis.interpreter.visitor.expression.strategies;

import static org.junit.jupiter.api.Assertions.assertFalse;

import com.ingsis.utils.nodes.nodes.expression.literal.LiteralNode;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.result.Result;
import org.junit.jupiter.api.Test;

class FinalStrategyTest {
    @Test
    void returnsIncorrectForAnyExpression() {
        FinalStrategy s = new FinalStrategy();
        Result<Object> r = s.solve((Interpreter) null, new LiteralNode("x", 0, 0));
        assertFalse(r.isCorrect());
    }
}
