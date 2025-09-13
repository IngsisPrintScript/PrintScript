/*
 * My Project
 */

package com.ingsis.printscript.astnodes.visitor;

import com.ingsis.printscript.astnodes.expression.ExpressionNode;
import com.ingsis.printscript.astnodes.statements.LetStatementNode;
import com.ingsis.printscript.astnodes.statements.PrintStatementNode;
import com.ingsis.printscript.results.Result;

public interface InterpretVisitorInterface {
    Result<String> interpret(LetStatementNode statement);

    Result<String> interpret(PrintStatementNode statement);

    Result<String> interpret(ExpressionNode expression);
}
