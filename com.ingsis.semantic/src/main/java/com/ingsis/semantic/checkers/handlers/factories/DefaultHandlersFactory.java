/*
 * My Project
 */

package com.ingsis.semantic.checkers.handlers.factories;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.nodes.keyword.LetKeywordNode;
import com.ingsis.runtime.Runtime;
import com.ingsis.semantic.checkers.handlers.NodeEventHandler;
import com.ingsis.semantic.checkers.handlers.operators.OperatorNodeValidityHandler;
import com.ingsis.semantic.checkers.handlers.variables.existance.ExpressionNodeEventVariableExistenceHandler;
import com.ingsis.semantic.checkers.handlers.variables.existance.LetNodeEventVariableExistenceHandler;
import com.ingsis.semantic.checkers.handlers.variables.existance.TypeAssignationNodeEventVariableExistenceHandler;
import com.ingsis.semantic.checkers.handlers.variables.existance.ValueAssignationNodeEventVariableExistenceHandler;
import com.ingsis.semantic.checkers.handlers.variables.type.LetNodeCorrectTypeEventHandler;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class DefaultHandlersFactory implements HandlersFactory {
    private final Runtime runtime;

    public DefaultHandlersFactory(Runtime runtime) {
        this.runtime = runtime;
    }

    @Override
    public NodeEventHandler<LetKeywordNode> createLetVariableExistenceHandler() {
        return new LetNodeEventVariableExistenceHandler(runtime);
    }

    @Override
    public NodeEventHandler<TypeAssignationNode> createTypeAssignationVariableExistenceHandler() {
        return new TypeAssignationNodeEventVariableExistenceHandler(runtime);
    }

    @Override
    public NodeEventHandler<ValueAssignationNode> createValueAssignationVariableExistenceHandler() {
        return new ValueAssignationNodeEventVariableExistenceHandler(runtime);
    }

    @Override
    public NodeEventHandler<ExpressionNode> createExpressionVariableExistenceHandler() {
        return new ExpressionNodeEventVariableExistenceHandler(runtime);
    }

    @Override
    public NodeEventHandler<ExpressionNode> createOperatorValidityHandler() {
        return new OperatorNodeValidityHandler(runtime);
    }

    @Override
    public NodeEventHandler<LetKeywordNode> createLetCorrectTypeHandler() {
        return new LetNodeCorrectTypeEventHandler(runtime);
    }
}
