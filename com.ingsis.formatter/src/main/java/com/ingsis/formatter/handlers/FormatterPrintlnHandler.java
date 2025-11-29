/*
 * My Project
 */

package com.ingsis.formatter.handlers;

import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.function.CallFunctionNode;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import java.util.function.Supplier;

public class FormatterPrintlnHandler implements NodeEventHandler<ExpressionNode> {
    private final Integer amountOfLinesBeforeCall;
    private final Supplier<NodeEventHandler<ExpressionNode>> expressionHandlerSupplier;
    private final ResultFactory resultFactory;

    public FormatterPrintlnHandler(
            Integer amountOfLinesBeforeCall,
            Supplier<NodeEventHandler<ExpressionNode>> expressionHandlerSupplier,
            ResultFactory resultFactory) {
        this.amountOfLinesBeforeCall = amountOfLinesBeforeCall;
        this.expressionHandlerSupplier = expressionHandlerSupplier;
        this.resultFactory = resultFactory;
    }

    @Override
    public Result<String> handle(ExpressionNode node) {
        if (!(node instanceof CallFunctionNode callFunctionNode)) {
            return resultFactory.createIncorrectResult("Incorrect handler.");
        }
        String functionIdentifier = callFunctionNode.identifierNode().name();
        StringBuilder sb = new StringBuilder();
        if (functionIdentifier.equals("println")) {
            for (int i = 0; i < amountOfLinesBeforeCall; i++) {
                sb.append("\n");
            }
        }
        sb.append(callFunctionNode.symbol());
        sb.append("( ");
        Result<String> parseExpressionResult =
                expressionHandlerSupplier.get().handle(callFunctionNode.children().get(0));
        if (!parseExpressionResult.isCorrect()) {
            return resultFactory.cloneIncorrectResult(parseExpressionResult);
        }
        sb.append(parseExpressionResult.result());
        sb.append(" )");
        sb.append(" ;\n");
        return resultFactory.createCorrectResult(sb.toString());
    }
}
