/*
 * My Project
 */

package com.ingsis.semantic.checkers.handlers.identifier.type;

import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.result.Result;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.runtime.Runtime;
import com.ingsis.semantic.checkers.handlers.NodeEventHandler;
import com.ingsis.typer.expression.DefaultExpressionTypeGetter;
import com.ingsis.types.Types;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class LetNodeCorrectTypeEventHandler
        implements NodeEventHandler<DeclarationKeywordNode> {
    private final Runtime runtime;
    private final ResultFactory resultFactory;

    public LetNodeCorrectTypeEventHandler(Runtime runtime, ResultFactory resultFactory) {
        this.runtime = runtime;
        this.resultFactory = resultFactory;
    }

    @Override
    public Result<String> handle(DeclarationKeywordNode node) {
        Types expectedType = node.typeAssignationNode().typeNode().type();
        Types actualType =
                new DefaultExpressionTypeGetter(runtime)
                        .getType(node.valueAssignationNode().expressionNode());
        if (!expectedType.isCompatibleWith(actualType)) {
            return resultFactory.createIncorrectResult(
                    String.format(
                            "Unexepected type for identifier value on line:%d and column:%d",
                            node.line(), node.column()));
        }
        return resultFactory.createCorrectResult("Check passed.");
    }
}
