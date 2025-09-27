/*
 * My Project
 */

package com.ingsis.typer.literal;

import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.runtime.Runtime;
import com.ingsis.typer.TypeGetter;
import com.ingsis.typer.string.DefaultStringTypeGetter;
import com.ingsis.types.Types;

public final class DefaultLiteralTypeGetter implements TypeGetter<LiteralNode> {
    private final Runtime runtime;

    public DefaultLiteralTypeGetter(Runtime runtime) {
        this.runtime = runtime;
    }

    @Override
    public Types getType(LiteralNode expressionNode) {
        String value = expressionNode.value();

        return new DefaultStringTypeGetter().getType(value);
    }
}
