/*
 * My Project
 */

package com.ingsis.rule.observer.handlers;

import com.ingsis.nodes.Node;

public interface NodeEventHandlerRegistry<T extends Node> extends NodeEventHandler<T> {
    public void register(NodeEventHandler<T> newHandler);
}
