/*
 * My Project
 */

package com.ingsis.parser.semantic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.parser.semantic.checkers.handlers.identifier.type.LetNodeCorrectTypeEventHandler;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.runtime.Runtime;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.nodes.expression.literal.LiteralNode;
import com.ingsis.utils.nodes.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.utils.nodes.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.type.TypeNode;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.type.types.Types;
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
    void handleWhenTypesMatchShouldReturnCorrectResult() {
        DeclarationKeywordNode node =
                new DeclarationKeywordNode(
                        new TypeAssignationNode(
                                new IdentifierNode("a", 1, 1),
                                new TypeNode(Types.STRING, 1, 1),
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
    void handleWhenTypesMismatchShouldReturnIncorrectResult() {
        DeclarationKeywordNode node =
                new DeclarationKeywordNode(
                        new TypeAssignationNode(
                                new IdentifierNode("b", 1, 1),
                                new TypeNode(Types.NUMBER, 1, 1),
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
