/*
 * My Project
 */

package com.ingsis.utils.type.typer.literal; /*
                                              * My Project
                                              */

import com.ingsis.utils.nodes.nodes.expression.literal.LiteralNode;
import com.ingsis.utils.type.typer.TypeGetter;
import com.ingsis.utils.type.typer.string.DefaultStringTypeGetter;
import com.ingsis.utils.type.types.Types;

public final class DefaultLiteralTypeGetter implements TypeGetter<LiteralNode> {
    @Override
    public Types getType(LiteralNode expressionNode) {
        String value = expressionNode.value();

        return new DefaultStringTypeGetter().getType(value);
    }
}
