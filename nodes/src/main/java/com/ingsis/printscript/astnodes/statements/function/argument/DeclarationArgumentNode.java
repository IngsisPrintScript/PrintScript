/*
 * My Project
 */

package com.ingsis.printscript.astnodes.statements.function.argument;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.astnodes.declaration.TypeNode;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.visitor.VisitorInterface;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import java.util.List;

public final class DeclarationArgumentNode implements Node {
    private final IdentifierNode identifier;
    private final TypeNode type;

    public DeclarationArgumentNode(IdentifierNode identifier, TypeNode type) {
        this.identifier = identifier;
        this.type = type;
    }

    public boolean hasIdentifier() {
        return !identifier.isNil();
    }

    public boolean hasType() {
        return !type.isNil();
    }

    public Result<IdentifierNode> identifier() {
        if (!hasIdentifier()) {
            return new IncorrectResult<>("Declare argument node has no identifier.");
        }
        return new CorrectResult<>(identifier);
    }

    public Result<TypeNode> type() {
        if (!hasType()) {
            return new IncorrectResult<>("Declare argument node has no type.");
        }
        return new CorrectResult<>(type);
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
