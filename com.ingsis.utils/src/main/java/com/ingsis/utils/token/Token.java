/*
 * My Project
 */

package com.ingsis.utils.token;

import java.util.List;

import com.ingsis.utils.token.type.TokenType;

public interface Token {
  TokenType type();

  Integer line();

  Integer column();

  String value();

  List<Token> leadingTrivia();
}
