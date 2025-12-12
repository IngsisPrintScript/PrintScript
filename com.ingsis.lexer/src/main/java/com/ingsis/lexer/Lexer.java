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
    SafeIterationResult<Token> result = maximalMunchOf();
    return result;
  }

  private SafeIterationResult<Token> maximalMunchOf() {
    SafeIterationResult<MetaChar> iterationResult = charIterator.next();
    ProcessCheckpoint<MetaChar, Token> checkpoint = ProcessCheckpoint.UNINITIALIZED();
    MetaCharStringBuilder sb = new MetaCharStringBuilder();
    List<Token> trailingTrivia = new ArrayList<>();
    while (iterationResult.isCorrect()) {
      sb = sb.append(iterationResult.iterationResult());

      ProcessResult<Token> processTralingToken = processTrivia(sb);
      switch (processTralingToken.status()) {
        case COMPLETE:
          trailingTrivia.add(processTralingToken.result());
          sb = new MetaCharStringBuilder();
          iterationResult = iterationResult.nextIterator().next();
          continue;
        case PREFIX, INVALID:
          break;
      }

      ProcessResult<Token> processResult = process(sb, trailingTrivia);
      switch (processResult.status()) {
        case COMPLETE:
          trailingTrivia = new ArrayList<>();
          checkpoint = ProcessCheckpoint.INITIALIZED(
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
          new Lexer(
              checkpoint.iterator(),
              this.triviaTokenizer,
              this.tokenizer,
              this.iterationResultFactory));
    }
    return iterationResultFactory.createIncorrectResult("Error lexing");
  }

  private ProcessResult<Token> processTrivia(MetaCharStringBuilder builder) {
    return triviaTokenizer.tokenize(
        builder.getString(), List.of(), builder.getLine(), builder.getColumn());
  }

  private ProcessResult<Token> process(
      MetaCharStringBuilder builder, List<Token> trailingTrivia) {
    return tokenizer.tokenize(
        builder.getString(), trailingTrivia, builder.getLine(), builder.getColumn());
  }
}
