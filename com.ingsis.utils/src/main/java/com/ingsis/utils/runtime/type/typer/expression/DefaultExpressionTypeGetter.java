/*
 * My Project
 */

package com.ingsis.utils.runtime.type.typer.expression;

import com.ingsis.utils.nodes.expressions.CallFunctionNode;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.expressions.IdentifierNode;
import com.ingsis.utils.nodes.expressions.LiteralNode;
import com.ingsis.utils.nodes.expressions.NilExpressionNode;
import com.ingsis.utils.runtime.Runtime;
import com.ingsis.utils.runtime.type.typer.function.DefaultFunctionTypeGetter;
import com.ingsis.utils.runtime.type.typer.identifier.DefaultIdentifierTypeGetter;
import com.ingsis.utils.type.typer.TypeGetter;
import com.ingsis.utils.type.typer.literal.DefaultLiteralTypeGetter;
import com.ingsis.utils.type.types.Types;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class DefaultExpressionTypeGetter implements TypeGetter<ExpressionNode> {
    private final Runtime runtime;

    public DefaultExpressionTypeGetter(Runtime runtime) {
        this.runtime = runtime;
    }

    public Types getType(ExpressionNode expressionNode) {
        if (expressionNode instanceof IdentifierNode identifierNode) {
            return new DefaultIdentifierTypeGetter(runtime).getType(identifierNode);
        } else if (expressionNode instanceof LiteralNode literalNode) {
            return new DefaultLiteralTypeGetter().getType(literalNode);
        } else if (expressionNode instanceof CallFunctionNode callFunctionNode) {
            return new DefaultFunctionTypeGetter(runtime).getType(callFunctionNode);
        } else if (expressionNode instanceof NilExpressionNode) {
            return Types.NIL;
        } else {
            return this.getType(expressionNode.children().get(0));
        }
    }
}
