/*
 * My Project
 */

package com.ingsis.formatter.handlers;

import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import com.ingsis.utils.token.template.TokenTemplate;
import java.io.Writer;
import java.util.function.Supplier;

public class FormatterFunctionCallHandler implements NodeEventHandler<ExpressionNode> {
    private final Supplier<NodeEventHandler<ExpressionNode>> expressionHandlerSupplier;
    private final Boolean enforceSingleSeparation;
    private final TokenTemplate space;
    private final TokenTemplate newLine;
    private final ResultFactory resultFactory;
    private final Writer writer;

    public FormatterFunctionCallHandler(
            Supplier<NodeEventHandler<ExpressionNode>> expressionHandlerSupplier,
            Boolean enforceSingleSeparation,
            TokenTemplate space,
            TokenTemplate newLine,
            ResultFactory resultFactory,
            Writer writer) {
        this.expressionHandlerSupplier = expressionHandlerSupplier;
        this.resultFactory = resultFactory;
        this.space = space;
        this.newLine = newLine;
        this.enforceSingleSeparation = enforceSingleSeparation;
        this.writer = writer;
    }

    @Override
    public Result<String> handle(ExpressionNode node) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }
}
