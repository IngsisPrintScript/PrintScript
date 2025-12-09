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
        TokenStream stream = node.stream();
        try {
            writer.append("if");
            stream = (TokenStream) stream.next().nextIterator();
            stream = consumeSpacesWithEnforcement(stream);
            writer.append("(");
            stream = (TokenStream) stream.next().nextIterator();
            stream = consumeSpacesWithEnforcement(stream);
            Result<String> expressionFormatResult =
                    eventsCheckerSupplier.get().check(node.condition());
            stream = stream.advanceBy(node.condition().stream().consumeAll());
            if (!expressionFormatResult.isCorrect()) {
                return resultFactory.cloneIncorrectResult(expressionFormatResult);
            }
            writer.append(")");
            stream = (TokenStream) stream.next().nextIterator();
            if (ifNotSameLine) {
                writer.append("\n");
                stream = consumeBlankCharsWithoutWriting(stream);
            } else {
                writer.append(" ");
                stream = consumeBlankCharsWithoutWriting(stream);
            }
            writer.append("{\n");
            stream = (TokenStream) stream.next().nextIterator();
            stream = consumeNewLineWithoutWriting(stream);
            for (Node thenChild : node.thenBody()) {
                if (thenChild instanceof IfKeywordNode ifKeywordNode) {
                    if (indentation == null) {
                        stream = consumeSpacesWithWriting(stream);
                    } else {
                        for (int i = 0; i < indentation; i++) {
                            writer.append(" ");
                            stream = consumeSpaceWithoutWriting(stream);
                        }
                    }
                    new FormatterConditionalHandler(
                                    eventsCheckerSupplier,
                                    enforceSingleSeparation,
                                    ifBraceSameLine,
                                    ifNotSameLine,
                                    indentation * (depth + 1),
                                    depth + 1,
                                    space,
                                    newLine,
                                    tab,
                                    resultFactory,
                                    writer)
                            .handle(ifKeywordNode);
                    stream = stream.advanceBy(ifKeywordNode.stream().consumeAll());
                    continue;
                } else {
                    if (indentation == null) {
                        stream = consumeSpacesWithWriting(stream);
                    } else {
                        for (int i = 0; i < indentation; i++) {
                            writer.append(" ");
                            stream = consumeSpaceWithoutWriting(stream);
                        }
                    }
                    Result<String> formatChildResult =
                            ((Checkable) thenChild).acceptChecker(eventsCheckerSupplier.get());
                    if (!formatChildResult.isCorrect()) {
                        return resultFactory.cloneIncorrectResult(formatChildResult);
                    }
                    stream = stream.advanceBy(thenChild.stream().consumeAll());
                    stream = consumeSpacesWithEnforcement(stream);
                }
            }
            for (Node elseChild : node.elseBody()) {
                if (indentation == null) {
                    stream = consumeSpacesWithWriting(stream);
                } else {
                    for (int i = 0; i < indentation; i++) {
                        writer.append(" ");
                        stream = consumeSpaceWithoutWriting(stream);
                    }
                }
                Result<String> formatChildResult =
                        ((Checkable) elseChild).acceptChecker(eventsCheckerSupplier.get());
                if (!formatChildResult.isCorrect()) {
                    return resultFactory.cloneIncorrectResult(formatChildResult);
                }
                stream = stream.advanceBy(elseChild.stream().consumeAll());
                stream = consumeSpacesWithEnforcement(stream);
            }
            if (indentation == null) {
                stream = consumeSpacesWithWriting(stream);
            } else {
                if (depth != 1) {
                    for (int i = 0; i < indentation / 2; i++) {
                        writer.append(" ");
                        stream = consumeSpaceWithoutWriting(stream);
                    }
                }
            }
            writer.append("}\n");
            stream = consumeSpacesWithEnforcement(stream);
            return resultFactory.createCorrectResult("Formatted.");
        } catch (IOException e) {
            return resultFactory.createIncorrectResult(e.getMessage());
        }
    }

    private TokenStream consumeNewLineWithWriting(TokenStream stream) throws IOException {
        while (stream.consume(newLine).isCorrect()) {
            writer.append("\n");
            stream = (TokenStream) stream.next().nextIterator();
        }
        return stream;
    }

    private TokenStream consumeNewLineWithoutWriting(TokenStream stream) {
        while (stream.consume(newLine).isCorrect()
                || stream.consume(newLine).isCorrect()
                || stream.consume(tab).isCorrect()) {
            stream = (TokenStream) stream.next().nextIterator();
        }
        return stream;
    }

    private TokenStream consumeSpaceWithoutWriting(TokenStream stream) {
        while (stream.consume(space).isCorrect()) {
            stream = (TokenStream) stream.next().nextIterator();
        }
        return stream;
    }

    private TokenStream consumeBlankCharsWithoutWriting(TokenStream stream) {
        while (stream.consume(space).isCorrect()
                || stream.consume(newLine).isCorrect()
                || stream.consume(tab).isCorrect()) {
            stream = (TokenStream) stream.next().nextIterator();
        }
        return stream;
    }

    private TokenStream consumeSpacesWithWriting(TokenStream stream) throws IOException {
        while (stream.consume(space).isCorrect()) {
            writer.append(" ");
            stream = (TokenStream) stream.next().nextIterator();
        }
        return stream;
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
        return stream;
    }
}
