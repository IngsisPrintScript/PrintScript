/*
 * My Project
 */

package com.ingsis.utils.nodes.nodes.type; /*
                                            * My Project
                                            */

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.nodes.visitors.Visitor;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.type.types.Types;

public record TypeNode(Types type, Integer line, Integer column) implements Node {

    @Override
    public Result<String> acceptVisitor(Visitor visitor) {
        return visitor.visit(this);
    }
}
