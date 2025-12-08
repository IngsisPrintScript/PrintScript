/*
 * My Project
 */

package com.ingsis.parser.syntactic;

import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.process.checkpoint.ProcessCheckpoint;
import com.ingsis.utils.process.result.ProcessResult;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.factories.DefaultTokensFactory;
import com.ingsis.utils.token.tokenstream.DefaultTokenStream;
import com.ingsis.utils.token.tokenstream.TokenStream;

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
        ProcessCheckpoint<Token, ProcessResult<Node>> checkpoint =
                ProcessCheckpoint.UNINITIALIZED();
        while (true) {
            if (stream.tokens().isEmpty()) {
                SafeIterationResult<Token> iterationResult = iterator.next();
                if (!iterationResult.isCorrect()) {
                    return iterationResultFactory.cloneIncorrectResult(iterationResult);
                }
                stream = stream.withToken(iterationResult.iterationResult());
                iterator = iterationResult.nextIterator();
            }

            ProcessCheckpoint<Token, ProcessResult<Node>> processCheckpoint = process(stream);
            if (processCheckpoint.isUninitialized()) {
                if (checkpoint.isUninitialized()) {
                    return iterationResultFactory.createIncorrectResult("Error parsing");
                }
                return iterationResultFactory.createCorrectResult(
                        checkpoint.result().result(),
                        new SyntacticParser(
                                checkpoint.iterator(),
                                this.parser,
                                new DefaultTokenStream(
                                        new DefaultTokensFactory(),
                                        iterationResultFactory,
                                        new DefaultResultFactory()),
                                this.iterationResultFactory));
            }

            switch (processCheckpoint.result().status()) {
                case COMPLETE -> {
                    checkpoint = processCheckpoint;
                }
                case INVALID -> {
                    if (checkpoint.isUninitialized()) {
                        return iterationResultFactory.createIncorrectResult(
                                "Unable to parse that stream");
                    }
                    return iterationResultFactory.createCorrectResult(
                            checkpoint.result().result(),
                            new SyntacticParser(
                                    checkpoint.iterator(),
                                    this.parser,
                                    new DefaultTokenStream(
                                            new DefaultTokensFactory(),
                                            iterationResultFactory,
                                            new DefaultResultFactory()),
                                    this.iterationResultFactory));
                }
                case PREFIX -> {}
            }
            SafeIterationResult<Token> getNextToken = iterator.next();
            if (!getNextToken.isCorrect()) {
                return iterationResultFactory.createIncorrectResult("TEST");
            }
            stream = stream.withToken(getNextToken.iterationResult());
            iterator = getNextToken.nextIterator();
        }
    }

    private ProcessCheckpoint<Token, ProcessResult<Node>> process(TokenStream tokenStream) {
        return parser.parse(tokenStream);
    }
}
