/*
 * My Project
 */

package com.ingsis.utils.rule.observer.handlers; /*
                                                  * My Project
                                                  */

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.result.Result;

public interface NodeEventHandler<T extends Node> {
    Result<String> handle(T node);
}
