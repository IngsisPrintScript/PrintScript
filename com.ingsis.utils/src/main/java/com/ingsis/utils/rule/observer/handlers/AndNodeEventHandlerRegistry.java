/*
 * My Project
 */

package com.ingsis.utils.rule.observer.handlers;

import com.ingsis.utils.evalstate.env.semantic.SemanticEnvironment;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.visitors.CheckResult;
import java.util.ArrayList;
import java.util.List;

public class AndNodeEventHandlerRegistry<T extends Node>
    implements NodeEventHandlerRegistry<T> {
  private final List<NodeEventHandler<T>> handlers;

  public AndNodeEventHandlerRegistry(
      List<NodeEventHandler<T>> handlers) {
    this.handlers = new ArrayList<>(handlers);
  }

  public AndNodeEventHandlerRegistry() {
    this(new ArrayList<>());
  }

  @Override
  public CheckResult handle(T node, SemanticEnvironment env) {
    SemanticEnvironment tempEnv = env;
    for (NodeEventHandler<T> handler : handlers) {
      switch (handler.handle(node, tempEnv)) {
        case CheckResult.CORRECT C:
          tempEnv = C.environment();
          break;
        case CheckResult.INCORRECT I:
          return I;
      }
    }
    return new CheckResult.CORRECT(tempEnv);
  }

  @Override
  public NodeEventHandlerRegistry<T> register(NodeEventHandler<T> newHandler) {
    List<NodeEventHandler<T>> newHandlers = new ArrayList<>(handlers);
    newHandlers.add(newHandler);
    return new OrNodeEventHandlerRegistry<>(newHandlers);
  }
}
