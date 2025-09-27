/*
 * My Project
 */

package com.ingsis.interpreter.visitor;

import com.ingsis.result.Result;
import com.ingsis.visitors.Interpretable;
import com.ingsis.visitors.Interpreter;

public class DefaultInterpreterVisitor implements Interpretable {
    @Override
    public Result<String> acceptInterpreter(Interpreter interpreter) {
        return null;
    }
}
