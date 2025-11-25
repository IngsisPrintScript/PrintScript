/*
 * My Project
 */

package com.ingsis.semantic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import com.ingsis.nodes.expression.ExpressionNode;
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
import com.ingsis.semantic.checkers.handlers.identifier.existance.ExpressionNodeEventVariableExistenceHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExpressionNodeEventVariableExistenceHandlerTest {

    private Runtime runtime;
    private ResultFactory resultFactory;
    private ExpressionNodeEventVariableExistenceHandler handler;

    @BeforeEach
    void setUp() {
        runtime = DefaultRuntime.getInstance();
        runtime.push();
        resultFactory = new DefaultResultFactory();
        handler = new ExpressionNodeEventVariableExistenceHandler(runtime, resultFactory);
    }

    @AfterEach
    void tearDown() {
        runtime.pop();
    }

    @Test
    void handleWhenIdentifierNotInitializedShouldReturnIncorrectResult() {
        // Declaramos el identifier sin inicializar
        IdentifierNode id = new IdentifierNode("u", 1, 1);
        // No creamos variable en el entorno => simula "no inicializado"

        Result<String> result = handler.handle(id);

        assertInstanceOf(IncorrectResult.class, result);
        assertFalse(((IncorrectResult<String>) result).isCorrect());
    }

    @Test
    void handleWhenChildrenHaveErrorShouldReturnFirstError() {
        // Nodo padre con un hijo IdentifierNode no inicializado
        ExpressionNode child = new IdentifierNode("v", 2, 3);

        ExpressionNode parent =
                new BinaryOperatorNode("+", child, new IdentifierNode("p", 1, 2), 1, 1);

        Result<String> result = handler.handle(parent);

        assertInstanceOf(IncorrectResult.class, result);
        assertFalse(((IncorrectResult<String>) result).isCorrect());
    }

    @Test
    void handleWhenAllOkShouldReturnCorrectResult() {
        ExpressionNode node = new LiteralNode("a", 1, 1);
        Result<String> result = handler.handle(node);

        assertInstanceOf(CorrectResult.class, result);
        assertEquals("Check passed.", ((CorrectResult<String>) result).result());
    }
}
