/*
 * My Project
 */

package com.ingsis.utils.rule.observer.handlers; /*
                                                  * My Project
                                                  */

import com.ingsis.utils.nodes.nodes.Node;

public interface NodeEventHandlerRegistry<T extends Node> extends NodeEventHandler<T> {
    public void register(NodeEventHandler<T> newHandler);
}
