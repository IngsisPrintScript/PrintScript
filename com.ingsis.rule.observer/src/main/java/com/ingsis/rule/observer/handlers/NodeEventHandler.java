/*
 * My Project
 */

package com.ingsis.rule.observer.handlers;

import com.ingsis.nodes.Node;
import com.ingsis.result.Result;

public interface NodeEventHandler<T extends Node> {
    Result<String> handle(T node);
}
