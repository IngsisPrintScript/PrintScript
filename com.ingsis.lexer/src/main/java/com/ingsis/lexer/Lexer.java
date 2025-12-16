/*
 * My Project
 */

package com.ingsis.lexer;

import com.ingsis.lexer.tokenizers.Tokenizer;
import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.metachar.MetaChar;
import com.ingsis.utils.metachar.string.builder.MetaCharStringBuilder;
import com.ingsis.utils.token.DefaultToken;
import com.ingsis.utils.token.Token;
import java.util.ArrayList;
import java.util.List;

public final class Lexer implements SafeIterator<Token> {
    private final SafeIterator<MetaChar> charIterator;
    private final Tokenizer triviaTokenizer;
    private final Tokenizer tokenizer;
    private final IterationResultFactory iterationResultFactory;
    private final List<Token> leadingTrivia;

    public Lexer(
            SafeIterator<MetaChar> charIterator,
            Tokenizer triviaTokenizer,
            Tokenizer tokenizer,
            IterationResultFactory iterationResultFactory) {
        this.charIterator = charIterator;
        this.triviaTokenizer = triviaTokenizer;
        this.tokenizer = tokenizer;
        this.iterationResultFactory = iterationResultFactory;
        this.leadingTrivia = List.of();
    }

    private Lexer(
            SafeIterator<MetaChar> charIterator,
            Tokenizer triviaTokenizer,
            Tokenizer tokenizer,
            IterationResultFactory iterationResultFactory,
            List<Token> leadingTrivia) {
        this.charIterator = charIterator;
        this.triviaTokenizer = triviaTokenizer;
        this.tokenizer = tokenizer;
        this.iterationResultFactory = iterationResultFactory;
        this.leadingTrivia = leadingTrivia;
    }

    @Override
    public SafeIterationResult<Token> next() {
        return maximalMunchOf();
    }

    private SafeIterationResult<Token> maximalMunchOf() {
        SafeIterationResult<MetaChar> iterationResult = charIterator.next();
        TokenizeCheckpoint checkpoint = new TokenizeCheckpoint.UNINITIALIZED();
        MetaCharStringBuilder sb = new MetaCharStringBuilder();
        while (iterationResult.isCorrect()) {
            sb = sb.append(iterationResult.iterationResult());
            switch (tokenizeRealToken(sb, leadingTrivia)) {
                case TokenizeResult.COMPLETE C:
                    checkpoint =
                            new TokenizeCheckpoint.INITIALIZED(
                                    C.token(), iterationResult.nextIterator());
                    break;
                case TokenizeResult.INVALID I:
                    return processCheckpoint(checkpoint, false);
                case TokenizeResult.PREFIX P:
                    break;
            }
            iterationResult = iterationResult.nextIterator().next();
        }
        if (!iterationResult.isCorrect() && checkpoint.equals(new TokenizeCheckpoint.UNINITIALIZED())){
            return iterationResultFactory.cloneIncorrectResult(iterationResult);
        }
        return processCheckpoint(checkpoint, false);
    }

    private SafeIterationResult<Token> processCheckpoint(TokenizeCheckpoint checkpoint, boolean triviaProcessed) {
        return switch (checkpoint) {
            case TokenizeCheckpoint.UNINITIALIZED U ->
                    iterationResultFactory.createIncorrectResult("Error lexing");
            case TokenizeCheckpoint.INITIALIZED I -> {
                if (!triviaProcessed){
                    yield  processTrailingTrivia(I);
                }
                yield iterationResultFactory.createCorrectResult(
                        I.token(),
                        new Lexer(
                                I.nextIterator(),
                                this.triviaTokenizer,
                                this.tokenizer,
                                this.iterationResultFactory,
                                I.token().trailingTrivia()));
            }
        };
    }

    private SafeIterationResult<Token> processTrailingTrivia(TokenizeCheckpoint.INITIALIZED checkpoint){
        SafeIterationResult<MetaChar> iterationResult = checkpoint.nextIterator().next();
        MetaCharStringBuilder sb = new MetaCharStringBuilder();
        List<Token> trailingTrivia = new ArrayList<>();
        boolean isTrivia = true;
        while (iterationResult.isCorrect() && isTrivia){
            sb = sb.append(iterationResult.iterationResult());
            switch (tokenizeTriviaToken(sb)){
                case TokenizeResult.COMPLETE C -> {
                    trailingTrivia.add(C.token());
                    checkpoint = new TokenizeCheckpoint.INITIALIZED(
                            new DefaultToken(
                                    checkpoint.token().type(),
                                    checkpoint.token().value(),
                                    checkpoint.token().leadingTrivia(),
                                    trailingTrivia,
                                    checkpoint.token().line(),
                                    checkpoint.token().column()
                            ),
                            iterationResult.nextIterator()
                    );
                    sb = new MetaCharStringBuilder();
                }
                default -> {
                    isTrivia = false;
                }
            }
            iterationResult = iterationResult.nextIterator().next();
        }
        return processCheckpoint(checkpoint, true);
    }

    private TokenizeResult tokenizeTriviaToken(MetaCharStringBuilder builder) {
        return triviaTokenizer.tokenize(builder, List.of());
    }

    private TokenizeResult tokenizeRealToken(
            MetaCharStringBuilder builder, List<Token> trailingTrivia) {
        return tokenizer.tokenize(builder, trailingTrivia);
    }
}
