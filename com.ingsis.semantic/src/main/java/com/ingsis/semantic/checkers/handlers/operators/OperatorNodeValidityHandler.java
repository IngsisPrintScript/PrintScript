/*
 * My Project
 */

package com.ingsis.semantic.checkers.handlers.operators;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.nodes.expression.operator.OperatorNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.runtime.Runtime;
import com.ingsis.semantic.checkers.handlers.NodeEventHandler;
import com.ingsis.typer.expression.DefaultExpressionTypeGetter;
import com.ingsis.typer.identifier.DefaultIdentifierTypeGetter;
import com.ingsis.typer.literal.DefaultLiteralTypeGetter;
import com.ingsis.types.Types;
import java.util.List;

public final class OperatorNodeValidityHandler implements NodeEventHandler<OperatorNode> {
    private final Runtime runtime;

    public OperatorNodeValidityHandler(Runtime runtime) {
        this.runtime = runtime;
    }

    @Override
    public Result<String> handle(OperatorNode node) {
        if (node.symbol().equals("+")) {
            return new CorrectResult<>(
                    "Since it's an addition it will al be casted to string and then operated.");
        }
        Types expectedType =
                new DefaultExpressionTypeGetter(runtime).getType(node.children().get(0));
        return recursiveCheck(expectedType, node);
    }

    private Result<String> recursiveCheck(Types expectedType, ExpressionNode node) {
        if (node instanceof LiteralNode literalNode) {
            boolean checkResult =
                    new DefaultLiteralTypeGetter(runtime).getType(literalNode).equals(expectedType);
            if (checkResult) {
                return new CorrectResult<>("Literal matches expected type.");
            } else {
                return new IncorrectResult<>("Literal does not match expected type.");
            }
        } else if (node instanceof IdentifierNode identifierNode) {
            boolean checkResult =
                    new DefaultIdentifierTypeGetter(runtime)
                            .getType(identifierNode)
                            .equals(expectedType);
            if (checkResult) {
                return new CorrectResult<>("Identifier matches expected type.");
            } else {
                return new IncorrectResult<>("Identifier does not match expected type.");
            }
        }
        List<ExpressionNode> children = node.children();

        for (ExpressionNode child : children) {
            Result<String> checkTypeResult = recursiveCheck(expectedType, child);
            if (!checkTypeResult.isCorrect()) {
                return new IncorrectResult<>(checkTypeResult);
            }
        }

        return new CorrectResult<>("Types matched.");
    }
}
