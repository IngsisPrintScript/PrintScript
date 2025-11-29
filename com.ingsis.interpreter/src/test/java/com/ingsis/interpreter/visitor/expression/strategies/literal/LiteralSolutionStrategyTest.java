/*
 * My Project
 */

package com.ingsis.interpreter.visitor.expression.strategies.literal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.interpreter.visitor.expression.strategies.ExpressionSolutionStrategy;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.literal.LiteralNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.result.Result;
import org.junit.jupiter.api.Test;

class LiteralSolutionStrategyTest {
    @Test
    void parsesNumberStringAndBoolean() {
        ExpressionSolutionStrategy strat = new LiteralSolutionStrategy(null);
        Interpreter dummy =
                new Interpreter() {
                    @Override
                    public Result<String> interpret(IfKeywordNode ifKeywordNode) {
                        throw new AssertionError("Should not be called");
                    }

                    @Override
                    public Result<String> interpret(DeclarationKeywordNode declarationKeywordNode) {
                        throw new AssertionError("Should not be called");
                    }

                    @Override
                    public Result<Object> interpret(ExpressionNode expressionNode) {
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
