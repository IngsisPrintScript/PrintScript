/*
 * My Project
 */

package com.ingsis.parser.semantic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import com.ingsis.parser.semantic.checkers.handlers.identifier.existance.LetNodeEventVariableExistenceHandler;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.nodes.expression.literal.LiteralNode;
import com.ingsis.utils.nodes.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.utils.nodes.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.type.TypeNode;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.type.types.Types;
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
    void handleWhenTypeAssignationFailShouldReturnClonedIncorrect() {
        DeclarationKeywordNode node =
                new DeclarationKeywordNode(
                        new TypeAssignationNode(
                                new IdentifierNode("x", 1, 1),
                                new TypeNode(Types.STRING, 1, 1),
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
    void handleWhenValueAssignationFailsShouldReturnClonedIncorrect() {
        DeclarationKeywordNode node =
                new DeclarationKeywordNode(
                        new TypeAssignationNode(
                                new IdentifierNode("y", 1, 1),
                                new TypeNode(Types.STRING, 1, 1),
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
    void handleWhenEverythingOkShouldReturnCorrectResult() {
        DeclarationKeywordNode node =
                new DeclarationKeywordNode(
                        new TypeAssignationNode(
                                new IdentifierNode("a", 1, 1),
                                new TypeNode(Types.STRING, 1, 1),
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
