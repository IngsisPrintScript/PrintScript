/*
 * My Project
 */

package com.ingsis.interpreter.visitor.expression.strategies.factories;

import com.ingsis.interpreter.visitor.expression.strategies.ExpressionSolutionStrategy;
import com.ingsis.interpreter.visitor.expression.strategies.FinalStrategy;
import com.ingsis.interpreter.visitor.expression.strategies.binary.AdditionSolutionStrategy;
import com.ingsis.interpreter.visitor.expression.strategies.identifier.IdentifierSolutionStrategy;
import com.ingsis.interpreter.visitor.expression.strategies.literal.LiteralSolutionStrategy;
import com.ingsis.runtime.DefaultRuntime;

public final class DefaultSolutionStrategyFactory implements SolutionStrategyFactory {
  @Override
  public ExpressionSolutionStrategy constructDefaultStrategy() {
    return new AdditionSolutionStrategy(
        new LiteralSolutionStrategy(
            new IdentifierSolutionStrategy(
                new FinalStrategy(),
                DefaultRuntime.getInstance())));
  }
}
