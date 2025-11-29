/*
 * My Project
 */

package com.ingsis.interpreter.visitor.factory;

import com.ingsis.interpreter.visitor.DefaultInterpreterVisitor;
import com.ingsis.interpreter.visitor.expression.strategies.factories.SolutionStrategyFactory;
import com.ingsis.runtime.Runtime;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.result.factory.ResultFactory;

public final class DefaultInterpreterVisitorFactory implements InterpreterVisitorFactory {
    private final SolutionStrategyFactory solutionStrategyFactory;
    private final ResultFactory resultFactory;

    public DefaultInterpreterVisitorFactory(
            SolutionStrategyFactory solutionStrategyFactory, ResultFactory resultFactory) {
        this.solutionStrategyFactory = solutionStrategyFactory;
        this.resultFactory = resultFactory;
    }

    @Override
    public Interpreter createDefaultInterpreter(Runtime runtime) {
        return new DefaultInterpreterVisitor(
                runtime, solutionStrategyFactory.constructDefaultStrategy(), resultFactory);
    }
}
