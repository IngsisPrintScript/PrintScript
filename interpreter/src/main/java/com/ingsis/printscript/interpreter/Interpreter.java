/*
 * My Project
 */

package com.ingsis.printscript.interpreter;

import com.ingsis.printscript.astnodes.visitor.InterpretVisitorInterface;
import com.ingsis.printscript.astnodes.visitor.InterpretableNode;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Interpreter implements InterpreterInterface {
    private final InterpretVisitorInterface interpretVisitor;
    private final Iterator<InterpretableNode> interpretableNodeIterator;

    public Interpreter(
            InterpretVisitorInterface interpretVisitor,
            Iterator<InterpretableNode> interpretableNodeIterator) {
        this.interpretVisitor = interpretVisitor;
        this.interpretableNodeIterator = createDefensiveCopy(interpretableNodeIterator);
    }

    private Iterator<InterpretableNode> createDefensiveCopy(Iterator<InterpretableNode> original) {
        List<InterpretableNode> copyList = new ArrayList<>();
        while (original.hasNext()) {
            copyList.add(original.next());
        }
        return copyList.iterator();
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
