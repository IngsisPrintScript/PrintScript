/*
 * My Project
 */

package com.ingsis.semantic.checkers.handlers.identifier.type;

import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.runtime.Runtime;
import com.ingsis.semantic.checkers.handlers.NodeEventHandler;
import com.ingsis.typer.expression.DefaultExpressionTypeGetter;
import com.ingsis.types.Types;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class LetNodeCorrectTypeEventHandler
        implements NodeEventHandler<DeclarationKeywordNode> {
    private final Runtime runtime;

    public LetNodeCorrectTypeEventHandler(Runtime runtime) {
        this.runtime = runtime;
    }

    @Override
    public Result<String> handle(DeclarationKeywordNode node) {
        Types expectedType = node.typeAssignationNode().typeNode().type();
        Types actualType =
                new DefaultExpressionTypeGetter(runtime)
                        .getType(node.valueAssignationNode().expressionNode());
        if (!expectedType.isCompatibleWith(actualType)) {
            return new IncorrectResult<>(
                    "Variable type: " + expectedType + " is not equal to " + actualType);
        }
        return new CorrectResult<>("Variable type: " + expectedType + " is equal to " + actualType);
    }
}
