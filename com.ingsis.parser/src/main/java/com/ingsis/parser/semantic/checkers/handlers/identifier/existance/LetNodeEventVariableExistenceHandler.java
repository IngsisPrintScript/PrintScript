/*
 * My Project
 */

package com.ingsis.parser.semantic.checkers.handlers.identifier.existance;

import com.ingsis.utils.evalstate.env.semantic.SemanticEnvironment;
import com.ingsis.utils.evalstate.env.semantic.bindings.SemanticBinding;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.expressions.NilExpressionNode;
import com.ingsis.utils.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.visitors.CheckResult;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class LetNodeEventVariableExistenceHandler implements NodeEventHandler<Node> {

    @Override
    public CheckResult handle(Node node, SemanticEnvironment env) {
        if (!(node instanceof DeclarationKeywordNode declarationKeywordNode)) {
            return new CheckResult.CORRECT(env);
        }
        String identifier = declarationKeywordNode.identifierNode().name();
        if (env.lookup(identifier).isPresent()) {
            return new CheckResult.INCORRECT(
                    env,
                    String.format(
                            "Trying to declare already declared variable: \"%s\" on line: %d and"
                                    + " column: %d",
                            identifier, node.line(), node.column()));
        }
        CheckResult expressionCheck =
                new ExpressionNodeEventVariableExistenceHandler()
                        .handle(declarationKeywordNode.expressionNode(), env);
        return switch (expressionCheck) {
            case CheckResult.INCORRECT I -> expressionCheck;
            case CheckResult.CORRECT C -> {
                boolean isInitialized = true;
                if (declarationKeywordNode.expressionNode() instanceof NilExpressionNode) {
                    isInitialized = false;
                }
                yield new CheckResult.CORRECT(
                        C.environment()
                                .define(
                                        identifier,
                                        new SemanticBinding.VariableBinding(
                                                declarationKeywordNode.declaredType(),
                                                declarationKeywordNode.isMutable(),
                                                isInitialized)));
            }
        };
    }
}
