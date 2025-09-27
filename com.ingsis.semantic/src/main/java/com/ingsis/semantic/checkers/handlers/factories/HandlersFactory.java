/*
 * My Project
 */

package com.ingsis.semantic.checkers.handlers.factories;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.nodes.keyword.LetKeywordNode;
import com.ingsis.semantic.checkers.handlers.NodeEventHandler;

public interface HandlersFactory {
    NodeEventHandler<LetKeywordNode> createLetVariableExistenceHandler();

    NodeEventHandler<TypeAssignationNode> createTypeAssignationVariableExistenceHandler();

    NodeEventHandler<ValueAssignationNode> createValueAssignationVariableExistenceHandler();

    NodeEventHandler<ExpressionNode> createExpressionVariableExistenceHandler();

    NodeEventHandler<LetKeywordNode> createLetCorrectTypeHandler();
}
