/*
 * My Project
 */

package com.ingsis.sca.observer.handlers;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.result.Result;
import com.ingsis.rule.observer.handlers.NodeEventHandler;

public class DeclarationHandler implements NodeEventHandler<DeclarationKeywordNode> {
    private final NodeEventHandler<IdentifierNode> identifierChecker;
    private final NodeEventHandler<ExpressionNode> expressionChecker;

    public DeclarationHandler(
            NodeEventHandler<IdentifierNode> identifierChecker,
            NodeEventHandler<ExpressionNode> expressionChecker) {
        this.identifierChecker = identifierChecker;
        this.expressionChecker = expressionChecker;
    }

    @Override
    public Result<String> handle(DeclarationKeywordNode node) {
        IdentifierNode identifierNode = node.valueAssignationNode().identifierNode();
        Result<String> identifierCheckResult = identifierChecker.handle(identifierNode);
        if (!identifierCheckResult.isCorrect()) {
            return identifierCheckResult;
        }
        ExpressionNode expressionNode = node.valueAssignationNode().expressionNode();
        return expressionChecker.handle(expressionNode);
    }
}
