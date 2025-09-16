/*
 * My Project
 */

package com.ingsis.printscript.astnodes.expression;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.visitor.InterpretVisitorInterface;
import com.ingsis.printscript.visitor.InterpretableNode;
import com.ingsis.printscript.visitor.SemanticallyCheckable;

public interface ExpressionNode extends Node, SemanticallyCheckable, InterpretableNode {
    Result<Object> evaluate();

    Result<String> prettyPrint();

    @Override
    default Result<String> acceptInterpreter(InterpretVisitorInterface interpreter) {
        return interpreter.interpret(this);
    }
}
