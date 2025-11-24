/*
 * My Project
 */

package com.ingsis.semantic;

import static org.junit.jupiter.api.Assertions.*;

import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.result.factory.DefaultResultFactory;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.runtime.Runtime;
import com.ingsis.semantic.checkers.handlers.identifier.type.IfNodeCorrectTypeEventHandler;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IfNodeCorrectTypeEventHandlerTest {

    private Runtime runtime;
    private ResultFactory resultFactory;
    private IfNodeCorrectTypeEventHandler handler;

    @BeforeEach
    void setUp() {
        runtime = DefaultRuntime.getInstance();
        runtime.push();
        resultFactory = new DefaultResultFactory();
        handler = new IfNodeCorrectTypeEventHandler(runtime, resultFactory);
    }

    @AfterEach
    void tearDown() {
        runtime.pop();
    }

    @Test
    void handle_whenConditionIsBooleanLiteral_shouldReturnCorrectResult() {
        LiteralNode condition = new LiteralNode("true", 1, 1);
        IfKeywordNode node = new IfKeywordNode(condition, List.of(), 1, 1);

        Result<String> result = handler.handle(node);

        assertInstanceOf(CorrectResult.class, result);
        assertTrue(((CorrectResult<String>) result).isCorrect());
        assertEquals("Check passed.", ((CorrectResult<String>) result).result());
    }

    @Test
    void handle_whenConditionIsNotBoolean_shouldReturnIncorrectResult() {
        LiteralNode condition = new LiteralNode("123", 2, 2);
        IfKeywordNode node = new IfKeywordNode(condition, List.of(), 2, 2);

        Result<String> result = handler.handle(node);

        assertInstanceOf(IncorrectResult.class, result);
        assertFalse(((IncorrectResult<String>) result).isCorrect());
    }
}
