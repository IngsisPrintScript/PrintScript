/*
 * My Project
 */

package com.ingsis.printscript.astnodes.statements.function.argument;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.astnodes.declaration.TypeNode;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.visitor.VisitorInterface;
import java.util.List;

public final class DeclarationArgumentNode implements Node {
    private final IdentifierNode identifier;
    private final TypeNode type;

    public DeclarationArgumentNode(IdentifierNode identifier, TypeNode type) {
        this.identifier = identifier;
        this.type = type;
    }
    public IdentifierNode identifier() {
        return identifier;
    }

    public TypeNode type() {
        return type;
    }

    @Override
    public List<Node> children() {
        return List.of(identifier, type);
    }

    @Override
    public Boolean isNil() {
        return false;
    }

    @Override
    public Result<String> accept(VisitorInterface visitor) {
        return null;
    }
}
