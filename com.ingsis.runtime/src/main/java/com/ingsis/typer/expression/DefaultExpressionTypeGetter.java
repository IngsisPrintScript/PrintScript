/*
 * My Project
 */

package com.ingsis.typer.expression;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.function.CallFunctionNode;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.runtime.Runtime;
import com.ingsis.typer.TypeGetter;
import com.ingsis.typer.function.DefaultFunctionTypeGetter;
import com.ingsis.typer.identifier.DefaultIdentifierTypeGetter;
import com.ingsis.typer.literal.DefaultLiteralTypeGetter;
import com.ingsis.types.Types;
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
        } else {
            return this.getType(expressionNode.children().get(0));
        }
    }
}
