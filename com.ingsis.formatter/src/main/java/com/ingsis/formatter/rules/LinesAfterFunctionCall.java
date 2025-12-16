package com.ingsis.formatter.rules;

import java.util.List;

import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.type.TokenType;

public class LinesAfterFunctionCall implements TriviaRule {
  private final Integer amount;

  public LinesAfterFunctionCall(Integer amount) {
    this.amount = amount;
  }

  @Override
  public boolean appliea(Token previousToken, Token currentToken) {
    return previousToken.type().equals(TokenType.SEMICOLON);
  }

  @Override
  public StringBuilder apply(Token previousToken, List<Token> trivia, Token currentToken, StringBuilder stringBuilder) {
    if (amount == null) {
      for (Token token : trivia) {
        stringBuilder.append(token.value());
      }
    } else {
        stringBuilder.append("\n".repeat(Math.max(0, amount+1)));
    }
    stringBuilder.append(currentToken.value());
    return stringBuilder;
  }

}
