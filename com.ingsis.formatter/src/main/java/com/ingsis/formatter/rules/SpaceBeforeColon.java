package com.ingsis.formatter.rules;

import java.util.List;

import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.type.TokenType;

public class SpaceBeforeColon implements TriviaRule {
  @Override
  public boolean applies(Token previousToken, Token currentToken) {
    return currentToken.type().equals(TokenType.COLON);
  }

  @Override
  public StringBuilder apply(Token previousToken, List<Token> trivia, Token currentToken, StringBuilder sb, int indentation) {
    sb.append(" ");
    sb.append(currentToken.value());
    return sb;
  }

}
