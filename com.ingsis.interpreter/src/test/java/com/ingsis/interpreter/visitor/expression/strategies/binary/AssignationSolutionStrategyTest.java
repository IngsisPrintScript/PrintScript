/*
 * My Project
 */

package com.ingsis.interpreter.visitor.expression.strategies.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.nodes.expression.operator.BinaryOperatorNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.Result;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.runtime.Runtime;
import com.ingsis.types.Types;
import com.ingsis.visitors.Interpreter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AssignationSolutionStrategyTest {
    private Runtime runtime;

    @BeforeEach
    void before() {
        runtime = DefaultRuntime.getInstance();
        runtime.push();
        // create mutable variable
        runtime.getCurrentEnvironment().createVariable("a", Types.NUMBER);
        runtime.getCurrentEnvironment().updateVariable("a", 1.0);
    }

    @AfterEach
    void after() {
        runtime.pop();
    }

    @Test
    void assignsNewValueWhenCompatible() {
        AssignationSolutionStrategy strat =
                new AssignationSolutionStrategy(
                        runtime,
                        new com.ingsis.interpreter.visitor.expression.strategies.FinalStrategy());
        BinaryOperatorNode node =
                new BinaryOperatorNode(
                        "=", new IdentifierNode("a", 0, 0), new LiteralNode("5", 0, 0), 0, 0);

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
                            com.ingsis.nodes.expression.ExpressionNode expr) {
                        if (expr instanceof LiteralNode l)
                            return new CorrectResult<>(Double.parseDouble(l.value()));
                        return new CorrectResult<>(null);
                    }
                };

        Result<Object> r = strat.solve(stub, node);
        assertTrue(r.isCorrect());
        assertEquals(5.0, r.result());
    }
}
