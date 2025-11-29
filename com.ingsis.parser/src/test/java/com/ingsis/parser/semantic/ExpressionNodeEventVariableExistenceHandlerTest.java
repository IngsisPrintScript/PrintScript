/*
 * My Project
 */

package com.ingsis.parser.semantic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import com.ingsis.parser.semantic.checkers.handlers.identifier.existance.ExpressionNodeEventVariableExistenceHandler;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.runtime.Runtime;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.nodes.expression.literal.LiteralNode;
import com.ingsis.utils.nodes.nodes.expression.operator.BinaryOperatorNode;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.result.factory.ResultFactory;
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
