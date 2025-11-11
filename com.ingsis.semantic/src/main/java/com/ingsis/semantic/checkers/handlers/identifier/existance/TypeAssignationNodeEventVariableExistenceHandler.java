/*
 * My Project
 */

package com.ingsis.semantic.checkers.handlers.identifier.existance;

import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.nodes.type.TypeNode;
import com.ingsis.result.Result;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.runtime.Runtime;
import com.ingsis.semantic.checkers.handlers.NodeEventHandler;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class TypeAssignationNodeEventVariableExistenceHandler
        implements NodeEventHandler<TypeAssignationNode> {
    private final Runtime runtime;
    private final ResultFactory resultFactory;

    public TypeAssignationNodeEventVariableExistenceHandler(
            Runtime runtime, ResultFactory resultFactory) {
        this.runtime = runtime;
        this.resultFactory = resultFactory;
    }

    @Override
    public Result<String> handle(TypeAssignationNode node) {
        IdentifierNode identifierNode = node.identifierNode();
        TypeNode typeNode = node.typeNode();

        if (runtime.getCurrentEnvironment().isVariableDeclared(identifierNode.name())) {
            return resultFactory.createIncorrectResult(
                    String.format(
                            "Redeclaration identifier: %s, on line: %d and column:%d",
                            identifierNode.name(), identifierNode.line(), identifierNode.column()));
        }

        runtime.getCurrentEnvironment().createVariable(identifierNode.name(), typeNode.type());

        return resultFactory.createCorrectResult("Check passed.");
    }
}
