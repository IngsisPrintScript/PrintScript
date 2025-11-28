/*
 * My Project
 */

package com.ingsis.typer;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.types.Types;

public interface TypeGetter<T extends ExpressionNode> {
    Types getType(T expressionNode);
}
