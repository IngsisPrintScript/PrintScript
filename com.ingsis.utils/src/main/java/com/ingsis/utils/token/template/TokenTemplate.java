package com.ingsis.utils.token.template;

import java.util.Optional;

import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.type.TokenType;

public record TokenTemplate(
    TokenType type,
    Optional<String> value) {

  public static TokenTemplate of(TokenType type) {
    return new TokenTemplate(type, Optional.empty());
  }

  public static TokenTemplate of(TokenType type, String value) {
    return new TokenTemplate(type, Optional.of(value));
  }

  public boolean matches(Token token) {
    if (!token.type().equals(type)) {
      return false;
    }
    return value.isEmpty() || token.value().equals(value.get());
  }
}
