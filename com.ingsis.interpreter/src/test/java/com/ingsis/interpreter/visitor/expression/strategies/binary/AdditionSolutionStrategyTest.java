/*
 * My Project
 */

package com.ingsis.interpreter.visitor.expression.strategies.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.interpreter.visitor.expression.strategies.ExpressionSolutionStrategy;
import com.ingsis.interpreter.visitor.expression.strategies.FinalStrategy;
import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.nodes.expression.operator.BinaryOperatorNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.Result;
import com.ingsis.visitors.Interpreter;
import org.junit.jupiter.api.Test;

class AdditionSolutionStrategyTest {
    @Test
    void addsNumbersAndConcatenatesStringsAndBooleans() {
        ExpressionSolutionStrategy strat = new AdditionSolutionStrategy(new FinalStrategy());

        Interpreter stub = createStubInterpreter();

        assertSolve(strat, stub, "1", "2", 3.0);
        assertSolve(strat, stub, "hello", " world", "hello world");
        assertSolve(strat, stub, "true", "false", true);
    }

    private Interpreter createStubInterpreter() {
        return new Interpreter() {
            @Override
            public com.ingsis.result.Result<String> interpret(IfKeywordNode ifKeywordNode) {
                throw new AssertionError("Should not be called");
            }

            @Override
            public com.ingsis.result.Result<String> interpret(
                    DeclarationKeywordNode declarationKeywordNode) {
                throw new AssertionError("Should not be called");
            }

            @Override
            public com.ingsis.result.Result<Object> interpret(ExpressionNode node) {
                if (node instanceof LiteralNode l) {
                    String v = l.value();
                    if (v.equals("1")) return new CorrectResult<>(1.0);
                    if (v.equals("2")) return new CorrectResult<>(2.0);
                    if (v.equals("hello")) return new CorrectResult<>("hello");
                    if (v.equals(" world")) return new CorrectResult<>(" world");
                    if (v.equals("true")) return new CorrectResult<>(true);
                    if (v.equals("false")) return new CorrectResult<>(false);
                }
                return new CorrectResult<>(null);
            }
        };
    }

    private void assertSolve(
            ExpressionSolutionStrategy strat,
            Interpreter stub,
            String left,
            String right,
            Object expected) {
        BinaryOperatorNode node =
                new BinaryOperatorNode(
                        "+", new LiteralNode(left, 0, 0), new LiteralNode(right, 0, 0), 0, 0);
        Result<Object> res = strat.solve(stub, node);
        assertTrue(res.isCorrect());
        assertEquals(expected, res.result());
    }
}
