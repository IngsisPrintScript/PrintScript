/*
 * My Project
 */

package com.ingsis.printscript.interpreter;

import com.ingsis.printscript.astnodes.visitor.InterpretVisitor;
import com.ingsis.printscript.astnodes.visitor.InterpretableNode;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import java.util.Iterator;

public record Interpreter(
        InterpretVisitor interpretVisitor, Iterator<InterpretableNode> interpretableNodeIterator)
        implements InterpreterInterface {
    @Override
    public Result<String> interpreter() {
        Result<String> interpretResult = null;
        while (interpretableNodeIterator.hasNext()) {
            InterpretableNode tree = interpretableNodeIterator.next();
            interpretResult = tree.acceptInterpreter(interpretVisitor());
            if (!interpretResult.isSuccessful()) {
                interpretResult = new IncorrectResult<>(interpretResult.errorMessage());
            }
        }
        if (interpretResult == null) {
            return new IncorrectResult<>("That's not a valid PrintScript line.");
        }
        return interpretResult;
    }
}
