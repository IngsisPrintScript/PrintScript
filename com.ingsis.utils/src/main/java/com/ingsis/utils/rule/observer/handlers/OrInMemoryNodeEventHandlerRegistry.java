/*
 * My Project
 */

package com.ingsis.utils.rule.observer.handlers;

import com.ingsis.utils.evalstate.env.semantic.SemanticEnvironment;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.visitors.CheckResult;
import com.ingsis.utils.result.factory.ResultFactory;
import java.util.ArrayList;
import java.util.List;

public class OrInMemoryNodeEventHandlerRegistry<T extends Node>
    implements NodeEventHandlerRegistry<T> {
  private final List<NodeEventHandler<T>> handlers;

  public OrInMemoryNodeEventHandlerRegistry(
      List<NodeEventHandler<T>> handlers, ResultFactory resultFactory) {
    this.handlers = new ArrayList<>(handlers);
  }

  public OrInMemoryNodeEventHandlerRegistry(ResultFactory resultFactory) {
    this(new ArrayList<>(), resultFactory);
  }

  @Override
  public CheckResult handle(T node, SemanticEnvironment env) {
    for (NodeEventHandler<T> handler : handlers) {
      switch (handler.handle(node, env)) {
        case CheckResult.CORRECT C:
          return C;
        default:
          break;
      }
    }
    return new CheckResult.INCORRECT(env, "It did not pass any of the expected checks.");
  }

  @Override
  public void register(NodeEventHandler<T> newHandler) {
    this.handlers.add(newHandler);
  }
}
