/*
 * My Project
 */

package com.ingsis.utils.rule.observer.handlers.factories; /*
                                                            * My Project
                                                            */

import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;

public interface HandlerFactory {
    public NodeEventHandler<DeclarationKeywordNode> createDeclarationHandler();

    public NodeEventHandler<IfKeywordNode> createConditionalHandler();

    public NodeEventHandler<ExpressionNode> createExpressionHandler();
}
