/*
 * My Project
 */

package com.ingsis.interpreter.visitor.expression.strategies.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.nodes.expression.operator.BinaryOperatorNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.Result;
import com.ingsis.visitors.Interpreter;
import org.junit.jupiter.api.Test;

class DivitionSolutionStrategyTest {
    @Test
    void dividesNumbers() {
        DivitionSolutionStrategy strat =
                new DivitionSolutionStrategy(
                        new com.ingsis.interpreter.visitor.expression.strategies.FinalStrategy());
        Interpreter stub =
                new Interpreter() {
                    @Override
                    public com.ingsis.result.Result<String> interpret(
                            com.ingsis.nodes.keyword.IfKeywordNode ifKeywordNode) {
                        throw new AssertionError("Should not be called");
                    }

                    @Override
                    public com.ingsis.result.Result<String> interpret(
                            com.ingsis.nodes.keyword.DeclarationKeywordNode
                                    declarationKeywordNode) {
                        throw new AssertionError("Should not be called");
                    }

                    @Override
                    public com.ingsis.result.Result<Object> interpret(
                            com.ingsis.nodes.expression.ExpressionNode node) {
                        if (node instanceof LiteralNode l)
                            return new CorrectResult<>(Double.parseDouble(l.value()));
                        return new CorrectResult<>(null);
                    }
                };

        BinaryOperatorNode op =
                new BinaryOperatorNode(
                        "/", new LiteralNode("6", 0, 0), new LiteralNode("2", 0, 0), 0, 0);
        Result<Object> res = strat.solve(stub, op);
        assertTrue(res.isCorrect());
        assertEquals(3.0, res.result());
    }

    @Test
    void returnsIncorrectOnNonNumeric() {
        DivitionSolutionStrategy strat =
                new DivitionSolutionStrategy(
                        new com.ingsis.interpreter.visitor.expression.strategies.FinalStrategy());
        Interpreter stub =
                new Interpreter() {
                    @Override
                    public com.ingsis.result.Result<String> interpret(
                            com.ingsis.nodes.keyword.IfKeywordNode ifKeywordNode) {
                        throw new AssertionError("Should not be called");
                    }

                    @Override
                    public com.ingsis.result.Result<String> interpret(
                            com.ingsis.nodes.keyword.DeclarationKeywordNode
                                    declarationKeywordNode) {
                        throw new AssertionError("Should not be called");
                    }

                    @Override
                    public com.ingsis.result.Result<Object> interpret(
                            com.ingsis.nodes.expression.ExpressionNode node) {
                        return new CorrectResult<>("notnumber");
                    }
                };
        BinaryOperatorNode op =
                new BinaryOperatorNode(
                        "/", new LiteralNode("a", 0, 0), new LiteralNode("b", 0, 0), 0, 0);
        Result<Object> res = strat.solve(stub, op);
        assertFalse(res.isCorrect());
    }
}
