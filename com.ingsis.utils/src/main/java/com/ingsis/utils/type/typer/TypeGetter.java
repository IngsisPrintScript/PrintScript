/*
 * My Project
 */

package com.ingsis.utils.type.typer;

import com.ingsis.utils.evalstate.env.Environment;
import com.ingsis.utils.evalstate.env.semantic.SemanticEnvironment;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.type.types.Types;

public interface TypeGetter<T extends ExpressionNode> {
    Types getType(T expressionNode, Environment env);

    Types getType(T expressionNode, SemanticEnvironment env);
}
