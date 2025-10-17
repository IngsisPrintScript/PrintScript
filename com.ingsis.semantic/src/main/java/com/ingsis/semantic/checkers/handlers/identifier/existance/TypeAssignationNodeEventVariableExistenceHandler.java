/*
 * My Project
 */

package com.ingsis.semantic.checkers.handlers.identifier.existance;

import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.nodes.type.TypeNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.runtime.Runtime;
import com.ingsis.semantic.checkers.handlers.NodeEventHandler;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class TypeAssignationNodeEventVariableExistenceHandler
        implements NodeEventHandler<TypeAssignationNode> {
    private final Runtime runtime;

    public TypeAssignationNodeEventVariableExistenceHandler(Runtime runtime) {
        this.runtime = runtime;
    }

    @Override
    public Result<String> handle(TypeAssignationNode node) {
        IdentifierNode identifierNode = node.identifierNode();
        TypeNode typeNode = node.typeNode();

        if (runtime.getCurrentEnvironment().isVariableDeclared(identifierNode.name())) {
            runtime.getCurrentEnvironment()
                    .setExecutionResult(
                            new IncorrectResult<>(
                                    "Variable " + identifierNode.name() + " is not declared."));
        }

        runtime.getCurrentEnvironment().createVariable(identifierNode.name(), typeNode.type());

        return new CorrectResult<>("Variable " + identifierNode.name() + " is already declared.");
    }
}
