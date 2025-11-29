/*
 * My Project
 */

package com.ingsis.interpreter.visitor.expression.strategies.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.literal.LiteralNode;
import com.ingsis.utils.nodes.nodes.expression.operator.BinaryOperatorNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.Result;
import org.junit.jupiter.api.Test;

class SubstractionSolutionStrategyTest {
    @Test
    void subtractsNumbers() {
        SubstractionSolutionStrategy strat =
                new SubstractionSolutionStrategy(
                        new com.ingsis.interpreter.visitor.expression.strategies.FinalStrategy());
        Interpreter stub =
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
                    public Result<Object> interpret(ExpressionNode node) {
                        if (node instanceof LiteralNode l)
                            return new CorrectResult<>(Double.parseDouble(l.value()));
                        return new CorrectResult<>(null);
                    }
                };

        BinaryOperatorNode op =
                new BinaryOperatorNode(
                        "-", new LiteralNode("6", 0, 0), new LiteralNode("2", 0, 0), 0, 0);
        Result<Object> res = strat.solve(stub, op);
        assertTrue(res.isCorrect());
        assertEquals(4.0, res.result());
    }
}
