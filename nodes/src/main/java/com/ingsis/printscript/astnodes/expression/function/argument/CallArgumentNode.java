/*
 * My Project
 */

package com.ingsis.printscript.astnodes.expression.function.argument;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.expression.literal.LiteralNode;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.visitor.VisitorInterface;
import java.util.List;

public final class CallArgumentNode implements Node {
    private final IdentifierNode identifier;
    private final LiteralNode value;

    public CallArgumentNode(IdentifierNode identifier, LiteralNode value) {
        this.identifier = identifier;
        this.value = value;
    }

    public IdentifierNode identifier() {
        return identifier;
    }

    public LiteralNode value() {
        return value;
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
