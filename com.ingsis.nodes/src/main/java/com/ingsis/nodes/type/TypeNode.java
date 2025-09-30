/*
 * My Project
 */

package com.ingsis.nodes.type;

import com.ingsis.nodes.Node;
import com.ingsis.result.Result;
import com.ingsis.types.Types;
import com.ingsis.visitors.Visitor;

public record TypeNode(Types type) implements Node {

    @Override
    public Result<String> acceptVisitor(Visitor visitor) {
        return visitor.visit(this);
    }
}
