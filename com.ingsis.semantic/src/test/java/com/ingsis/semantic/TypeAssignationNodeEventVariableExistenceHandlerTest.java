package com.ingsis.semantic;

import com.ingsis.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.type.TypeNode;
import com.ingsis.result.Result;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.factory.DefaultResultFactory;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.runtime.Runtime;
import com.ingsis.runtime.environment.Environment;
import com.ingsis.semantic.checkers.handlers.identifier.existance.TypeAssignationNodeEventVariableExistenceHandler;
import com.ingsis.types.Types;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeAssignationNodeEventVariableExistenceHandlerTest {

    private Runtime runtime;
    private ResultFactory resultFactory;
    private TypeAssignationNodeEventVariableExistenceHandler handler;
    private Environment env;

    @BeforeEach
    void setUp() {
        runtime = DefaultRuntime.getInstance();
        resultFactory = new DefaultResultFactory();
        env = runtime.getCurrentEnvironment();
        handler = new TypeAssignationNodeEventVariableExistenceHandler(runtime, resultFactory);
    }

    @Test
    void handle_whenIdentifierAlreadyDeclared_shouldReturnIncorrectResult() {
        env.createVariable("x", Types.STRING);

        IdentifierNode id = new IdentifierNode("x", 1, 1);
        TypeNode typeNode = new TypeNode(Types.STRING, 1, 1);
        TypeAssignationNode node = new TypeAssignationNode(id, typeNode,1,1);

        Result<String> result = handler.handle(node);

        assertInstanceOf(IncorrectResult.class, result);
        assertFalse(((IncorrectResult<String>) result).isCorrect());
    }

    @Test
    void handle_whenIdentifierNotDeclared_shouldCreateVariableAndReturnCorrectResult() {
        IdentifierNode id = new IdentifierNode("y", 1, 1);
        TypeNode typeNode = new TypeNode(Types.STRING, 1, 1);
        TypeAssignationNode node = new TypeAssignationNode(id, typeNode,1,1);

        Result<String> result = handler.handle(node);

        assertInstanceOf(CorrectResult.class, result);
        assertTrue(env.isVariableDeclared("y"));
    }
}
