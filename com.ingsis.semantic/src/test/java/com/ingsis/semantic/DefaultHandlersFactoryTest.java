package com.ingsis.semantic;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.result.factory.DefaultResultFactory;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.rule.observer.handlers.NodeEventHandler;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.runtime.Runtime;
import com.ingsis.semantic.checkers.handlers.factories.DefaultHandlersFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DefaultHandlersFactoryTest {

    private Runtime runtime;
    private ResultFactory resultFactory;
    private DefaultHandlersFactory factory;

    @BeforeEach
    public void setUp() {
        runtime = DefaultRuntime.getInstance();
        runtime.push();
        resultFactory = new DefaultResultFactory();
        factory = new DefaultHandlersFactory(runtime, resultFactory);
    }

    @AfterEach
    public void tearDown() {
        runtime.pop();
    }

    @Test
    public void createLetVariableExistenceHandler_shouldReturnHandler() {
        NodeEventHandler<DeclarationKeywordNode> handler =
                factory.createLetVariableExistenceHandler();
        assertNotNull(handler);
    }

    @Test
    public void createTypeAssignationVariableExistenceHandler_shouldReturnHandler() {
        NodeEventHandler<TypeAssignationNode> handler =
                factory.createTypeAssignationVariableExistenceHandler();
        assertNotNull(handler);
    }

    @Test
    public void createValueAssignationVariableExistenceHandler_shouldReturnHandler() {
        NodeEventHandler<ValueAssignationNode> handler =
                factory.createValueAssignationVariableExistenceHandler();
        assertNotNull(handler);
    }

    @Test
    public void createExpressionVariableExistenceHandler_shouldReturnHandler() {
        NodeEventHandler<ExpressionNode> handler =
                factory.createExpressionVariableExistenceHandler();
        assertNotNull(handler);
    }

    @Test
    public void createOperatorValidityHandler_shouldReturnHandler() {
        NodeEventHandler<ExpressionNode> handler =
                factory.createOperatorValidityHandler();
        assertNotNull(handler);
    }

    @Test
    public void createLetCorrectTypeHandler_shouldReturnHandler() {
        NodeEventHandler<DeclarationKeywordNode> handler =
                factory.createLetCorrectTypeHandler();
        assertNotNull(handler);
    }
}
