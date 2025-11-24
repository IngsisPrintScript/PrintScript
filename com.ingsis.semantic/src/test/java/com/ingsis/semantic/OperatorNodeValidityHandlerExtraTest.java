/*
 * My Project
 */

package com.ingsis.semantic;

import static org.junit.jupiter.api.Assertions.*;

import com.ingsis.nodes.expression.function.CallFunctionNode;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.nodes.expression.operator.BinaryOperatorNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.result.factory.DefaultResultFactory;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.runtime.Runtime;
import com.ingsis.semantic.checkers.handlers.operators.OperatorNodeValidityHandler;
import com.ingsis.types.Types;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperatorNodeValidityHandlerExtraTest {
    private Runtime runtime;
    private ResultFactory resultFactory;
    private OperatorNodeValidityHandler handler;

    @BeforeEach
    void setUp() {
        runtime = DefaultRuntime.getInstance();
        runtime.push();
        resultFactory = new DefaultResultFactory();
        handler = new OperatorNodeValidityHandler(runtime, resultFactory);
    }

    @AfterEach
    void tearDown() {
        runtime.pop();
    }

    @Test
    void handle_literalMismatch_shouldReturnIncorrect() {
        BinaryOperatorNode node =
                new BinaryOperatorNode(
                        "-", new LiteralNode("1", 1, 1), new LiteralNode("true", 1, 2), 1, 1);

        Result<String> result = handler.handle(node);

        assertFalse(result.isCorrect());
        assertInstanceOf(IncorrectResult.class, result);
    }

    @Test
    void handle_identifierMatch_shouldReturnCorrect() {
        // create variable x with NUMBER type
        runtime.getCurrentEnvironment().createVariable("x", Types.NUMBER, 1);

        BinaryOperatorNode node =
                new BinaryOperatorNode(
                        "-", new IdentifierNode("x", 1, 1), new IdentifierNode("x", 1, 2), 1, 1);

        Result<String> result = handler.handle(node);

        assertTrue(result.isCorrect());
        assertInstanceOf(CorrectResult.class, result);
    }

    @Test
    void handle_functionMismatch_shouldReturnIncorrect() {
        // create function f returning STRING
        runtime.getCurrentEnvironment().createFunction("f", Map.of(), Types.STRING);

        CallFunctionNode call =
                new CallFunctionNode(new IdentifierNode("f", 1, 1), List.of(), 1, 1);
        BinaryOperatorNode node =
                new BinaryOperatorNode("-", new LiteralNode("1", 1, 1), call, 1, 1);

        Result<String> result = handler.handle(node);

        assertFalse(result.isCorrect());
        assertInstanceOf(IncorrectResult.class, result);
    }

    @Test
    void handle_functionMatch_shouldReturnCorrect() {
        // create function f returning NUMBER
        runtime.getCurrentEnvironment().createFunction("g", Map.of(), Types.NUMBER);

        CallFunctionNode call =
                new CallFunctionNode(new IdentifierNode("g", 1, 1), List.of(), 1, 1);
        BinaryOperatorNode node =
                new BinaryOperatorNode("-", new LiteralNode("1", 1, 1), call, 1, 1);

        Result<String> result = handler.handle(node);

        assertTrue(result.isCorrect());
        assertInstanceOf(CorrectResult.class, result);
    }
}
