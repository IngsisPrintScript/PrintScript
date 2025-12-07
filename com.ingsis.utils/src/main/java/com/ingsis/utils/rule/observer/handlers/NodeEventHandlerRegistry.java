/*
 * My Project
 */

package com.ingsis.utils.rule.observer.handlers;

import com.ingsis.utils.nodes.Node;

public interface NodeEventHandlerRegistry<T extends Node> extends NodeEventHandler<T> {
    public void register(NodeEventHandler<T> newHandler);
}
