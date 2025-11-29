/*
 * My Project
 */

package com.ingsis.formatter.handlers;

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import java.util.function.Supplier;

public class FormatterConditionalHandler implements NodeEventHandler<IfKeywordNode> {
    private final Supplier<Checker> eventsCheckerSupplier;
    private final ResultFactory resultFactory;

    public FormatterConditionalHandler(
            Supplier<Checker> eventsCheckerSupplier, ResultFactory resultFactory) {
        this.eventsCheckerSupplier = eventsCheckerSupplier;
        this.resultFactory = resultFactory;
    }

    @Override
    public Result<String> handle(IfKeywordNode node) {
        StringBuilder sb = new StringBuilder();
        sb.append("if(");
        Result<String> expressionFormatResult = eventsCheckerSupplier.get().check(node.condition());
        if (!expressionFormatResult.isCorrect()) {
            return resultFactory.cloneIncorrectResult(expressionFormatResult);
        }
        sb.append(expressionFormatResult.result() + "){\n");
        for (Node thenChild : node.thenBody()) {
            Result<String> formatChildResult =
                    ((Checkable) thenChild).acceptChecker(eventsCheckerSupplier.get());
            if (!formatChildResult.isCorrect()) {
                return resultFactory.cloneIncorrectResult(formatChildResult);
            }
            sb.append("\t" + formatChildResult.result());
        }
        for (Node elseChild : node.elseBody()) {
            Result<String> formatChildResult =
                    ((Checkable) elseChild).acceptChecker(eventsCheckerSupplier.get());
            if (!formatChildResult.isCorrect()) {
                return resultFactory.cloneIncorrectResult(formatChildResult);
            }
            sb.append("\t" + formatChildResult.result());
        }
        sb.append("}\n");
        return resultFactory.createCorrectResult(sb.toString());
    }
}
