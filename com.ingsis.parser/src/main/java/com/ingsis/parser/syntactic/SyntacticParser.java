/*
 * My Project
 */

package com.ingsis.parser.syntactic;

import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.parser.syntactic.tokenstream.TokenStream;
import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.token.Token;

public final class SyntacticParser implements SafeIterator<Checkable> {
    private final SafeIterator<Token> tokenIterator;
    private final Parser<Node> parser;
    private final TokenStream tokenStream;
    private final IterationResultFactory iterationResultFactory;

    public SyntacticParser(
            SafeIterator<Token> tokenIterator,
            Parser<Node> parser,
            TokenStream tokenStream,
            IterationResultFactory iterationResultFactory) {
        this.tokenIterator = tokenIterator;
        this.parser = parser;
        this.tokenStream = tokenStream;
        this.iterationResultFactory = iterationResultFactory;
    }

    @Override
    public SafeIterationResult<Checkable> next() {
        return maximalMunchOf(tokenStream, tokenIterator);
    }

    private SafeIterationResult<Checkable> maximalMunchOf(
            TokenStream stream, SafeIterator<Token> iterator) {
        SafeIterationResult<Token> iterationResult = iterator.next();
        ParseCheckpoint checkpoint = new ParseCheckpoint.UNINITIALIZED();
        while (iterationResult.isCorrect()) {
            if (stream.isEmpty()) {
                stream = stream.withToken(iterationResult.iterationResult());
            }
            ParseResult<Node> parseResult = process(stream);
            switch (parseResult) {
                case ParseResult.COMPLETE<Node> C -> {
                    checkpoint =
                            new ParseCheckpoint.INITIALIZED(
                                    C.node(), iterationResult.nextIterator());
                    if (C.isFinal()) {
                        return processCheckpoint(checkpoint, stream);
                    }
                    break;
                }
                case ParseResult.PREFIX<Node> P -> {
                    break;
                }
                case ParseResult.INVALID<Node> I -> {
                    return processCheckpoint(checkpoint, stream);
                }
            }
            iterationResult = iterationResult.nextIterator().next();
            if (!iterationResult.isCorrect()) {
                break;
            }
            stream = stream.withToken(iterationResult.iterationResult());
        }
        return processCheckpoint(checkpoint, stream);
    }

    private SafeIterationResult<Checkable> processCheckpoint(
            ParseCheckpoint checkpoint, TokenStream stream) {
        return switch (checkpoint) {
            case ParseCheckpoint.INITIALIZED I ->
                    iterationResultFactory.createCorrectResult(
                            I.checkable(),
                            new SyntacticParser(
                                    I.tokenIterator(),
                                    this.parser,
                                    this.tokenStream,
                                    this.iterationResultFactory));
            case ParseCheckpoint.UNINITIALIZED U -> {
                if (stream.isEmpty()) {
                    yield iterationResultFactory.createIncorrectResult("EOL");
                } else {
                    yield iterationResultFactory.createIncorrectResult(
                            "Unable to parse token stream");
                }
            }
        };
    }

    private ParseResult<Node> process(TokenStream tokenStream) {
        return parser.parse(tokenStream);
    }
}
