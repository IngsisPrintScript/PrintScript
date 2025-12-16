/*
 * My Project
 */

package com.ingsis.sca.observer.handlers;

import com.ingsis.utils.evalstate.env.semantic.SemanticEnvironment;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.expressions.IdentifierNode;
import com.ingsis.utils.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.visitors.CheckResult;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;

public class DeclarationHandler implements NodeEventHandler<Node> {
    private final NodeEventHandler<Node> identifierChecker;
    private final NodeEventHandler<Node> expressionChecker;

    public DeclarationHandler(
            NodeEventHandler<Node> identifierChecker, NodeEventHandler<Node> expressionChecker) {
        this.identifierChecker = identifierChecker;
        this.expressionChecker = expressionChecker;
    }

    @Override
    public CheckResult handle(Node node, SemanticEnvironment env) {
        if (!(node instanceof DeclarationKeywordNode declarationKeywordNode)) {
            return new CheckResult.CORRECT(env);
        }
        IdentifierNode identifierNode = declarationKeywordNode.identifierNode();
        return switch (identifierChecker.handle(identifierNode, env)) {
            case CheckResult.INCORRECT I -> I;
            case CheckResult.CORRECT C ->
                    expressionChecker.handle(
                            declarationKeywordNode.expressionNode(), C.environment());
        };
    }
}
