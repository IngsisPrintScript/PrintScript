/*
 * My Project
 */

package com.ingsis.nodes.operator.strategies;

import com.ingsis.nodes.Node;
import com.ingsis.result.Result;

public interface OperatorStrategy {
    Result<Object> execute(Node... operands);
}
