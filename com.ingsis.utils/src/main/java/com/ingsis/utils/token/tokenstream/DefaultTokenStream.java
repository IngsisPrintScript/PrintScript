/*
 * My Project
 */

package com.ingsis.utils.token.tokenstream;

import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
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
    private final ResultFactory resultFactory;

    public DefaultTokenStream(
            List<Token> tokens,
            int pointer,
            TokenFactory tokenFactory,
            IterationResultFactory iterationResultFactory,
            ResultFactory resultFactory) {
        this.tokenFactory = tokenFactory;
        this.tokenBuffer = List.copyOf(tokens);
        this.pointer = pointer;
        this.iterationResultFactory = iterationResultFactory;
        this.resultFactory = resultFactory;
    }

    public DefaultTokenStream(
            List<Token> tokens,
            TokenFactory tokenFactory,
            IterationResultFactory iterationResultFactory,
            ResultFactory resultFactory) {
        this(tokens, 0, tokenFactory, iterationResultFactory, resultFactory);
    }

    public DefaultTokenStream(
            TokenFactory tokenFactory,
            IterationResultFactory iterationResultFactory,
            ResultFactory resultFactory) {
        this(new ArrayList<>(), tokenFactory, iterationResultFactory, resultFactory);
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
                        this.iterationResultFactory,
                        this.resultFactory));
    }

    @Override
    public SafeIterationResult<Token> consume(TokenTemplate tokenTemplate) {
        SafeIterationResult<Token> result = next();
        if (!result.isCorrect()) {
            return result;
        }
        if (!tokenTemplate.matches(result.iterationResult())) {
            return iterationResultFactory.createIncorrectResult(
                    String.format(
                            "EXPECTED: %s ACTUAL %s", tokenTemplate, result.iterationResult()));
        }
        return result;
    }

    @Override
    public TokenStream consumeAll(TokenTemplate tokenTemplate) {
        TokenStream current = this;

        while (true) {
            Result<Token> peekResult = current.peek(0);

            if (!peekResult.isCorrect()) {
                return current;
            }

            Token nextToken = peekResult.result();

            if (!tokenTemplate.matches(nextToken)) {
                return current;
            }

            SafeIterationResult<Token> consumed = current.consume(tokenTemplate);

            if (!consumed.isCorrect()) {
                return current;
            }

            current = (TokenStream) consumed.nextIterator();
        }
    }

    @Override
    public Result<Token> peek(int offset) {
        int index = pointer + offset;
        if (index < 0 || index >= tokenBuffer.size()) {
            return resultFactory.createIncorrectResult("Offset points outside of index.");
        }
        return resultFactory.createCorrectResult(tokenBuffer.get(index));
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
                newTokens,
                this.pointer,
                this.tokenFactory,
                this.iterationResultFactory,
                this.resultFactory);
    }

    @Override
    public TokenStream reset() {
        return new DefaultTokenStream(
                this.tokens(),
                0,
                this.tokenFactory,
                this.iterationResultFactory,
                this.resultFactory);
    }

    @Override
    public TokenStream sliceFromPointer() {
        return new DefaultTokenStream(
                this.tokens().subList(pointer, this.tokens().size()),
                0,
                this.tokenFactory,
                this.iterationResultFactory,
                this.resultFactory);
    }

    @Override
    public TokenStream advanceBy(TokenStream subStream) {

        int newPointer = this.pointer + subStream.pointer();

        if (newPointer > tokenBuffer.size()) {
            newPointer = tokenBuffer.size(); // clamp safely
        }

        return new DefaultTokenStream(
                this.tokenBuffer,
                newPointer,
                this.tokenFactory,
                this.iterationResultFactory,
                this.resultFactory);
    }
}
