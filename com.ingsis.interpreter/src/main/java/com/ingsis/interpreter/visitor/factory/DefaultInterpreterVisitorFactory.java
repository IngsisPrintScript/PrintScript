/*
 * My Project
 */

package com.ingsis.interpreter.visitor.factory;

import com.ingsis.interpreter.visitor.DefaultInterpreterVisitor;
import com.ingsis.interpreter.visitor.expression.strategies.factories.SolutionStrategyFactory;
import com.ingsis.runtime.Runtime;
import com.ingsis.visitors.Interpreter;

public final class DefaultInterpreterVisitorFactory implements InterpreterVisitorFactory {
    private final SolutionStrategyFactory solutionStrategyFactory;

    public DefaultInterpreterVisitorFactory(SolutionStrategyFactory solutionStrategyFactory) {
        this.solutionStrategyFactory = solutionStrategyFactory;
    }

    @Override
    public Interpreter createDefaultInterpreter(Runtime runtime) {
        return new DefaultInterpreterVisitor(
                runtime, solutionStrategyFactory.constructDefaultStrategy());
    }
}
