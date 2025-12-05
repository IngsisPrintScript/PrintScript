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
import com.ingsis.utils.process.state.ProcessState;
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

  private ProcessResult<Token> process(MetaCharStringBuilder builder) {
    // replace with real tokenization logic
    throw new UnsupportedOperationException("Not implemented yet.");
  }

  @Override
  public SafeIterationResult<Token> next() {
    SafeIterationResult<MetaChar> iterationResult = charIterator.next();
    if (!iterationResult.isCorrect()) {
      return iterationResultFactory.cloneIncorrectResult(iterationResult);
    }

    return lexNextToken(iterationResult, metaCharStringBuilder);
  }

  private SafeIterationResult<Token> lexNextToken(
      SafeIterationResult<MetaChar> iterationResult,
      MetaCharStringBuilder builder) {
    ProcessCheckpoint<MetaChar, MetaCharStringBuilder, Token> checkpoint = ProcessCheckpoint.UNINITIALIZED();
    MetaCharStringBuilder currentBuilder = builder.append(iterationResult.iterationResult());
    SafeIterator<MetaChar> currentIterator = iterationResult.nextIterator();

    while (true) {
      ProcessResult<Token> result = process(currentBuilder);
      checkpoint = updateCheckpoint(checkpoint, result, currentIterator, currentBuilder);
      if (result.status() == ProcessState.INVALID) {
        return emitCheckpointOrError(checkpoint, currentBuilder);
      }
      SafeIterationResult<MetaChar> nextCharResult = currentIterator.next();
      if (!nextCharResult.isCorrect()) {
        return emitCheckpointOrError(checkpoint, currentBuilder, nextCharResult);
      }
      currentBuilder = currentBuilder.append(nextCharResult.iterationResult());
      currentIterator = nextCharResult.nextIterator();
    }
  }

  private ProcessCheckpoint<MetaChar, MetaCharStringBuilder, Token> updateCheckpoint(
      ProcessCheckpoint<MetaChar, MetaCharStringBuilder, Token> checkpoint,
      ProcessResult<Token> result,
      SafeIterator<MetaChar> iterator,
      MetaCharStringBuilder builder) {
    if (result.status() == ProcessState.COMPLETE) {
      return ProcessCheckpoint.INITIALIZED(iterator, builder, result.result());
    }
    return checkpoint;
  }

  private SafeIterationResult<Token> emitCheckpointOrError(
      ProcessCheckpoint<MetaChar, MetaCharStringBuilder, Token> checkpoint,
      MetaCharStringBuilder builder) {
    if (checkpoint.isUninitialized()) {
      return iterationResultFactory.createIncorrectResult("Unable to tokenize sequence: " + builder.getString());
    }
    return iterationResultFactory.createCorrectResult(
        checkpoint.result(),
        new Lexer(
            checkpoint.iterator(),
            this.tokenizer,
            this.iterationResultFactory));
  }

  private SafeIterationResult<Token> emitCheckpointOrError(
      ProcessCheckpoint<MetaChar, MetaCharStringBuilder, Token> checkpoint,
      MetaCharStringBuilder builder,
      SafeIterationResult<MetaChar> lastCharResult) {
    if (checkpoint.isUninitialized()) {
      return iterationResultFactory.cloneIncorrectResult(lastCharResult);
    }
    return iterationResultFactory.createCorrectResult(
        checkpoint.result(),
        new Lexer(
            checkpoint.iterator(),
            this.tokenizer,
            this.iterationResultFactory));
  }
}
