/*
 * My Project
 */

package com.ingsis.interpreter.visitor.expression.strategies.factories;

import com.ingsis.interpreter.visitor.expression.strategies.ExpressionSolutionStrategy;
import com.ingsis.interpreter.visitor.expression.strategies.FinalStrategy;
import com.ingsis.interpreter.visitor.expression.strategies.binary.AdditionSolutionStrategy;

public final class DefaultSolutionStrategyFactory implements SolutionStrategyFactory {
    @Override
    public ExpressionSolutionStrategy constructDefaultStrategy() {
        return new AdditionSolutionStrategy(new FinalStrategy());
    }
}
