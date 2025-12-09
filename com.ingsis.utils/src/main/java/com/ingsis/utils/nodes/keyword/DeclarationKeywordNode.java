/*
 * My Project
 */

package com.ingsis.utils.nodes.keyword;

import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.expressions.atomic.identifier.IdentifierNode;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.tokenstream.TokenStream;
import com.ingsis.utils.type.types.Types;

public record DeclarationKeywordNode(
        IdentifierNode identifierNode,
        ExpressionNode expressionNode,
        Types declaredType,
        Boolean isMutable,
        TokenStream stream,
        Integer line,
        Integer column)
        implements Node, Interpretable {

    @Override
    public Result<String> acceptChecker(Checker checker) {
        return checker.check(this);
    }

    @Override
    public Result<String> acceptInterpreter(Interpreter interpreter) {
        return interpreter.interpret(this);
    }
}
