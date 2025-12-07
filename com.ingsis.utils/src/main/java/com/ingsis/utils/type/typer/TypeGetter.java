/*
 * My Project
 */

package com.ingsis.utils.type.typer;

import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.type.types.Types;

public interface TypeGetter<T extends ExpressionNode> {
    Types getType(T expressionNode);
}
