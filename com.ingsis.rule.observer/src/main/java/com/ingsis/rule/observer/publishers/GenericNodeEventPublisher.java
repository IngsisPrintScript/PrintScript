/*
 * My Project
 */

package com.ingsis.rule.observer.publishers;

import com.ingsis.nodes.Node;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.Result;
import com.ingsis.rule.observer.handlers.NodeEventHandler;
import java.util.Collection;
import java.util.List;

public final class GenericNodeEventPublisher<T extends Node> implements NodeEventPublisher<T> {
    private final Collection<NodeEventHandler<T>> listeners;

    public GenericNodeEventPublisher(Collection<NodeEventHandler<T>> listeners) {
        this.listeners = List.copyOf(listeners);
    }

    @Override
    public Result<String> notify(T node) {
        for (NodeEventHandler<T> listener : listeners) {
            Result<String> result = listener.handle(node);
            if (!result.isCorrect()) {
                return result;
            }
        }
        return new CorrectResult<>("Let node passed the following semantic rules: " + listeners);
    }
}
