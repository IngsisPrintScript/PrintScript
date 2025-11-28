/*
 * My Project
 */

package com.ingsis.nodes.expression.operator.strategies.factories;

import com.ingsis.nodes.expression.operator.strategies.OperatorStrategy;

public interface StrategyFactory {
    OperatorStrategy typeAssignationStrategy();

    OperatorStrategy valueAssignationStrategy();
}
