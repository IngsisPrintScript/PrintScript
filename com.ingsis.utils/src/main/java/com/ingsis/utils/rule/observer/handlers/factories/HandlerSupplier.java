/*
 * My Project
 */

package com.ingsis.utils.rule.observer.handlers.factories;

import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;

public interface HandlerSupplier {
  public NodeEventHandler<Node> getDeclarationHandler();

  public NodeEventHandler<Node> getConditionalHandler();

  public NodeEventHandler<Node> getExpressionHandler();
}
