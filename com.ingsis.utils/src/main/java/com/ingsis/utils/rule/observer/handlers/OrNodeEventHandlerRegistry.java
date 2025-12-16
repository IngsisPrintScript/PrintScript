/*
 * My Project
 */

package com.ingsis.utils.rule.observer.handlers;

import com.ingsis.utils.evalstate.env.semantic.SemanticEnvironment;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.visitors.CheckResult;
import java.util.ArrayList;
import java.util.List;

public class OrNodeEventHandlerRegistry<T extends Node> implements NodeEventHandlerRegistry<T> {
    private final List<NodeEventHandler<T>> handlers;

    public OrNodeEventHandlerRegistry(List<NodeEventHandler<T>> handlers) {
        this.handlers = List.copyOf(handlers);
    }

    public OrNodeEventHandlerRegistry() {
        this(List.of());
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
    public NodeEventHandlerRegistry<T> register(NodeEventHandler<T> newHandler) {
        List<NodeEventHandler<T>> newHandlers = new ArrayList<>(handlers);
        newHandlers.add(newHandler);
        return new OrNodeEventHandlerRegistry<>(newHandlers);
    }
}
