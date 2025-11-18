/*
 * My Project
 */

package com.ingsis.sca.observer.handlers.factories;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.rule.observer.handlers.NodeEventHandler;

public interface HandlerFactory {
    public NodeEventHandler<DeclarationKeywordNode> createDeclarationHandler();

    public NodeEventHandler<IfKeywordNode> createConditionalHandler();

    public NodeEventHandler<ExpressionNode> createExpressionHandler();
}
