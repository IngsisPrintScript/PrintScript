/*
 * My Project
 */

package com.ingsis.rule.observer.publishers;

import com.ingsis.nodes.Node;
import com.ingsis.result.Result;

public interface NodeEventPublisher<T extends Node> {
    Result<String> notify(T node);
}
