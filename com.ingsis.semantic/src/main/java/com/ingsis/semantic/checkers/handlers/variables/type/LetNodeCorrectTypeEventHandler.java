/*
 * My Project
 */

package com.ingsis.semantic.checkers.handlers.variables.type;

import com.ingsis.nodes.keyword.LetKeywordNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.runtime.Runtime;
import com.ingsis.semantic.checkers.handlers.NodeEventHandler;
import com.ingsis.typer.expression.DefaultExpressionTypeGetter;
import com.ingsis.types.Types;

public final class LetNodeCorrectTypeEventHandler implements NodeEventHandler<LetKeywordNode> {
    private final Runtime runtime;

    public LetNodeCorrectTypeEventHandler(Runtime runtime) {
        this.runtime = runtime;
    }

    @Override
    public Result<String> handle(LetKeywordNode node) {
        Types expectedType = node.typeAssignationNode().typeNode().type();
        Types actualType =
                new DefaultExpressionTypeGetter(runtime)
                        .getType(node.valueAssignationNode().expressionNode());
        if (!expectedType.equals(actualType)) {
            return new IncorrectResult<>(
                    "Variable type: " + expectedType + " is not equal to " + actualType);
        }
        return new CorrectResult<>("Variable type: " + expectedType + " is equal to " + actualType);
    }
}
