/*
 * My Project
 */

package com.ingsis.printscript.astnodes.declaration;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.visitor.VisitorInterface;
import java.util.List;

public class TypeNode implements Node {
    private final String type;

    public TypeNode(String type) {
        this.type = type;
    }

    public String type() {
        return this.type;
    }

    @Override
    public Result accept(VisitorInterface visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<Node> children() {
        return List.of();
    }

    @Override
    public Boolean isNil() {
        return false;
    }
}
