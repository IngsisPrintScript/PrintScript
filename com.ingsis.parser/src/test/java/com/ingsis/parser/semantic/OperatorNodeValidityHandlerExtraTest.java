/*
 * My Project
 */

package com.ingsis.parser.semantic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.parser.semantic.checkers.handlers.operators.OperatorNodeValidityHandler;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.runtime.Runtime;
import com.ingsis.utils.nodes.nodes.expression.function.CallFunctionNode;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.nodes.expression.literal.LiteralNode;
import com.ingsis.utils.nodes.nodes.expression.operator.BinaryOperatorNode;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.type.types.Types;
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
    void handleLiteralMismatchShouldReturnIncorrect() {
        BinaryOperatorNode node =
                new BinaryOperatorNode(
                        "-", new LiteralNode("1", 1, 1), new LiteralNode("true", 1, 2), 1, 1);

        Result<String> result = handler.handle(node);

        assertFalse(result.isCorrect());
        assertInstanceOf(IncorrectResult.class, result);
    }

    @Test
    void handleIdentifierMatchShouldReturnCorrect() {
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
    void handleFunctionMismatchShouldReturnIncorrect() {
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
    void handleFunctionMatchShouldReturnCorrect() {
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
