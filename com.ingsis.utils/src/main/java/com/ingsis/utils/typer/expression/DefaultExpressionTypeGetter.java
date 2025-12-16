/*
 * My Project
 */

package com.ingsis.utils.typer.expression;

import com.ingsis.utils.evalstate.env.Environment;
import com.ingsis.utils.evalstate.env.semantic.SemanticEnvironment;
import com.ingsis.utils.nodes.expressions.*;
import com.ingsis.utils.type.typer.TypeGetter;
import com.ingsis.utils.type.typer.literal.DefaultLiteralTypeGetter;
import com.ingsis.utils.type.types.Types;
import com.ingsis.utils.typer.function.DefaultFunctionTypeGetter;
import com.ingsis.utils.typer.identifier.DefaultIdentifierTypeGetter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class DefaultExpressionTypeGetter implements TypeGetter<ExpressionNode> {

    public Types getType(ExpressionNode expressionNode, Environment env) {
        if (expressionNode instanceof IdentifierNode identifierNode) {
            return new DefaultIdentifierTypeGetter().getType(identifierNode, env);
        } else if (expressionNode instanceof LiteralNode literalNode) {
            return new DefaultLiteralTypeGetter().getType(literalNode, env);
        } else if (expressionNode instanceof CallFunctionNode callFunctionNode) {
            return new DefaultFunctionTypeGetter().getType(callFunctionNode, env);
        } else if (expressionNode instanceof NilExpressionNode) {
            return  Types.NIL;
        } else {
            return this.getType(expressionNode.children().get(0), env);
        }
    }

    @Override
    public Types getType(ExpressionNode expressionNode, SemanticEnvironment env) {
        if (expressionNode instanceof IdentifierNode identifierNode) {
            return new DefaultIdentifierTypeGetter().getType(identifierNode, env);
        } else if (expressionNode instanceof LiteralNode literalNode) {
            return new DefaultLiteralTypeGetter().getType(literalNode, env);
        } else if (expressionNode instanceof CallFunctionNode callFunctionNode) {
            return new DefaultFunctionTypeGetter().getType(callFunctionNode, env);
        } else if(expressionNode instanceof  NilExpressionNode) {
            return  Types.NIL;
        } else {
            return this.getType(expressionNode.children().get(0), env);
        }
    }
}
