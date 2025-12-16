package com.ingsis.formatter.rules;

import java.util.List;

import com.ingsis.utils.token.Token;

public class BaseRule implements TriviaRule {

  @Override
  public boolean appliea(Token previousToken, Token currentToken) {
    return true;
  }

  @Override
  public StringBuilder apply(Token previousToken, List<Token> trivia, Token currentToken, StringBuilder stringBuilder) {
    for (Token token : trivia) {
      stringBuilder.append(token.value());
    }
    stringBuilder.append(currentToken.value());
    return stringBuilder;
  }

}
