/*
 * My Project
 */

package com.ingsis.rule.observer.publishers;

import com.ingsis.nodes.Node;
import com.ingsis.result.Result;
import com.ingsis.rule.observer.handlers.NodeEventHandler;

public final class GenericNodeEventPublisher<T extends Node> implements NodeEventPublisher<T> {
    private final NodeEventHandler<T> handler;

    public GenericNodeEventPublisher(NodeEventHandler<T> handler) {
        this.handler = handler;
    }

    @Override
    public Result<String> notify(T node) {
        return handler.handle(node);
    }
}
