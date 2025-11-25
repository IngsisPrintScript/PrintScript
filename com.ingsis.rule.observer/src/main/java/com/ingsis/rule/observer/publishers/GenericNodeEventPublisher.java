/*
 * My Project
 */

package com.ingsis.rule.observer.publishers;

import com.ingsis.nodes.Node;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.Result;
import com.ingsis.rule.observer.handlers.NodeEventHandler;
import java.util.List;

public final class GenericNodeEventPublisher<T extends Node> implements NodeEventPublisher<T> {
    private final List<NodeEventHandler<T>> handlers;

    public GenericNodeEventPublisher(List<NodeEventHandler<T>> handlers) {
        this.handlers = List.copyOf(handlers);
    }

    public GenericNodeEventPublisher(NodeEventHandler<T> handler) {
        this.handlers = List.of(handler);
    }

    @Override
    public Result<String> notify(T node) {
        for (NodeEventHandler<T> h : handlers) {
            Result<String> r = h.handle(node);
            if (!r.isCorrect()) {
                return r;
            }
        }
        return new CorrectResult<>("Let node passed the following semantic rules:");
    }
}
