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
import com.ingsis.utils.process.checkpoint.ProcessCheckpoint;
import com.ingsis.utils.process.result.ProcessResult;
import com.ingsis.utils.token.Token;

public final class Lexer implements SafeIterator<Token> {
    private final SafeIterator<MetaChar> charIterator;
    private final Tokenizer tokenizer;
    private final IterationResultFactory iterationResultFactory;

    public Lexer(
            SafeIterator<MetaChar> charIterator,
            Tokenizer tokenizer,
            IterationResultFactory iterationResultFactory) {

        this.charIterator = charIterator;
        this.tokenizer = tokenizer;
        this.iterationResultFactory = iterationResultFactory;
    }

    @Override
    public SafeIterationResult<Token> next() {
        SafeIterationResult<MetaChar> iterationResult = charIterator.next();
        ProcessCheckpoint<MetaChar, Token> checkpoint = ProcessCheckpoint.UNINITIALIZED();
        MetaCharStringBuilder sb = new MetaCharStringBuilder();
        while (iterationResult.isCorrect()) {
            sb = sb.append(iterationResult.iterationResult());
            ProcessResult<Token> processResult = process(sb);
            switch (processResult.status()) {
                case COMPLETE:
                    checkpoint =
                            ProcessCheckpoint.INITIALIZED(
                                    iterationResult.nextIterator(), processResult.result());
                    break;
                case INVALID:
                    return processCheckpoint(checkpoint);
                case PREFIX:
                    break;
            }
            iterationResult = iterationResult.nextIterator().next();
        }
        return processCheckpoint(checkpoint);
    }

    private SafeIterationResult<Token> processCheckpoint(
            ProcessCheckpoint<MetaChar, Token> checkpoint) {
        if (checkpoint.isInitialized()) {
            return iterationResultFactory.createCorrectResult(
                    checkpoint.result(),
                    new Lexer(checkpoint.iterator(), tokenizer, iterationResultFactory));
        }
        return iterationResultFactory.createIncorrectResult("Error lexing");
    }

    private ProcessResult<Token> process(MetaCharStringBuilder builder) {
        return tokenizer.tokenize(builder.getString(), builder.getLine(), builder.getColumn());
    }
}
