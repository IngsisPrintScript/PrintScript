/*
 * My Project
 */

package com.ingsis.parser.syntactic.tokenstream;

/*
 * My Project
 */

import com.ingsis.parser.syntactic.ParseResult;
import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.parser.syntactic.tokenstream.results.ConsumeResult;
import com.ingsis.parser.syntactic.tokenstream.results.ConsumeSequenceResult;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.factories.TokenFactory;
import com.ingsis.utils.token.template.TokenTemplate;
import java.util.ArrayList;
import java.util.List;

public final class DefaultTokenStream implements TokenStream {
    private final List<Token> tokenBuffer;
    private final TokenFactory tokenFactory;
    private final int pointer;
    private final IterationResultFactory iterationResultFactory;

    public DefaultTokenStream(
            List<Token> tokens,
            int pointer,
            TokenFactory tokenFactory,
            IterationResultFactory iterationResultFactory) {
        this.tokenFactory = tokenFactory;
        this.tokenBuffer = List.copyOf(tokens);
        this.pointer = pointer;
        this.iterationResultFactory = iterationResultFactory;
    }

    public DefaultTokenStream(
            TokenFactory tokenFactory, IterationResultFactory iterationResultFactory) {
        this(List.of(), 0, tokenFactory, iterationResultFactory);
    }

    @Override
    public SafeIterationResult<Token> next() {
        if (pointer >= tokenBuffer.size()) {
            return iterationResultFactory.createIncorrectResult("EOL");
        }
        return iterationResultFactory.createCorrectResult(
                tokenBuffer.get(pointer),
                new DefaultTokenStream(
                        this.tokenBuffer,
                        pointer + 1,
                        this.tokenFactory,
                        this.iterationResultFactory));
    }

    @Override
    public ConsumeResult consume() {
        SafeIterationResult<Token> result = next();
        if (!result.isCorrect()) {
            return new ConsumeResult.INCORRECT(result.error());
        }
        return new ConsumeResult.CORRECT(
                result.iterationResult(), (TokenStream) result.nextIterator());
    }

    @Override
    public ConsumeResult consume(TokenTemplate tokenTemplate) {
        SafeIterationResult<Token> result = next();
        if (!result.isCorrect()) {
            return new ConsumeResult.INCORRECT(result.error());
        }
        if (!tokenTemplate.matches(result.iterationResult())) {
            return new ConsumeResult.INCORRECT("Unexpected token.");
        }
        return new ConsumeResult.CORRECT(
                result.iterationResult(), (TokenStream) result.nextIterator());
    }

    @Override
    public <T extends Node> ConsumeSequenceResult<T> consumeSequenceWithNoSeparator(
            TokenTemplate initialToken, TokenTemplate finalToken, Parser<T> elementParser) {
        return switch (this.consume(initialToken)) {
            case ConsumeResult.CORRECT C ->
                    consumeElementsWithNoSeparator(
                            elementParser, finalToken, C.finalTokenStream(), new ArrayList<>());
            default ->
                    new ConsumeSequenceResult.INCORRECT<>(
                            "Sequence did not started with proper token");
        };
    }

    @Override
    public <T extends Node> ConsumeSequenceResult<T> consumeFullSequence(
            TokenTemplate initialToken,
            TokenTemplate separator,
            TokenTemplate finalToken,
            Parser<T> elementParser) {
        return switch (this.consume(initialToken)) {
            case ConsumeResult.CORRECT C ->
                    consumeElements(
                            separator,
                            finalToken,
                            elementParser,
                            C.finalTokenStream(),
                            new ArrayList<>());
            default ->
                    new ConsumeSequenceResult.INCORRECT<>(
                            "Sequence did not started with proper token");
        };
    }

    @Override
    public <T extends Node> ConsumeSequenceResult<T> consumeSequenceWithNoInitialToken(
            TokenTemplate separator, TokenTemplate finalToken, Parser<T> elementParser) {
        return consumeElements(separator, finalToken, elementParser, this, new ArrayList<>());
    }

    @Override
    public <T extends Node> ConsumeSequenceResult<T> consumeElementsWithSeparator(
            TokenTemplate separator, Parser<T> elementParser) {
        return consumeElements(separator, elementParser, this, new ArrayList<>());
    }

    private <T extends Node> ConsumeSequenceResult<T> consumeElements(
            TokenTemplate separator, Parser<T> elementParser, TokenStream stream, List<T> nodes) {
        return switch (elementParser.parse(stream)) {
            case ParseResult.COMPLETE<T> C -> {
                nodes.add(C.node());
                yield switch (C.finalStream().consume(separator)) {
                    case ConsumeResult.CORRECT RC ->
                            consumeElements(separator, elementParser, RC.finalTokenStream(), nodes);
                    default -> new ConsumeSequenceResult.CORRECT<>(nodes, stream);
                };
            }
            default ->
                    new ConsumeSequenceResult.INCORRECT<>(
                            "Error parsing an element of the sequence.");
        };
    }

    private <T extends Node> ConsumeSequenceResult<T> consumeElementsWithNoSeparator(
            Parser<T> elementParser, TokenTemplate finalToken, TokenStream stream, List<T> nodes) {
        return switch (elementParser.parse(stream)) {
            case ParseResult.COMPLETE<T> C -> {
                nodes.add(C.node());

                yield consumeElementsWithNoSeparator(
                        elementParser, finalToken, C.finalStream(), nodes);
            }

            default -> consumeSequenceFinalToken(finalToken, stream, nodes);
        };
    }

    private <T extends Node> ConsumeSequenceResult<T> consumeElements(
            TokenTemplate separator,
            TokenTemplate finalToken,
            Parser<T> elementParser,
            TokenStream stream,
            List<T> nodes) {
        return switch (elementParser.parse(stream)) {
            case ParseResult.COMPLETE<T> C -> {
                nodes.add(C.node());
                yield switch (C.finalStream().consume(separator)) {
                    case ConsumeResult.CORRECT RC ->
                            consumeElements(
                                    separator,
                                    finalToken,
                                    elementParser,
                                    RC.finalTokenStream(),
                                    nodes);
                    default -> consumeSequenceFinalToken(finalToken, C.finalStream(), nodes);
                };
            }
            default ->
                    new ConsumeSequenceResult.INCORRECT<>(
                            "Error parsing an element of the sequence.");
        };
    }

    private <T extends Node> ConsumeSequenceResult<T> consumeSequenceFinalToken(
            TokenTemplate finalToken, TokenStream stream, List<T> nodes) {
        return switch (stream.consume(finalToken)) {
            case ConsumeResult.CORRECT C ->
                    new ConsumeSequenceResult.CORRECT<>(nodes, C.finalTokenStream());
            default ->
                    new ConsumeSequenceResult.INCORRECT<>(
                            "Sequence did not ended with proper token.");
        };
    }

    @Override
    public TokenStream consumeAll(TokenTemplate tokenTemplate) {
        TokenStream current = this;

        while (true) {
            Result<Token> peekResult = current.peek(0);
            if (!peekResult.isCorrect()) {
                return current;
            }

            switch (current.consume(tokenTemplate)) {
                case ConsumeResult.CORRECT C -> current = C.finalTokenStream();
                case ConsumeResult.INCORRECT I -> {
                    return current;
                }
            }
        }
    }

    @Override
    public Result<Token> peek(int offset) {
        int index = pointer + offset;
        if (index < 0 || index >= tokenBuffer.size()) {
            return new IncorrectResult<>("Offset points outside of index.");
        }
        return new CorrectResult<Token>(tokenBuffer.get(index));
    }

    @Override
    public List<Token> tokens() {
        return List.copyOf(this.tokenBuffer);
    }

    @Override
    public Integer pointer() {
        return this.pointer;
    }

    @Override
    public TokenStream withToken(Token token) {
        List<Token> newTokens = new ArrayList<>(this.tokens());
        newTokens.add(token);
        return new DefaultTokenStream(
                newTokens, this.pointer, this.tokenFactory, this.iterationResultFactory);
    }

    @Override
    public TokenStream reset() {
        return new DefaultTokenStream(
                new ArrayList<>(), 0, this.tokenFactory, this.iterationResultFactory);
    }

    @Override
    public TokenStream sliceFromPointer() {
        return new DefaultTokenStream(
                this.tokens().subList(pointer, this.tokens().size()),
                0,
                this.tokenFactory,
                this.iterationResultFactory);
    }

    @Override
    public TokenStream advanceBy(TokenStream subStream) {

        int newPointer = this.pointer + subStream.pointer();

        if (newPointer > tokenBuffer.size()) {
            newPointer = tokenBuffer.size(); // clamp safely
        }

        return new DefaultTokenStream(
                this.tokenBuffer, newPointer, this.tokenFactory, this.iterationResultFactory);
    }

    @Override
    public TokenStream consumeAll() {
        return new DefaultTokenStream(
                this.tokenBuffer,
                this.tokens().size(),
                this.tokenFactory,
                this.iterationResultFactory);
    }

    @Override
    public Boolean isEmpty() {
        return tokens().isEmpty() || pointer == tokens().size();
    }
}
