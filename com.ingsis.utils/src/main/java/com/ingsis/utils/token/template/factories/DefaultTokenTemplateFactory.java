package com.ingsis.utils.token.template.factories;

import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.template.TokenTemplate;
import com.ingsis.utils.token.type.TokenType;

public class DefaultTokenTemplateFactory implements TokenTemplateFactory {

  @Override
  public TokenTemplate numberLiteral() {
    return TokenTemplate.of(TokenType.NUMBER_LITERAL);
  }

  @Override
  public TokenTemplate stringLiteral() {
    return TokenTemplate.of(TokenType.STRING_LITERAL);
  }

  @Override
  public TokenTemplate booleanLiteral() {
    return TokenTemplate.of(TokenType.BOOLEAN_LITERAL);
  }

  @Override
  public TokenTemplate identifier() {
    return TokenTemplate.of(TokenType.IDENTIFIER);
  }

  @Override
  public Result<TokenTemplate> keyword(String keyword) {
    if (!TokenType.isKeyword(keyword)) {
      return new IncorrectResult<>("Tried to create a template keyword token from: " + keyword);
    }
    Result<TokenType> getTokenTypeResult = TokenType.fromString(keyword);
    if (!getTokenTypeResult.isCorrect()) {
      return new IncorrectResult<>(getTokenTypeResult);
    }
    TokenType type = getTokenTypeResult.result();
    return new CorrectResult<TokenTemplate>(
        TokenTemplate.of(type));
  }

  @Override
  public Result<TokenTemplate> type(String type) {
    if (!TokenType.isType(type)) {
      return new IncorrectResult<>("Tried to create a template type token from: " + type);
    }
    Result<TokenType> getTokenTypeResult = TokenType.fromString(type);
    if (!getTokenTypeResult.isCorrect()) {
      return new IncorrectResult<>(getTokenTypeResult);
    }
    TokenType ttype = getTokenTypeResult.result();
    return new CorrectResult<TokenTemplate>(
        TokenTemplate.of(ttype));
  }

  @Override
  public Result<TokenTemplate> operator(String symbol) {
    if (!TokenType.isOperator(symbol)) {
      return new IncorrectResult<>("Tried to create a template operator token from: " + symbol);
    }
    Result<TokenType> getTokenTypeResult = TokenType.fromString(symbol);
    if (!getTokenTypeResult.isCorrect()) {
      return new IncorrectResult<>(getTokenTypeResult);
    }
    TokenType type = getTokenTypeResult.result();
    return new CorrectResult<TokenTemplate>(
        TokenTemplate.of(type));
  }

  @Override
  public Result<TokenTemplate> separator(String symbol) {
    if (!TokenType.isSeparator(symbol)) {
      return new IncorrectResult<>("Tried to create a template operator separator from: " + symbol);
    }
    Result<TokenType> getTokenTypeResult = TokenType.fromString(symbol);
    if (!getTokenTypeResult.isCorrect()) {
      return new IncorrectResult<>(getTokenTypeResult);
    }
    TokenType type = getTokenTypeResult.result();
    return new CorrectResult<TokenTemplate>(
        TokenTemplate.of(type));
  }
}
