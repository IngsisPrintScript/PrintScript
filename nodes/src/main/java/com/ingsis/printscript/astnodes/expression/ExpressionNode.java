/*
 * My Project
 */

package com.ingsis.printscript.astnodes.expression;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.astnodes.visitor.InterpretVisitor;
import com.ingsis.printscript.astnodes.visitor.InterpretableNode;
import com.ingsis.printscript.astnodes.visitor.SemanticallyCheckable;
import com.ingsis.printscript.results.Result;

public interface ExpressionNode extends Node, SemanticallyCheckable, InterpretableNode {
    Result<Object> evaluate();

    Result<String> prettyPrint();

    @Override
    default Result<String> acceptInterpreter(InterpretVisitor interpreter) {
        return interpreter.interpret(this);
    }
}
