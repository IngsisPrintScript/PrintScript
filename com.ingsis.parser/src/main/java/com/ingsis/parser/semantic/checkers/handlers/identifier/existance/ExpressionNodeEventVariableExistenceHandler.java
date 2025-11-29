/*
 * My Project
 */

package com.ingsis.parser.semantic.checkers.handlers.identifier.existance;

import com.ingsis.runtime.Runtime;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Collection;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class ExpressionNodeEventVariableExistenceHandler
        implements NodeEventHandler<ExpressionNode> {
    private final Runtime runtime;
    private final ResultFactory resultFactory;

    public ExpressionNodeEventVariableExistenceHandler(
            Runtime runtime, ResultFactory resultFactory) {
        this.runtime = runtime;
        this.resultFactory = resultFactory;
    }

    @Override
    public Result<String> handle(ExpressionNode node) {
        Collection<ExpressionNode> children = node.children();

        if (node instanceof IdentifierNode identifierNode) {
            if (!runtime.getCurrentEnvironment().isIdentifierInitialized(identifierNode.name())) {
                return resultFactory.createIncorrectResult(
                        String.format(
                                "Usage of unitialized identifier: %s on line: %d and column: %d",
                                identifierNode.name(),
                                identifierNode.line(),
                                identifierNode.column()));
            }
            return resultFactory.createCorrectResult("Check passed.");
        }

        for (ExpressionNode child : children) {
            Result<String> result = this.handle(child);
            if (!result.isCorrect()) {
                return result;
            }
        }

        return resultFactory.createCorrectResult("Check passed.");
    }
}
