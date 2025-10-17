/*
 * My Project
 */

package com.ingsis.semantic.checkers.handlers.identifier.existance;

import com.ingsis.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.nodes.keyword.LetKeywordNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.runtime.Runtime;
import com.ingsis.semantic.checkers.handlers.NodeEventHandler;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class LetNodeEventVariableExistenceHandler
        implements NodeEventHandler<LetKeywordNode> {
    private final Runtime runtime;

    public LetNodeEventVariableExistenceHandler(Runtime runtime) {
        this.runtime = runtime;
    }

    @Override
    public Result<String> handle(LetKeywordNode node) {
        TypeAssignationNode typeAssignationNode = node.typeAssignationNode();

        Result<String> checkTypeAssignationNode =
                new TypeAssignationNodeEventVariableExistenceHandler(runtime)
                        .handle(typeAssignationNode);

        if (!checkTypeAssignationNode.isCorrect()) {
            return new IncorrectResult<>(checkTypeAssignationNode);
        }

        ValueAssignationNode valueAssignationNode = node.valueAssignationNode();

        Result<String> checkValueAssignationNode =
                new ValueAssignationNodeEventVariableExistenceHandler(runtime)
                        .handle(valueAssignationNode);

        if (!checkValueAssignationNode.isCorrect()) {
            return new IncorrectResult<>(checkValueAssignationNode);
        }

        return new CorrectResult<>("All variables inside the assignation are correctly used.");
    }
}
