/*
 * My Project
 */

package com.ingsis.utils.rule.observer.handlers; /*
                                                  * My Project
                                                  */

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import java.util.ArrayList;
import java.util.List;

public class OrInMemoryNodeEventHandlerRegistry<T extends Node>
        implements NodeEventHandlerRegistry<T> {
    private final List<NodeEventHandler<T>> handlers;
    private final ResultFactory resultFactory;

    public OrInMemoryNodeEventHandlerRegistry(
            List<NodeEventHandler<T>> handlers, ResultFactory resultFactory) {
        this.handlers = new ArrayList<>(handlers);
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
