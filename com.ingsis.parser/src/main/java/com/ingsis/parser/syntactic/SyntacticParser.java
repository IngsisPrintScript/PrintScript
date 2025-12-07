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
import com.ingsis.utils.token.Token;
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
        SafeIterationResult<Token> iterateResult = tokenIterator.next();
        if (!iterateResult.isCorrect()) {
            return iterationResultFactory.cloneIncorrectResult(iterateResult);
        }

        SafeIterator<Token> currentIterator = iterateResult.nextIterator();
        TokenStream currentStream = tokenStream.withToken(iterateResult.iterationResult());
        ProcessCheckpoint<Token, Checkable> checkpoint = ProcessCheckpoint.UNINITIALIZED();

        return obtainMaximalMunch(currentStream, currentIterator, checkpoint);
    }

    private SafeIterationResult<Checkable> obtainMaximalMunch(
            TokenStream currentStream,
            SafeIterator<Token> currentIterator,
            ProcessCheckpoint<Token, Checkable> checkpoint) {

        while (true) {
            ProcessCheckpoint<Token, ProcessResult<Node>> result = process(currentStream);
            if (result.isInitialized()) {
                switch (result.result().status()) {
                    case COMPLETE ->
                            checkpoint =
                                    ProcessCheckpoint.INITIALIZED(
                                            currentIterator, (Checkable) result.result().result());
                    case INVALID -> {
                        return manageInvalidCheckpoint(checkpoint, currentStream);
                    }
                    case PREFIX -> {}
                }
            }

            SafeIterationResult<Token> nextTokenResult = currentIterator.next();

            if (!nextTokenResult.isCorrect()) {
                return handleEndOfStream(checkpoint, nextTokenResult);
            }

            currentStream = currentStream.withToken(nextTokenResult.iterationResult());
            currentIterator = nextTokenResult.nextIterator();
        }
    }

    private SafeIterationResult<Checkable> handleEndOfStream(
            ProcessCheckpoint<Token, Checkable> checkpoint,
            SafeIterationResult<Token> nextTokenResult) {

        if (checkpoint.isUninitialized()) {
            return iterationResultFactory.cloneIncorrectResult(nextTokenResult);
        } else {
            return iterationResultFactory.createCorrectResult(
                    checkpoint.result(),
                    new SyntacticParser(
                            checkpoint.iterator(),
                            parser,
                            this.tokenStream,
                            iterationResultFactory));
        }
    }

    private SafeIterationResult<Checkable> manageInvalidCheckpoint(
            ProcessCheckpoint<Token, Checkable> checkpoint, TokenStream currentStream) {
        if (checkpoint.isUninitialized()) {
            return iterationResultFactory.createIncorrectResult(
                    "Cannot build a node with token stream: " + currentStream.tokens());
        } else {
            return iterationResultFactory.createCorrectResult(
                    checkpoint.result(),
                    new SyntacticParser(
                            checkpoint.iterator(),
                            parser,
                            this.tokenStream,
                            iterationResultFactory));
        }
    }

    private ProcessCheckpoint<Token, ProcessResult<Node>> process(TokenStream tokenStream) {
        return parser.parse(tokenStream);
    }
}
