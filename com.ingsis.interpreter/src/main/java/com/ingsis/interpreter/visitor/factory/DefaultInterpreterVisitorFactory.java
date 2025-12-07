/*
 * My Project
 */

package com.ingsis.interpreter.visitor.factory;

import com.ingsis.interpreter.visitor.DefaultInterpreterVisitor;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.runtime.Runtime;

public final class DefaultInterpreterVisitorFactory implements InterpreterVisitorFactory {
    private final ResultFactory resultFactory;

    public DefaultInterpreterVisitorFactory(ResultFactory resultFactory) {
        this.resultFactory = resultFactory;
    }

    @Override
    public Interpreter createDefaultInterpreter(Runtime runtime) {
        return new DefaultInterpreterVisitor(runtime, resultFactory);
    }
}
