/*
 * My Project
 */

package com.ingsis.utils.rule.observer.publishers; /*
                                                    * My Project
                                                    */

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
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
