/*
 * My Project
 */

package com.ingsis.semantic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.result.factory.DefaultResultFactory;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.runtime.Runtime;
import com.ingsis.runtime.environment.Environment;
import com.ingsis.semantic.checkers.handlers.identifier.existance.ValueAssignationNodeEventVariableExistenceHandler;
import com.ingsis.types.Types;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ValueAssignationNodeEventVariableExistenceHandlerTest {

    private Runtime runtime;
    private Environment env;
    private ResultFactory resultFactory;
    private ValueAssignationNodeEventVariableExistenceHandler handler;

    @BeforeEach
    void setUp() {
        runtime = DefaultRuntime.getInstance();
        runtime.push();
        env = runtime.getCurrentEnvironment();
        resultFactory = new DefaultResultFactory();
        handler = new ValueAssignationNodeEventVariableExistenceHandler(runtime, resultFactory);
    }

    @AfterEach
    void tearDown() {
        runtime.pop();
    }

    @Test
    void handleWhenVariableNotDeclaredShouldReturnIncorrectResult() {
        IdentifierNode id = new IdentifierNode("a", 1, 1);
        ValueAssignationNode node = new ValueAssignationNode(id, null, 1, 1);

        Result<String> result = handler.handle(node);

        assertInstanceOf(IncorrectResult.class, result);
        assertFalse(((IncorrectResult<String>) result).isCorrect());
    }

    @Test
    void handleWhenVariableDeclaredShouldReturnCorrectResult() {
        env.createVariable("b", Types.STRING);

        IdentifierNode id = new IdentifierNode("b", 1, 1);
        ValueAssignationNode node =
                new ValueAssignationNode(id, new LiteralNode("Check Passed.", 1, 1), 1, 1);

        Result<String> result = handler.handle(node);

        assertInstanceOf(CorrectResult.class, result);
        assertEquals("Check passed.", ((CorrectResult<String>) result).result());
    }

    @Test
    void handleWhenExpressionHasUninitializedIdentifierShouldReturnIncorrectResult() {
        env.createVariable("b", Types.STRING);

        IdentifierNode exprId = new IdentifierNode("x", 1, 2);

        IdentifierNode id = new IdentifierNode("b", 1, 1);
        ValueAssignationNode node = new ValueAssignationNode(id, exprId, 1, 1);

        Result<String> result = handler.handle(node);

        assertInstanceOf(IncorrectResult.class, result);
        assertFalse(((IncorrectResult<String>) result).isCorrect());
    }
}
