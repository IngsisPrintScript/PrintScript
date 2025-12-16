package com.ingsis.formatter.rules;

import java.util.List;

import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.type.TokenType;

public class SingleSpaceInBetween implements TriviaRule {
  @Override
  public boolean applies(Token previousToken, Token currentToken) {
    return !currentToken.type().equals(TokenType.SEMICOLON) && !previousToken.type().equals(TokenType.SEMICOLON);
  }

  @Override
  public StringBuilder apply(Token previousToken, List<Token> trivia, Token currentToken, StringBuilder stringBuilder, int indentation) {
    stringBuilder.append(" ");
    stringBuilder.append(currentToken.value());
    return stringBuilder;
  }

  private boolean isWordLike(TokenType type) {
    return switch (type) {
      case IDENTIFIER,
          NUMBER_LITERAL,
          STRING_LITERAL,
          BOOLEAN_LITERAL,
          NUMBER,
          STRING,
          BOOLEAN,
          LET,
          CONST,
          IF,
          ELSE ->
        true;
      default -> false;
    };
  }

}
