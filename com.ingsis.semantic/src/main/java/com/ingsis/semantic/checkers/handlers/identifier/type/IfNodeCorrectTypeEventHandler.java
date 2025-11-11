/*
 * My Project
 */

package com.ingsis.semantic.checkers.handlers.identifier.type;

import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.result.Result;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.runtime.Runtime;
import com.ingsis.semantic.checkers.handlers.NodeEventHandler;
import com.ingsis.typer.expression.DefaultExpressionTypeGetter;
import com.ingsis.types.Types;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class IfNodeCorrectTypeEventHandler implements NodeEventHandler<IfKeywordNode> {
    private final Runtime runtime;
    private final ResultFactory resultFactory;

    public IfNodeCorrectTypeEventHandler(Runtime runtime, ResultFactory resultFactory) {
        this.runtime = runtime;
        this.resultFactory = resultFactory;
    }

    @Override
    public Result<String> handle(IfKeywordNode node) {
        Types expectedType = Types.BOOLEAN;
        Types actualType = new DefaultExpressionTypeGetter(runtime).getType(node.condition());
        if (!expectedType.equals(actualType)) {
            return resultFactory.createIncorrectResult(
                    String.format(
                            "Condition did not have Boolean type on line:%d and column:%d",
                            node.line(), node.column()));
        }
        return resultFactory.createCorrectResult("Check passed.");
    }
}
