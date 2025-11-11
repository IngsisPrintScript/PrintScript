/*
 * My Project
 */

package com.ingsis.lexer;

import com.ingsis.lexer.tokenizers.Tokenizer;
import com.ingsis.metachar.MetaChar;
import com.ingsis.metachar.string.builder.MetaCharStringBuilder;
import com.ingsis.peekableiterator.PeekableIterator;
import com.ingsis.result.Result;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.tokens.Token;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public final class DefaultLexer implements Lexer {
  private final PeekableIterator<MetaChar> charIterator;
  private final Queue<Token> tokenBuffer;
  private final Tokenizer tokenizer;
  private final ResultFactory RESULT_FACTORY;

  public DefaultLexer(
      PeekableIterator<MetaChar> charIterator,
      Tokenizer tokenizer,
      Queue<Token> tokenBuffer,
      ResultFactory resultFactory) {
    this.charIterator = charIterator;
    this.tokenBuffer = new LinkedList<>(tokenBuffer);
    this.tokenizer = tokenizer;
    this.RESULT_FACTORY = resultFactory;
  }

  public DefaultLexer(
      PeekableIterator<MetaChar> charIterator,
      Tokenizer tokenizer,
      ResultFactory resultFactory) {
    this(charIterator, tokenizer, new LinkedList<>(), resultFactory);
  }

  @Override
  public Result<Token> analyze(MetaCharStringBuilder stringBuilder) {
    return tokenizer.tokenize(
        stringBuilder.getString(), stringBuilder.getLine(), stringBuilder.getColumn());
  }

  @Override
  public Token peek() {
    if (!hasNext()) {
      throw new NoSuchElementException();
    }
    return tokenBuffer.peek();
  }

  @Override
  public boolean hasNext() {
    if (!tokenBuffer.isEmpty()) {
      return true;
    }

    Result<Token> computeNextResult = computeNext();

    if (computeNextResult.isCorrect()) {
      tokenBuffer.add(computeNextResult.result());
    }

    return computeNextResult.isCorrect();
  }

  @Override
  public Token next() {
    if (!hasNext()) {
      throw new NoSuchElementException();
    }
    return tokenBuffer.poll();
  }

  private Result<Token> computeNext() {
    Token cadidateToken = null;
    MetaCharStringBuilder stringBuilder = new MetaCharStringBuilder();
    while (charIterator.hasNext()) {
      MetaChar nextChar = charIterator.peek();
      stringBuilder.append(nextChar);
      Result<Token> analyzeInputResult = this.analyze(stringBuilder);
      if (analyzeInputResult.isCorrect()) {
        cadidateToken = analyzeInputResult.result();
      } else {
        if (cadidateToken != null) {
          return RESULT_FACTORY.createCorrectResult(cadidateToken);
        }
      }
      charIterator.next();
    }
    if (cadidateToken == null) {
      return RESULT_FACTORY.createIncorrectResult(
          String.format(
              "Unkown token on line: %d and column: %d",
              stringBuilder.getLine(), stringBuilder.getColumn()));
    } else {
      return RESULT_FACTORY.createCorrectResult(cadidateToken);
    }
  }
}
