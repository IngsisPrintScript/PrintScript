/*
 * My Project
 */

package com.ingsis.utils.nodes.nodes.expression.operator.strategies; /*
                                                                      * My Project
                                                                      */

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.result.Result;
import java.util.List;

public interface OperatorStrategy {
    Result<Object> execute(List<Node> arguments);
}
