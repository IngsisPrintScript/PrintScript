/*
 * My Project
 */

package com.ingsis.parser.semantic.checkers.handlers.identifier.type;

import com.ingsis.runtime.Runtime;
import com.ingsis.runtime.type.typer.expression.DefaultExpressionTypeGetter;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import com.ingsis.utils.type.types.Types;
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
