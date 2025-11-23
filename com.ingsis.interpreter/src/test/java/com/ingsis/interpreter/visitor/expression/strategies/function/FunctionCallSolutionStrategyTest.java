package com.ingsis.interpreter.visitor.expression.strategies.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.nodes.expression.function.CallFunctionNode;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.Result;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.runtime.Runtime;
import com.ingsis.types.Types;
import com.ingsis.visitors.Interpreter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FunctionCallSolutionStrategyTest {
    private Runtime runtime;

    @BeforeEach
    void before() {
        runtime = DefaultRuntime.getInstance();
        runtime.push();
        runtime.getCurrentEnvironment().createFunction("f", Map.of("x", Types.NUMBER), Types.NUMBER);
        runtime.getCurrentEnvironment().updateFunction("f", List.of(new LiteralNode("10",0,0)));
    }

    @AfterEach
    void after() {
        runtime.pop();
    }

    @Test
    void callsFunctionAndReturnsBodyValue() {
        FunctionCallSolutionStrategy strat = new FunctionCallSolutionStrategy(runtime, new GlobalFunctionBodySolutionStrategy(runtime, null));

        CallFunctionNode call = new CallFunctionNode(new IdentifierNode("f",0,0), List.of(new LiteralNode("1",0,0)),0,0);

        Interpreter stub = new Interpreter() {
            @Override
            public com.ingsis.result.Result<String> interpret(com.ingsis.nodes.keyword.IfKeywordNode ifKeywordNode) {
                throw new AssertionError("Should not be called");
            }

            @Override
            public com.ingsis.result.Result<String> interpret(com.ingsis.nodes.keyword.DeclarationKeywordNode declarationKeywordNode) {
                throw new AssertionError("Should not be called");
            }

            @Override
            public com.ingsis.result.Result<Object> interpret(com.ingsis.nodes.expression.ExpressionNode node) {
                if (node instanceof LiteralNode l) return new CorrectResult<>(Double.parseDouble(l.value()));
                return new CorrectResult<>(null);
            }
        };

        Result<Object> r = strat.solve(stub, call);
        assertTrue(r.isCorrect());
        assertEquals(10.0, r.result());
    }
}
