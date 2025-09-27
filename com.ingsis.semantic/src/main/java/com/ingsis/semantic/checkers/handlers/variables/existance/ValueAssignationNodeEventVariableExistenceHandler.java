/*
 * My Project
 */

package com.ingsis.semantic.checkers.handlers.variables.existance;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.runtime.Runtime;
import com.ingsis.semantic.checkers.handlers.NodeEventHandler;

public final class ValueAssignationNodeEventVariableExistenceHandler
        implements NodeEventHandler<ValueAssignationNode> {
    private final Runtime runtime;

    public ValueAssignationNodeEventVariableExistenceHandler(Runtime runtime) {
        this.runtime = runtime;
    }

    @Override
    public Result<String> handle(ValueAssignationNode node) {
        IdentifierNode identifierNode = node.identifierNode();

        if (runtime.getCurrentEnvironment().isVariableDeclared(identifierNode.name())) {
            runtime.getCurrentEnvironment()
                    .setExecutionResult(
                            new IncorrectResult<>(
                                    "Variable " + identifierNode.name() + " is already declared."));
        }

        ExpressionNode expressionNode = node.expressionNode();
        Result<String> checkExpressionVariables =
                new ExpressionNodeEventVariableExistenceHandler(runtime).handle(expressionNode);

        if (!checkExpressionVariables.isCorrect()) {
            return new IncorrectResult<>(checkExpressionVariables);
        }

        return new CorrectResult<>(
                "Variable " + identifierNode.name() + " is not declared and variables used are.");
    }
}
