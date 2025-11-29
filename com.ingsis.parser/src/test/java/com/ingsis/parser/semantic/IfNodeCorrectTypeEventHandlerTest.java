/*
 * My Project
 */

package com.ingsis.parser.semantic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.parser.semantic.checkers.handlers.identifier.type.IfNodeCorrectTypeEventHandler;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.runtime.Runtime;
import com.ingsis.utils.nodes.nodes.expression.literal.LiteralNode;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.result.factory.ResultFactory;
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
    void handleWhenConditionIsBooleanLiteralShouldReturnCorrectResult() {
        LiteralNode condition = new LiteralNode("true", 1, 1);
        IfKeywordNode node = new IfKeywordNode(condition, List.of(), 1, 1);

        Result<String> result = handler.handle(node);

        assertInstanceOf(CorrectResult.class, result);
        assertTrue(((CorrectResult<String>) result).isCorrect());
        assertEquals("Check passed.", ((CorrectResult<String>) result).result());
    }

    @Test
    void handleWhenConditionIsNotBooleanShouldReturnIncorrectResult() {
        LiteralNode condition = new LiteralNode("123", 2, 2);
        IfKeywordNode node = new IfKeywordNode(condition, List.of(), 2, 2);

        Result<String> result = handler.handle(node);

        assertInstanceOf(IncorrectResult.class, result);
        assertFalse(((IncorrectResult<String>) result).isCorrect());
    }
}
