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
import com.ingsis.utils.token.Token;
import java.util.ArrayList;
import java.util.List;

public final class Lexer implements SafeIterator<Token> {
    private final SafeIterator<MetaChar> charIterator;
    private final Tokenizer triviaTokenizer;
    private final Tokenizer tokenizer;
    private final IterationResultFactory iterationResultFactory;

    public Lexer(
            SafeIterator<MetaChar> charIterator,
            Tokenizer triviaTokenizer,
            Tokenizer tokenizer,
            IterationResultFactory iterationResultFactory) {
        this.charIterator = charIterator;
        this.triviaTokenizer = triviaTokenizer;
        this.tokenizer = tokenizer;
        this.iterationResultFactory = iterationResultFactory;
    }

    @Override
    public SafeIterationResult<Token> next() {
        return maximalMunchOf();
    }

    private SafeIterationResult<Token> maximalMunchOf() {
        SafeIterationResult<MetaChar> iterationResult = charIterator.next();
        TokenizeCheckpoint checkpoint = new TokenizeCheckpoint.UNINITIALIZED();
        MetaCharStringBuilder sb = new MetaCharStringBuilder();
        List<Token> trailingTrivia = new ArrayList<>();
        while (iterationResult.isCorrect()) {
            sb = sb.append(iterationResult.iterationResult());
            switch (tokenizeTriviaToken(sb)) {
                case TokenizeResult.COMPLETE C:
                    trailingTrivia.add(C.token());
                    sb = new MetaCharStringBuilder();
                    iterationResult = iterationResult.nextIterator().next();
                    continue;
                default:
                    break;
            }

            switch (tokenizeRealToken(sb, trailingTrivia)) {
                case TokenizeResult.COMPLETE C:
                    checkpoint =
                            new TokenizeCheckpoint.INITIALIZED(
                                    C.token(), iterationResult.nextIterator());
                    trailingTrivia = new ArrayList<>();
                    break;
                case TokenizeResult.INVALID I:
                    return processCheckpoint(checkpoint);
                case TokenizeResult.PREFIX P:
                    break;
            }
            iterationResult = iterationResult.nextIterator().next();
        }
        return processCheckpoint(checkpoint);
    }

    private SafeIterationResult<Token> processCheckpoint(TokenizeCheckpoint checkpoint) {
        return switch (checkpoint) {
            case TokenizeCheckpoint.INITIALIZED I ->
                    iterationResultFactory.createCorrectResult(
                            I.token(),
                            new Lexer(
                                    I.nextIterator(),
                                    this.triviaTokenizer,
                                    this.tokenizer,
                                    this.iterationResultFactory));
            case TokenizeCheckpoint.UNINITIALIZED U ->
                    iterationResultFactory.createIncorrectResult("Error lexing");
        };
    }

    private TokenizeResult tokenizeTriviaToken(MetaCharStringBuilder builder) {
        return triviaTokenizer.tokenize(builder, List.of());
    }

    private TokenizeResult tokenizeRealToken(
            MetaCharStringBuilder builder, List<Token> trailingTrivia) {
        return tokenizer.tokenize(builder, trailingTrivia);
    }
}
