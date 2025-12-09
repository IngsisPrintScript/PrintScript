/*
 * My Project
 */

package com.ingsis.formatter.handlers;

import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import com.ingsis.utils.token.template.TokenTemplate;
import com.ingsis.utils.token.tokenstream.TokenStream;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.function.Supplier;

public class FormatterConditionalHandler implements NodeEventHandler<IfKeywordNode> {
    private final Supplier<Checker> eventsCheckerSupplier;
    private final Boolean enforceSingleSeparation;
    private final TokenTemplate space;
    private final TokenTemplate newLine;
    private final TokenTemplate tab;
    private final ResultFactory resultFactory;
    private final Writer writer;

    public FormatterConditionalHandler(
            Supplier<Checker> eventsCheckerSupplier,
            Boolean enforceSingleSeparation,
            TokenTemplate space,
            TokenTemplate newLine,
            TokenTemplate tab,
            ResultFactory resultFactory,
            Writer writer) {
        this.eventsCheckerSupplier = eventsCheckerSupplier;
        this.enforceSingleSeparation = enforceSingleSeparation;
        this.space = space;
        this.newLine = newLine;
        this.tab = tab;
        this.resultFactory = resultFactory;
        this.writer = writer;
    }

    @Override
    public Result<String> handle(IfKeywordNode node) {
        TokenStream stream = node.stream();
        try {
            writer.append("if");
            stream = (TokenStream) stream.next().nextIterator();
            stream = consumeSpacesWithEnforcement(stream);
            writer.append("(");
            stream = (TokenStream) stream.next().nextIterator();
            stream = consumeSpacesWithEnforcement(stream);
            Result<String> expressionFormatResult = eventsCheckerSupplier.get().check(node.condition());
            stream = stream.advanceBy(node.condition().stream().consumeAll());
            if (!expressionFormatResult.isCorrect()) {
                return resultFactory.cloneIncorrectResult(expressionFormatResult);
            }
            writer.append(")");
            stream = (TokenStream) stream.next().nextIterator();
            stream = consumeSpacesWithEnforcement(stream);
            writer.append("{");
            stream = (TokenStream) stream.next().nextIterator();
            stream = consumeSpacesWithEnforcement(stream);
            for (Node thenChild : node.thenBody()) {
                Result<String> formatChildResult =
                        ((Checkable) thenChild).acceptChecker(eventsCheckerSupplier.get());
                if (!formatChildResult.isCorrect()) {
                    return resultFactory.cloneIncorrectResult(formatChildResult);
                }
                stream=stream.advanceBy(thenChild.stream().consumeAll());
                stream = consumeSpacesWithEnforcement(stream);
            }
            for (Node elseChild : node.elseBody()) {
                Result<String> formatChildResult =
                        ((Checkable) elseChild).acceptChecker(eventsCheckerSupplier.get());
                if (!formatChildResult.isCorrect()) {
                    return resultFactory.cloneIncorrectResult(formatChildResult);
                }
                stream = stream.advanceBy(elseChild.stream().consumeAll());
                stream = consumeSpacesWithEnforcement(stream);
            }
            writer.append("}");
            stream = consumeSpacesWithEnforcement(stream);
            return resultFactory.createCorrectResult("Formatted.");
        } catch (IOException e){
            return  resultFactory.createIncorrectResult(e.getMessage());
        }
    }

    private TokenStream consumeSpacesWithEnforcement(TokenStream stream) throws IOException {
        if (enforceSingleSeparation) {
            writer.append(" ");
            while (stream.consume(space).isCorrect()) {
                stream = (TokenStream) stream.next().nextIterator();
            }
        } else {
            while (stream.consume(space).isCorrect()) {
                writer.append(" ");
                stream = (TokenStream) stream.next().nextIterator();
            }
        }
        while (stream.consume(newLine).isCorrect()){
            writer.append("\n");
            stream = (TokenStream) stream.next().nextIterator();
        }
        while (stream.consume(tab).isCorrect()){
            writer.append("\t");
            stream = (TokenStream) stream.next().nextIterator();
        }
        return stream;
    }
}
