/*
 * My Project
 */

package com.ingsis.printscript.interpreter;

import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.visitor.InterpretVisitorInterface;
import com.ingsis.printscript.visitor.InterpretableNode;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Iterator;

public class Interpreter implements InterpreterInterface {
    private final InterpretVisitorInterface interpretVisitor;
    private final Iterator<InterpretableNode> interpretableNodeIterator;

    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public Interpreter(
            InterpretVisitorInterface interpretVisitor,
            Iterator<InterpretableNode> interpretableNodeIterator) {
        this.interpretVisitor = interpretVisitor;
        this.interpretableNodeIterator = interpretableNodeIterator;
    }

    @Override
    public Result<String> interpreter() {
        Result<String> interpretResult = null;
        while (interpretableNodeIterator.hasNext()) {
            InterpretableNode tree = interpretableNodeIterator.next();
            interpretResult = tree.acceptInterpreter(interpretVisitor);
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
