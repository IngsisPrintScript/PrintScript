package com.ingsis.formatter.rules;

import java.util.List;

import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.type.TokenType;

public class BraceLine implements TriviaRule {
  private final boolean onIfLine;

  public BraceLine(boolean onIfLine) {
    this.onIfLine = onIfLine;
  }

  @Override
  public boolean appliea(Token previousToken, Token currentToken) {
    return previousToken.type().equals(TokenType.RPAREN) && currentToken.type().equals(TokenType.LBRACE);
  }

  @Override
  public StringBuilder apply(Token previousToken, List<Token> trivia, Token currentToken, StringBuilder stringBuilder) {
    if (!onIfLine) {
      stringBuilder.append("\n");
    } else {
      stringBuilder.append(" ");
    }
    stringBuilder.append(currentToken.value());
    return stringBuilder;
  }

}
