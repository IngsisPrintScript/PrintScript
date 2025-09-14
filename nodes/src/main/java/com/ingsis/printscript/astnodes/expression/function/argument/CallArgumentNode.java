package com.ingsis.printscript.astnodes.expression.function.argument;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.expression.literal.LiteralNode;
import com.ingsis.printscript.astnodes.visitor.VisitorInterface;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;

import java.util.List;

public final class CallArgumentNode implements Node {
    private final IdentifierNode identifier;
    private final LiteralNode value;

    public CallArgumentNode(IdentifierNode identifier, LiteralNode value) {
        this.identifier = identifier;
        this.value = value;
    }

    public boolean hasIdentifier() {
        return !identifier.isNil();
    }
    public Result<IdentifierNode> identifier() {
        if (!hasIdentifier()) {
            return new IncorrectResult<>("Call Argument node has no identifier");
        }
        return new CorrectResult<>(identifier);
    }

    public boolean hasValue() {
        return !value.isNil();
    }

    public Result<LiteralNode> value() {
        if (!hasValue()) {
            return new IncorrectResult<>("Call Argument node has no value");
        }
        return new CorrectResult<>(value);
    }

    @Override
    public List<Node> children() {
        return List.of(identifier, value);
    }

    @Override
    public Boolean isNil() {
        return false;
    }

    @Override
    public Result<String> accept(VisitorInterface visitor) {
        return visitor.visit(this);
    }
}
