/*
 * My Project
 */

package com.ingsis.utils.rule.observer.handlers.factories;

import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;

public interface HandlerFactory {
  public NodeEventHandler<DeclarationKeywordNode> createDeclarationHandler();

  public NodeEventHandler<IfKeywordNode> createConditionalHandler();

  public NodeEventHandler<ExpressionNode> createExpressionHandler();
}
