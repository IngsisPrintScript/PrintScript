/*
 * My Project
 */

package com.ingsis.semantic;

import static org.junit.jupiter.api.Assertions.*;

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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperatorNodeValidityHandlerTest {

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
    void handle_whenSymbolIsPlus_shouldReturnCorrect() {
        LiteralNode left = new LiteralNode("1", 1, 1);
        LiteralNode right = new LiteralNode("2", 1, 2);
        BinaryOperatorNode node = new BinaryOperatorNode("+", left, right, 1, 1);

        Result<String> result = handler.handle(node);

        assertInstanceOf(CorrectResult.class, result);
        assertTrue(((CorrectResult<String>) result).isCorrect());
    }

    @Test
    void handle_whenChildrenMatchExpectedType_shouldReturnCorrect() {
        // left is number -> expected NUMBER, right is number -> OK
        LiteralNode left = new LiteralNode("123", 2, 1);
        LiteralNode right = new LiteralNode("456", 2, 2);
        BinaryOperatorNode node = new BinaryOperatorNode("-", left, right, 2, 1);

        Result<String> result = handler.handle(node);

        assertInstanceOf(CorrectResult.class, result);
        assertTrue(((CorrectResult<String>) result).isCorrect());
    }

    @Test
    void handle_whenChildrenMismatchExpectedType_shouldReturnIncorrect() {
        // left is number -> expected NUMBER, right is boolean -> mismatch
        LiteralNode left = new LiteralNode("123", 3, 1);
        LiteralNode right = new LiteralNode("true", 3, 2);
        BinaryOperatorNode node = new BinaryOperatorNode("-", left, right, 3, 1);

        Result<String> result = handler.handle(node);

        assertInstanceOf(IncorrectResult.class, result);
        assertFalse(result.isCorrect());
    }
}
