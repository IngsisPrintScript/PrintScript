/*
 * My Project
 */

package com.ingsis.rule.observer.handlers;

import com.ingsis.nodes.Node;
import com.ingsis.result.Result;
import com.ingsis.result.factory.ResultFactory;
import java.util.ArrayList;
import java.util.List;

public class OrInMemoryNodeEventHandlerRegistry<T extends Node>
    implements NodeEventHandlerRegistry<T> {
  private final List<NodeEventHandler<T>> handlers;
  private final ResultFactory resultFactory;

  public OrInMemoryNodeEventHandlerRegistry(
      List<NodeEventHandler<T>> handlers, ResultFactory resultFactory) {
    this.handlers = List.copyOf(handlers);
    this.resultFactory = resultFactory;
  }

  public OrInMemoryNodeEventHandlerRegistry(ResultFactory resultFactory) {
    this(new ArrayList<>(), resultFactory);
  }

  @Override
  public Result<String> handle(T node) {
    for (NodeEventHandler<T> handler : handlers) {
      Result<String> handleResult = handler.handle(node);
      if (handleResult.isCorrect()) {
        return handleResult;
      }
    }
    return resultFactory.createIncorrectResult("No way to handle that.");
  }

  @Override
  public void register(NodeEventHandler<T> newHandler) {
    this.handlers.add(newHandler);
  }
}
