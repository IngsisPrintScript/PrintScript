package com.ingsis.formatter.rules;

import java.util.List;

import com.ingsis.utils.token.Token;

public class BaseRule implements TriviaRule {

  @Override
  public boolean applies(Token previousToken, Token currentToken) {
    return true;
  }

  @Override
  public StringBuilder apply(
          Token previousToken,
          List<Token> trivia,
          Token currentToken,
          StringBuilder stringBuilder,
          int indentation
  ) {
    StringBuilder triviaBuffer = new StringBuilder();

    for (Token token : trivia) {
      triviaBuffer.append(token.value());
    }

    int lastNewline = triviaBuffer.lastIndexOf("\n");

    if (lastNewline == -1) {
      // No newline → preserve trivia exactly
      stringBuilder.append(triviaBuffer);
    } else {
      // Always keep everything up to the newline
      stringBuilder.append(triviaBuffer, 0, lastNewline + 1);

      if (indentation == 0) {
        // Keep original spaces if indentation is zero
        stringBuilder.append(triviaBuffer.substring(lastNewline + 1));
      } else {
        // Formatter owns indentation
        stringBuilder.append(" ".repeat(indentation));
      }
    }

    stringBuilder.append(currentToken.value());
    return stringBuilder;
  }
}
