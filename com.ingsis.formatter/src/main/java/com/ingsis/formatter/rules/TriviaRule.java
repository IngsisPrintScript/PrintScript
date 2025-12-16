package com.ingsis.formatter.rules;

import java.util.List;

import com.ingsis.utils.token.Token;

public interface TriviaRule {
  boolean appliea(Token previousToken, Token currentToken);

  StringBuilder apply(Token previousToken, List<Token> trivia, Token currentToken, StringBuilder stringBuilder);
}
