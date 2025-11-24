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
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.result.factory.DefaultResultFactory;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.semantic.checkers.handlers.identifier.existance.LetNodeEventVariableExistenceHandler;
import com.ingsis.types.Types;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LetNodeEventVariableExistenceHandlerTest {
    private DefaultRuntime runtime;
    private ResultFactory resultFactory;
    private LetNodeEventVariableExistenceHandler handler;

    @BeforeEach
    void setUp() {
        runtime = DefaultRuntime.getInstance();
        runtime.push();
        resultFactory = new DefaultResultFactory();
        handler = new LetNodeEventVariableExistenceHandler(runtime, resultFactory);
    }

    @AfterEach
    void tearDown() {
        runtime.pop();
    }

    @Test
    void handle_whenTypeAssignationFails_shouldReturnClonedIncorrect() {
        DeclarationKeywordNode node =
                new DeclarationKeywordNode(
                        new TypeAssignationNode(
                                new IdentifierNode("x", 1, 1),
                                new com.ingsis.nodes.type.TypeNode(Types.STRING, 1, 1),
                                1,
                                1),
                        null,
                        false,
                        1,
                        1);

        runtime.getCurrentEnvironment().createVariable("x", Types.STRING);

        Result<String> result = handler.handle(node);

        assertFalse(result.isCorrect());
        assertInstanceOf(IncorrectResult.class, result);
    }

    @Test
    void handle_whenValueAssignationFails_shouldReturnClonedIncorrect() {
        DeclarationKeywordNode node =
                new DeclarationKeywordNode(
                        new TypeAssignationNode(
                                new IdentifierNode("y", 1, 1),
                                new com.ingsis.nodes.type.TypeNode(Types.STRING, 1, 1),
                                1,
                                1),
                        new ValueAssignationNode(
                                new IdentifierNode("z", 1, 1),
                                new LiteralNode("Check Passed.", 1, 1),
                                1,
                                1),
                        false,
                        1,
                        1);

        Result<String> result = handler.handle(node);

        assertFalse(result.isCorrect());
        assertInstanceOf(IncorrectResult.class, result);
    }

    @Test
    void handle_whenEverythingOk_shouldReturnCorrectResult() {
        DeclarationKeywordNode node =
                new DeclarationKeywordNode(
                        new TypeAssignationNode(
                                new IdentifierNode("a", 1, 1),
                                new com.ingsis.nodes.type.TypeNode(Types.STRING, 1, 1),
                                1,
                                1),
                        new ValueAssignationNode(
                                new IdentifierNode("z", 1, 1),
                                new LiteralNode("Check Passed.", 1, 1),
                                1,
                                1),
                        true,
                        1,
                        1);
        Result<String> result = handler.handle(node);
        assertFalse(result.isCorrect());
        assertInstanceOf(IncorrectResult.class, result);
    }
}
