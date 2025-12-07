/*
 * My Project
 */

package com.ingsis.utils.rule.observer.handlers;

import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.result.factory.ResultFactory;
import java.util.List;

public class InMemoryNodeEventHandlerRegistry<T extends Node>
    extends AndInMemoryNodeEventHandlerRegistry<T> {

  public InMemoryNodeEventHandlerRegistry(
      List<NodeEventHandler<T>> handlers, ResultFactory resultFactory) {
    super(handlers, resultFactory);
  }

  public InMemoryNodeEventHandlerRegistry(ResultFactory resultFactory) {
    super(resultFactory);
  }
}
