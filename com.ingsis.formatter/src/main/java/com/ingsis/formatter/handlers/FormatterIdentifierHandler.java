/*
 * My Project
 */

package com.ingsis.formatter.handlers;

import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import java.io.Writer;

public class FormatterIdentifierHandler implements NodeEventHandler<ExpressionNode> {
    private final ResultFactory resultFactory;
    private final Writer writer;

    public FormatterIdentifierHandler(ResultFactory resultFactory, Writer writer) {
        this.resultFactory = resultFactory;
        this.writer = writer;
    }

    @Override
    public Result<String> handle(ExpressionNode node) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }
}
