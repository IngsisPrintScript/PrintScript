/*
 * My Project
 */

package com.ingsis.utils.type.typer; /*
                                      * My Project
                                      */

import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.type.types.Types;

public interface TypeGetter<T extends ExpressionNode> {
    Types getType(T expressionNode);
}
