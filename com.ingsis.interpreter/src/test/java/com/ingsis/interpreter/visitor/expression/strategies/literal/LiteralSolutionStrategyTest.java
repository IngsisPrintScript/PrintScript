package com.ingsis.interpreter.visitor.expression.strategies.literal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.interpreter.visitor.expression.strategies.ExpressionSolutionStrategy;
import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.result.Result;
import com.ingsis.visitors.Interpreter;
import org.junit.jupiter.api.Test;

class LiteralSolutionStrategyTest {
    @Test
    void parsesNumberStringAndBoolean() {
        ExpressionSolutionStrategy strat = new LiteralSolutionStrategy(null);
        Interpreter dummy = new Interpreter() {
            @Override
            public com.ingsis.result.Result<String> interpret(com.ingsis.nodes.keyword.IfKeywordNode ifKeywordNode) {
                throw new AssertionError("Should not be called");
            }

            @Override
            public com.ingsis.result.Result<String> interpret(com.ingsis.nodes.keyword.DeclarationKeywordNode declarationKeywordNode) {
                throw new AssertionError("Should not be called");
            }

            @Override
            public com.ingsis.result.Result<Object> interpret(com.ingsis.nodes.expression.ExpressionNode expressionNode) {
                throw new AssertionError("Should not be called");
            }
        };

        Result<Object> r1 = strat.solve(dummy, new LiteralNode("123", 0, 0));
        assertTrue(r1.isCorrect());
        assertEquals(123.0, r1.result());

        Result<Object> r2 = strat.solve(dummy, new LiteralNode("true", 0, 0));
        assertTrue(r2.isCorrect());
        assertEquals(true, r2.result());

        Result<Object> r3 = strat.solve(dummy, new LiteralNode("hello", 0, 0));
        assertTrue(r3.isCorrect());
        assertEquals("hello", r3.result());
    }
}
