/*
 * My Project
 */

package com.ingsis.tokenstream;

import com.ingsis.peekableiterator.PeekableIterator;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.tokens.Token;
import com.ingsis.tokens.factories.DefaultTokensFactory;
import java.util.ArrayList;
import java.util.List;

public final class DefaultTokenStream implements TokenStream {
  private final PeekableIterator<Token> tokens;
  private final List<Token> tokenBuffer;
  private final Token SPACE_TOKEN_TEMPLATE;
  private Integer pointer;

  public DefaultTokenStream(PeekableIterator<Token> tokenStream) {
    this.tokens = tokenStream;
    this.SPACE_TOKEN_TEMPLATE = new DefaultTokensFactory().createSpaceSeparatorToken("");
    this.tokenBuffer = new ArrayList<>();
    this.pointer = 0;
  }

  @Override
  public Result<Token> consume() {
    consumeAll(SPACE_TOKEN_TEMPLATE);
    if (pointer < tokenBuffer.size()) {
      return new CorrectResult<>(tokenBuffer.get(pointer++));
    }
    if (tokens.hasNext()) {
      return new CorrectResult<>(tokens.next());
    }
    return new IncorrectResult<>("No more tokens.");
  }

  @Override
  public Result<Token> consume(Token tokenTemplate) {
    consumeAll(SPACE_TOKEN_TEMPLATE);
    if (!baseMatch(tokenTemplate)) {
      return new IncorrectResult<>("Token does not match template.");
    }
    return consume();
  }

  @Override
  public Result<Integer> consumeAll(Token token) {
    int count = 0;
    while (pointer < tokenBuffer.size()) {
      while (baseMatch(token) || baseMatch(SPACE_TOKEN_TEMPLATE)) {
        pointer++;
        count++;
      }
      return new CorrectResult<>(count);
    }
    while (baseMatch(token) || baseMatch(SPACE_TOKEN_TEMPLATE)) {
      tokens.next();
      count++;
    }
    return new CorrectResult<>(count);
  }

  @Override
  public boolean match(Token tokenTemplate) {
    consumeAll(SPACE_TOKEN_TEMPLATE);
    return baseMatch(tokenTemplate);
  }

  private boolean baseMatch(Token tokenTemplate) {
    if (pointer < tokenBuffer.size()) {
      return tokenBuffer.get(pointer).equals(tokenTemplate);
    }
    return tokens.hasNext() && tokens.peek().equals(tokenTemplate);
  }

  @Override
  public Token peek() {
    if (pointer < tokenBuffer.size()) {
      return tokenBuffer.get(pointer);
    }
    return tokens.peek();
  }

  @Override
  public boolean hasNext() {
    return tokens.hasNext() || pointer < tokenBuffer.size();
  }

  @Override
  public Token next() {
    if (pointer < tokenBuffer.size()) {
      return tokenBuffer.get(pointer++);
    }
    return tokens.next();
  }

  @Override
  public Token peek(int offset) {
    while (offset + pointer >= tokenBuffer.size()) {
      if (!tokens.hasNext()) {
        return null;
      }
      tokenBuffer.add(tokens.next());
    }
    return tokenBuffer.get(pointer + offset);
  }

  @Override
  public void cleanBuffer() {
    this.pointer = 0;
    this.tokenBuffer.clear();
  }
}
