/*
 * My Project
 */

package com.ingsis.formatter.handlers;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.function.CallFunctionNode;
import com.ingsis.result.Result;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.rule.observer.handlers.NodeEventHandler;

public class FormatterPrintlnHandler implements NodeEventHandler<ExpressionNode> {
    private final Integer amountOfLinesBeforeCall;
    private final NodeEventHandler<ExpressionNode> expressionHandler;
    private final ResultFactory resultFactory;

    public FormatterPrintlnHandler(
            Integer amountOfLinesBeforeCall,
            NodeEventHandler<ExpressionNode> expressionHandler,
            ResultFactory resultFactory) {
        this.amountOfLinesBeforeCall = amountOfLinesBeforeCall;
        this.expressionHandler = expressionHandler;
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
                expressionHandler.handle(callFunctionNode.children().get(0));
        if (!parseExpressionResult.isCorrect()) {
            return resultFactory.cloneIncorrectResult(parseExpressionResult);
        }
        sb.append(parseExpressionResult.result());
        sb.append(" )");
        sb.append(" ;\n");
        return resultFactory.createCorrectResult(sb.toString());
    }
}
