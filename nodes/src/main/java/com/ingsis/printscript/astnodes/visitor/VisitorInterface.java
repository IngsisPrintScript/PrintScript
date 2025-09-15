/*
 * My Project
 */

package com.ingsis.printscript.astnodes.visitor;

import com.ingsis.printscript.astnodes.NilNode;
import com.ingsis.printscript.astnodes.declaration.AscriptionNode;
import com.ingsis.printscript.astnodes.declaration.TypeNode;
import com.ingsis.printscript.astnodes.expression.binary.AdditionNode;
import com.ingsis.printscript.astnodes.expression.binary.AssignationNode;
import com.ingsis.printscript.astnodes.expression.function.CallFunctionNode;
import com.ingsis.printscript.astnodes.expression.function.argument.CallArgumentNode;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.expression.literal.LiteralNode;
import com.ingsis.printscript.astnodes.statements.LetStatementNode;
import com.ingsis.printscript.astnodes.statements.PrintStatementNode;
import com.ingsis.printscript.astnodes.statements.function.DeclareFunctionNode;
import com.ingsis.printscript.results.Result;

public interface VisitorInterface {
    Result<String> visit(LetStatementNode node);

    Result<String> visit(PrintStatementNode node);

    Result<String> visit(AscriptionNode node);

    Result<String> visit(AdditionNode node);

    Result<String> visit(AssignationNode node);

    Result<String> visit(LiteralNode node);

    Result<String> visit(IdentifierNode node);

    Result<String> visit(TypeNode node);

    Result<String> visit(CallArgumentNode node);

    Result<String> visit(DeclareFunctionNode node);

    Result<String> visit(CallFunctionNode node);

    Result<String> visit(NilNode node);
}
