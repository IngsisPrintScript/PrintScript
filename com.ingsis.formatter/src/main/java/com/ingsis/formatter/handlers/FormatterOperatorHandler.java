/*
 * My Project
 */

package com.ingsis.formatter.handlers;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.operator.OperatorNode;
import com.ingsis.result.Result;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.rule.observer.handlers.NodeEventHandler;
import java.util.function.Supplier;

public class FormatterOperatorHandler implements NodeEventHandler<ExpressionNode> {
    private final ResultFactory resultFactory;
    private final Supplier<NodeEventHandler<ExpressionNode>> leafHandlerSupplier;

    public FormatterOperatorHandler(
            ResultFactory resultFactory,
            Supplier<NodeEventHandler<ExpressionNode>> leafHandlerSupplier) {
        this.resultFactory = resultFactory;
        this.leafHandlerSupplier = leafHandlerSupplier;
    }

    @Override
    public Result<String> handle(ExpressionNode node) {
        StringBuilder sb = new StringBuilder();
        if (!(node instanceof OperatorNode operatorNode)) {
            return resultFactory.createIncorrectResult("Incorrect handler.");
        }
        Result<String> leftFormatResult = leafHandlerSupplier.get().handle(node.children().get(0));
        if (!leftFormatResult.isCorrect()) {
            return resultFactory.cloneIncorrectResult(leftFormatResult);
        }
        sb.append(leftFormatResult.result());
        sb.append(" ");
        sb.append(operatorNode.symbol());
        sb.append(" ");
        Result<String> rightFormatResult = leafHandlerSupplier.get().handle(node.children().get(1));
        if (!rightFormatResult.isCorrect()) {
            return resultFactory.cloneIncorrectResult(rightFormatResult);
        }
        sb.append(rightFormatResult.result());
        return resultFactory.createCorrectResult(sb.toString());
    }
}
