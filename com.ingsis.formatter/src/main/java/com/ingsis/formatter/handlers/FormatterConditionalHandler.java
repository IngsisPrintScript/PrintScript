/*
 * My Project
 */

package com.ingsis.formatter.handlers;

import com.ingsis.utils.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import com.ingsis.utils.token.template.TokenTemplate;
import java.io.Writer;
import java.util.function.Supplier;

public class FormatterConditionalHandler implements NodeEventHandler<IfKeywordNode> {
    private final Supplier<Checker> eventsCheckerSupplier;
    private final Boolean enforceSingleSeparation;
    private final Boolean ifBraceSameLine;
    private final Boolean ifNotSameLine;
    private final Integer indentation;
    private final Integer depth;
    private final TokenTemplate space;
    private final TokenTemplate newLine;
    private final TokenTemplate tab;
    private final ResultFactory resultFactory;
    private final Writer writer;

    public FormatterConditionalHandler(
            Supplier<Checker> eventsCheckerSupplier,
            Boolean enforceSingleSeparation,
            Boolean ifBraceSameLine,
            Boolean ifNotBraceSameLine,
            Integer indentation,
            Integer depth,
            TokenTemplate space,
            TokenTemplate newLine,
            TokenTemplate tab,
            ResultFactory resultFactory,
            Writer writer) {
        this.eventsCheckerSupplier = eventsCheckerSupplier;
        this.enforceSingleSeparation = enforceSingleSeparation;
        this.ifBraceSameLine = ifBraceSameLine;
        this.ifNotSameLine = ifNotBraceSameLine;
        this.indentation = indentation;
        this.depth = depth;
        this.space = space;
        this.newLine = newLine;
        this.tab = tab;
        this.resultFactory = resultFactory;
        this.writer = writer;
    }

    public FormatterConditionalHandler(
            Supplier<Checker> eventsCheckerSupplier,
            Boolean enforceSingleSeparation,
            Boolean ifBraceSameLine,
            Boolean ifNotBraceSameLine,
            Integer indentation,
            TokenTemplate space,
            TokenTemplate newLine,
            TokenTemplate tab,
            ResultFactory resultFactory,
            Writer writer) {
        this.eventsCheckerSupplier = eventsCheckerSupplier;
        this.enforceSingleSeparation = enforceSingleSeparation;
        this.ifBraceSameLine = ifBraceSameLine;
        this.ifNotSameLine = ifNotBraceSameLine;
        this.indentation = indentation;
        this.depth = 1;
        this.space = space;
        this.newLine = newLine;
        this.tab = tab;
        this.resultFactory = resultFactory;
        this.writer = writer;
    }

    @Override
    public Result<String> handle(IfKeywordNode node) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }
}
