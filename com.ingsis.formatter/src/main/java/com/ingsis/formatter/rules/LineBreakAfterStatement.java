package com.ingsis.formatter.rules;

import java.util.List;

import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.type.TokenType;

public class LineBreakAfterStatement implements TriviaRule {
  @Override
  public boolean appliea(Token previousToken, Token currentToken) {
    return previousToken.type().equals(TokenType.SEMICOLON);
  }

  @Override
  public StringBuilder apply(Token previousToken, List<Token> trivia, Token currentToken, StringBuilder stringBuilder) {
    stringBuilder.append("\n");
    stringBuilder.append(currentToken.value());
    return stringBuilder;
  }

}
