/*
 * My Project
 */

package com.ingsis.utils.nodes.nodes.expression.operator.strategies.factories; /*
                                                                                * My Project
                                                                                */

import com.ingsis.utils.nodes.nodes.expression.operator.strategies.OperatorStrategy;

public interface StrategyFactory {
    OperatorStrategy typeAssignationStrategy();

    OperatorStrategy valueAssignationStrategy();
}
