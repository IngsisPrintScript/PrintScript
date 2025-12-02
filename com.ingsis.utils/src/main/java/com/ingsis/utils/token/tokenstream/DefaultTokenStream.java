/*
 * My Project
 */

package com.ingsis.utils.token.tokenstream;

import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.tokens.Token;
import com.ingsis.utils.token.tokens.factories.DefaultTokensFactory;
import java.util.ArrayList;
import java.util.List;

public final class DefaultTokenStream implements TokenStream {
  private List<Token> tokenBuffer;
  private final Token SPACE_TOKEN_TEMPLATE;
  private int pointer;

  public DefaultTokenStream(List<Token> tokens, Integer pointer) {
    this.SPACE_TOKEN_TEMPLATE = new DefaultTokensFactory().createSpaceSeparatorToken("", null, null);
    this.tokenBuffer = new ArrayList<>(tokens);
    this.pointer = pointer;
  }

  public DefaultTokenStream(List<Token> tokens) {
    this(tokens, 0);
  }

  public DefaultTokenStream() {
    this(new ArrayList<>());
  }

  @Override
  public Result<Token> consume() {
    consumeAll(SPACE_TOKEN_TEMPLATE);
    if (pointer < tokenBuffer.size()) {
      return new CorrectResult<>(tokenBuffer.get(pointer++));
    }
    consumeAll(SPACE_TOKEN_TEMPLATE);
    return new IncorrectResult<>("No more tokens.");
  }

  @Override
  public Result<Token> consume(Token tokenTemplate) {
    consumeAll(SPACE_TOKEN_TEMPLATE);

    if (!baseMatch(tokenTemplate)) {
      if (!hasNext()) {
        return new IncorrectResult<>("Uncomplete tokens sequence");
      }
      Token peekToken = peek();
      return new IncorrectResult<>(
          String.format(
              "Unexpected token on line: %d and column: %d, original:%s expected:%s",
              peekToken.line(), peekToken.column(), peekToken, tokenTemplate));
    }

    return consume();
  }

  @Override
  public Result<Integer> consumeAll(Token tokenTemplate) {
    int count = 0;

    while (pointer < tokenBuffer.size()
        && (baseMatch(tokenTemplate) || baseMatch(SPACE_TOKEN_TEMPLATE))) {
      pointer++;
      count++;
    }

    return new CorrectResult<>(count);
  }

  @Override
  public boolean match(Token tokenTemplate) {
    consumeAll(SPACE_TOKEN_TEMPLATE);
    return baseMatch(tokenTemplate);
  }

  private boolean baseMatch(Token template) {
    return pointer < tokenBuffer.size() && tokenBuffer.get(pointer).equals(template);
  }

  @Override
  public Token peek() {
    consumeAll(SPACE_TOKEN_TEMPLATE);
    if (pointer < tokenBuffer.size()) {
      return tokenBuffer.get(pointer);
    }
    return null;
  }

  @Override
  public Token peek(int offset) {
    consumeAll(SPACE_TOKEN_TEMPLATE);
    int index = pointer + offset;
    if (index >= tokenBuffer.size()) {
      return null;
    }
    return tokenBuffer.get(index);
  }

  @Override
  public boolean hasNext() {
    return pointer < tokenBuffer.size();
  }

  @Override
  public Token next() {
    if (pointer < tokenBuffer.size()) {
      return tokenBuffer.get(pointer++);
    }
    return null;
  }

  @Override
  public void cleanBuffer() {
    List<Token> remaining = new ArrayList<>(tokenBuffer.subList(pointer, tokenBuffer.size()));
    tokenBuffer.clear();
    tokenBuffer.addAll(remaining);
    pointer = 0;
  }

  @Override
  public List<Token> tokens() {
    return new ArrayList<>(tokenBuffer);
  }

  @Override
  public TokenStream addToken(Token token) {
    List<Token> tokens = new ArrayList<>(this.tokens());
    tokens.add(token);
    return new DefaultTokenStream(tokens, pointer);
  }

  @Override
  public Integer pointer() {
    return pointer;
  }

  @Override
  public void resetPointer() {
    this.pointer = 0;
  }

  @Override
  public TokenStream retrieveNonConsumedStream() {
    List<Token> tokens = new ArrayList<>(tokens());
    tokens = tokens.subList(pointer, tokens.size());
    return new DefaultTokenStream(tokens);
  }

  @Override
  public TokenStream advanceMovedTokens(TokenStream subStream) {
    if (subStream == null) {
      throw new IllegalArgumentException("subStream cannot be null");
    }
    // Move this stream's pointer forward by the number of tokens consumed in
    // subStream
    int consumed = subStream.pointer();
    this.pointer = Math.min(this.pointer + consumed, this.tokenBuffer.size());
    return this;
  }
}
