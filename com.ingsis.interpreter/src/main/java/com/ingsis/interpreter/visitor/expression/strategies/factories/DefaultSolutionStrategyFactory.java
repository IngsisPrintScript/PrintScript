/*
 * My Project
 */

package com.ingsis.interpreter.visitor.expression.strategies.factories;

import com.ingsis.interpreter.visitor.expression.strategies.ExpressionSolutionStrategy;
import com.ingsis.interpreter.visitor.expression.strategies.FinalStrategy;
import com.ingsis.interpreter.visitor.expression.strategies.binary.AdditionSolutionStrategy;
import com.ingsis.interpreter.visitor.expression.strategies.binary.AssignationSolutionStrategy;
import com.ingsis.interpreter.visitor.expression.strategies.binary.DivitionSolutionStrategy;
import com.ingsis.interpreter.visitor.expression.strategies.binary.MultiplicationSolutionStrategy;
import com.ingsis.interpreter.visitor.expression.strategies.binary.SubstractionSolutionStrategy;
import com.ingsis.interpreter.visitor.expression.strategies.function.FunctionCallSolutionStrategy;
import com.ingsis.interpreter.visitor.expression.strategies.function.GlobalFunctionBodySolutionStrategy;
import com.ingsis.interpreter.visitor.expression.strategies.identifier.IdentifierSolutionStrategy;
import com.ingsis.interpreter.visitor.expression.strategies.literal.LiteralSolutionStrategy;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.runtime.Runtime;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings(
        value = "EI2",
        justification = "Runtime is intentionally passed and stored; design needs mutability.")
public final class DefaultSolutionStrategyFactory implements SolutionStrategyFactory {
    private final Runtime RUNTIME;

    public DefaultSolutionStrategyFactory(Runtime runtime) {
        this.RUNTIME = runtime;
    }

    @Override
    public ExpressionSolutionStrategy constructDefaultStrategy() {
        return new AssignationSolutionStrategy(
                RUNTIME,
                new MultiplicationSolutionStrategy(
                        new DivitionSolutionStrategy(
                                new SubstractionSolutionStrategy(
                                        new AdditionSolutionStrategy(
                                                new FunctionCallSolutionStrategy(
                                                        RUNTIME,
                                                        new GlobalFunctionBodySolutionStrategy(
                                                                RUNTIME,
                                                                new LiteralSolutionStrategy(
                                                                        new IdentifierSolutionStrategy(
                                                                                new FinalStrategy(),
                                                                                DefaultRuntime
                                                                                        .getInstance())))))))));
    }
}
