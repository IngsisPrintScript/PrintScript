/*
 * My Project
 */

package com.ingsis.semantic.checkers.handlers;

import com.ingsis.nodes.Node;
import com.ingsis.result.Result;

public interface NodeEventHandler<T extends Node> {
    Result<String> handle(T node);
}
