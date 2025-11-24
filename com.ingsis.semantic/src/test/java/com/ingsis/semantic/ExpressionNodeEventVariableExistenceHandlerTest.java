package com.ingsis.semantic;

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
import com.ingsis.types.Types;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionNodeEventVariableExistenceHandlerTest {

    private Runtime runtime;
    private ResultFactory resultFactory;
    private ExpressionNodeEventVariableExistenceHandler handler;

    @BeforeEach
    void setUp() {
        runtime = DefaultRuntime.getInstance();
        resultFactory = new DefaultResultFactory();
        handler = new ExpressionNodeEventVariableExistenceHandler(runtime, resultFactory);
    }

    @Test
    void handle_whenIdentifierNotInitialized_shouldReturnIncorrectResult() {
        // Declaramos el identifier sin inicializar
        IdentifierNode id = new IdentifierNode("u", 1, 1);
        // No creamos variable en el entorno => simula "no inicializado"

        Result<String> result = handler.handle(id);

        assertInstanceOf(IncorrectResult.class, result);
        assertFalse(((IncorrectResult<String>) result).isCorrect());
    }

    @Test
    void handle_whenChildrenHaveError_shouldReturnFirstError() {
        // Nodo padre con un hijo IdentifierNode no inicializado
        ExpressionNode child = new IdentifierNode("v",2,3);

        ExpressionNode parent = new BinaryOperatorNode("+",child,new IdentifierNode("p",1,2),1,1);

        Result<String> result = handler.handle(parent);

        assertInstanceOf(IncorrectResult.class, result);
        assertFalse(((IncorrectResult<String>) result).isCorrect());
    }

    @Test
    void handle_whenAllOk_shouldReturnCorrectResult() {
        ExpressionNode node = new LiteralNode("a",1,1);
        Result<String> result = handler.handle(node);

        assertInstanceOf(CorrectResult.class, result);
        assertEquals("Check passed.", ((CorrectResult<String>) result).result());
    }
}
