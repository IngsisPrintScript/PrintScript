/*
 * My Project
 */

package com.ingsis.semantic;

import static org.junit.jupiter.api.Assertions.*;

import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.result.factory.DefaultResultFactory;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.runtime.Runtime;
import com.ingsis.semantic.checkers.handlers.identifier.type.LetNodeCorrectTypeEventHandler;
import com.ingsis.types.Types;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LetNodeCorrectTypeEventHandlerTest {
    private Runtime runtime;
    private ResultFactory resultFactory;
    private LetNodeCorrectTypeEventHandler handler;

    @BeforeEach
    void setUp() {
        runtime = DefaultRuntime.getInstance();
        runtime.push();
        resultFactory = new DefaultResultFactory();
        handler = new LetNodeCorrectTypeEventHandler(runtime, resultFactory);
    }

    @AfterEach
    void tearDown() {
        runtime.pop();
    }

    @Test
    void handle_whenTypesMatch_shouldReturnCorrectResult() {
        DeclarationKeywordNode node =
                new DeclarationKeywordNode(
                        new TypeAssignationNode(
                                new IdentifierNode("a", 1, 1),
                                new com.ingsis.nodes.type.TypeNode(Types.STRING, 1, 1),
                                1,
                                1),
                        new ValueAssignationNode(
                                new IdentifierNode("a", 1, 1),
                                new LiteralNode("Hello", 1, 1),
                                1,
                                1),
                        true,
                        1,
                        1);

        Result<String> result = handler.handle(node);

        assertTrue(result.isCorrect());
        assertInstanceOf(CorrectResult.class, result);
    }

    @Test
    void handle_whenTypesMismatch_shouldReturnIncorrectResult() {
        DeclarationKeywordNode node =
                new DeclarationKeywordNode(
                        new TypeAssignationNode(
                                new IdentifierNode("b", 1, 1),
                                new com.ingsis.nodes.type.TypeNode(Types.NUMBER, 1, 1),
                                1,
                                1),
                        new ValueAssignationNode(
                                new IdentifierNode("b", 1, 1), new LiteralNode("true", 1, 1), 1, 1),
                        true,
                        1,
                        1);

        Result<String> result = handler.handle(node);

        assertFalse(result.isCorrect());
        assertInstanceOf(IncorrectResult.class, result);
    }
}
