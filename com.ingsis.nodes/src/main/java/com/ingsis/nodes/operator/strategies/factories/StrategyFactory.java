/*
 * My Project
 */

package com.ingsis.nodes.operator.strategies.factories;

import com.ingsis.nodes.operator.strategies.OperatorStrategy;

public interface StrategyFactory {
    OperatorStrategy typeAssignationStrategy();

    OperatorStrategy valueAssignationStrategy();
}
