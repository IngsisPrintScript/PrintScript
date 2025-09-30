/*
 * My Project
 */

package com.ingsis.interpreter.visitor.expression.strategies.factories;

import com.ingsis.interpreter.visitor.expression.strategies.ExpressionSolutionStrategy;

public interface SolutionStrategyFactory {
    ExpressionSolutionStrategy constructDefaultStrategy();
}
