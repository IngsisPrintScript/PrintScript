/*
 * My Project
 */

package com.ingsis.formatter.handlers;

import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import java.io.Writer;
import java.util.function.Supplier;

public class FormatterSpecialFunctionCallHandler implements NodeEventHandler<ExpressionNode> {
    private final Integer amountOfLinesBeforeCall;
    private final String functionName;
    private final Boolean singleSpaceSeparation;
    private final Supplier<NodeEventHandler<ExpressionNode>> expressionHandlerSupplier;
    private final FormatterFunctionCallHandler baseFunctionParser;
    private final ResultFactory resultFactory;
    private final Writer writer;

    public FormatterSpecialFunctionCallHandler(
            Integer amountOfLinesBeforeCall,
            String functionName,
            Boolean singleSpaceSeparation,
            Supplier<NodeEventHandler<ExpressionNode>> expressionHandlerSupplier,
            FormatterFunctionCallHandler baseFunctionParser,
            ResultFactory resultFactory,
            Writer writer) {
        Integer temp = amountOfLinesBeforeCall;
        if (temp == null) {
            temp = 0;
        }
        this.amountOfLinesBeforeCall = temp;
        this.functionName = functionName;
        this.singleSpaceSeparation = singleSpaceSeparation;
        this.expressionHandlerSupplier = expressionHandlerSupplier;
        this.baseFunctionParser = baseFunctionParser;
        this.resultFactory = resultFactory;
        this.writer = writer;
    }

    @Override
    public Result<String> handle(ExpressionNode node) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }
}
