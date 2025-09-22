/*
 * My Project
 */

package com.ingsis.nodes.operator.strategies;

import com.ingsis.nodes.Node;
import com.ingsis.result.Result;
import java.util.List;

public interface OperatorStrategy {
    Result<Object> execute(List<Node> arguments);
}
