/*
 * My Project
 */

package com.ingsis.printscript.astnodes.visitor;

import com.ingsis.printscript.astnodes.expression.binary.BinaryExpression;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.expression.literal.LiteralNode;
import com.ingsis.printscript.astnodes.statements.LetStatementNode;
import com.ingsis.printscript.astnodes.statements.PrintStatementNode;
import com.ingsis.printscript.results.Result;

public interface RuleVisitor {
    Result<String> check(LetStatementNode node);

    Result<String> check(PrintStatementNode node);

    Result<String> check(BinaryExpression node);

    Result<String> check(IdentifierNode node);

    Result<String> check(LiteralNode node);
}
