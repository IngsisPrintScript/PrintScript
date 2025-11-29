/*
 * My Project
 */

package com.ingsis.utils.rule.observer.publishers; /*
                                                    * My Project
                                                    */

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.result.Result;

public interface NodeEventPublisher<T extends Node> {
    Result<String> notify(T node);
}
