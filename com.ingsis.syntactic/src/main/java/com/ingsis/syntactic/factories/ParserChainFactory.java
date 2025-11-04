/*
 * My Project
 */

package com.ingsis.syntactic.factories;

import com.ingsis.syntactic.parsers.Parser;

public interface ParserChainFactory {
  Parser createDefaultChain();

  Parser createExpressionChain();
}
