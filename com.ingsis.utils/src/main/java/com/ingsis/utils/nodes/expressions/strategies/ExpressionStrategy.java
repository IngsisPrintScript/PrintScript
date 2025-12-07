/*
 * My Project
 */

package com.ingsis.utils.nodes.expressions.strategies;

import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.result.Result;
import java.util.List;

public interface ExpressionStrategy {
    Result<Object> solve(List<ExpressionNode> arguments);
}
