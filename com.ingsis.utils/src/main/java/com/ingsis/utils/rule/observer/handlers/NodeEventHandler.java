/*
 * My Project
 */

package com.ingsis.utils.rule.observer.handlers;

import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.result.Result;

public interface NodeEventHandler<T extends Node> {
    Result<String> handle(T node);
}
