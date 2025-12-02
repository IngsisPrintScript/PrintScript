/*
 * My Project
 */

package com.ingsis.utils.token.tokenstream;

import com.ingsis.utils.peekableiterator.PeekableIterator;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.tokens.Token;
import java.util.List;

public interface TokenStream extends PeekableIterator<Token> {
  boolean match(Token tokenTemplate);

  Result<Token> consume();

  Result<Token> consume(Token token);

  Result<Integer> consumeAll(Token token);

  Token peek(int offset);

  List<Token> tokens();

  Integer pointer();

  TokenStream addToken(Token token);

  void cleanBuffer();

  void resetPointer();

  TokenStream retrieveNonConsumedStream();

  TokenStream advanceMovedTokens(TokenStream subStream);
}
