/*
 * My Project
 */

package com.ingsis.semantic.checkers.publishers;

import com.ingsis.nodes.Node;
import com.ingsis.result.Result;

public interface NodeEventPublisher<T extends Node> {
    Result<String> notify(T node);
}
