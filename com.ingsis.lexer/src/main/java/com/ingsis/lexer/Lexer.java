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
    private final MetaCharStringBuilder metaCharStringBuilder;
    private final IterationResultFactory iterationResultFactory;

    public Lexer(
            SafeIterator<MetaChar> charIterator,
            Tokenizer tokenizer,
            MetaCharStringBuilder metaCharStringBuilder,
            IterationResultFactory iterationResultFactory) {

        this.charIterator = charIterator;
        this.tokenizer = tokenizer;
        this.metaCharStringBuilder = metaCharStringBuilder;
        this.iterationResultFactory = iterationResultFactory;
    }

    public Lexer(
            SafeIterator<MetaChar> charIterator,
            Tokenizer tokenizer,
            IterationResultFactory iterationResultFactory) {

        this(charIterator, tokenizer, new MetaCharStringBuilder(), iterationResultFactory);
    }

    @Override
    public SafeIterationResult<Token> next() {
        return maximalMunchOf(metaCharStringBuilder, charIterator);
    }

    private SafeIterationResult<Token> maximalMunchOf(
            MetaCharStringBuilder builder, SafeIterator<MetaChar> currentIterator) {
        ProcessCheckpoint<MetaChar, Token> checkpoint = ProcessCheckpoint.UNINITIALIZED();
        while (true) {
            if (builder.getString().isEmpty()) {
                SafeIterationResult<MetaChar> iterationResult = currentIterator.next();
                if (!iterationResult.isCorrect()) {
                    return iterationResultFactory.cloneIncorrectResult(iterationResult);
                }
                builder = builder.append(iterationResult.iterationResult());
                currentIterator = iterationResult.nextIterator();
            }

            ProcessResult<Token> processBuilder = process(builder);
            switch (processBuilder.status()) {
                case COMPLETE ->
                        checkpoint =
                                ProcessCheckpoint.INITIALIZED(
                                        currentIterator, processBuilder.result());
                case INVALID -> {
                    if (checkpoint.isUninitialized()) {
                        return iterationResultFactory.createIncorrectResult(
                                "Cannot tokenize: " + "\"" + builder.getString() + "\"");
                    }
                    return iterationResultFactory.createCorrectResult(
                            checkpoint.result(),
                            new Lexer(
                                    checkpoint.iterator(),
                                    this.tokenizer,
                                    new MetaCharStringBuilder(),
                                    this.iterationResultFactory));
                }
                case PREFIX -> {}
            }

            SafeIterationResult<MetaChar> getNextMetachar = currentIterator.next();
            if (!getNextMetachar.isCorrect()) {
                processBuilder = process(builder);
                return switch (processBuilder.status()) {
                    case COMPLETE ->
                            iterationResultFactory.createCorrectResult(
                                    processBuilder.result(),
                                    new Lexer(
                                            currentIterator,
                                            this.tokenizer,
                                            this.iterationResultFactory));
                    case PREFIX, INVALID ->
                            iterationResultFactory.cloneIncorrectResult(getNextMetachar);
                };
            }
            builder = builder.append(getNextMetachar.iterationResult());
            currentIterator = getNextMetachar.nextIterator();
        }
    }

    private ProcessResult<Token> process(MetaCharStringBuilder builder) {
        return tokenizer.tokenize(builder.getString(), builder.getLine(), builder.getColumn());
    }

    public String toPrettyString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Lexer {\n");

        sb.append("  buffer: \"").append(metaCharStringBuilder.getString()).append("\"\n");

        sb.append("  bufferChars: ");
        sb.append(metaCharStringBuilder.chars());
        sb.append("\n");

        sb.append("  bufferSize: ").append(metaCharStringBuilder.chars().size()).append("\n");

        sb.append("  charIterator: ").append(charIterator.getClass().getSimpleName()).append("\n");

        sb.append("  tokenizer: ").append(tokenizer.getClass().getSimpleName()).append("\n");

        sb.append("  iterationResultFactory: ")
                .append(iterationResultFactory.getClass().getSimpleName())
                .append("\n");

        sb.append("}");

        return sb.toString();
    }

    @Override
    public String toString() {
        return toPrettyString();
    }
}
