/*
 * My Project
 */

package com.ingsis.utils.token.tokenstream;

import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.template.TokenTemplate;

import java.util.List;

public interface TokenStream extends SafeIterator<Token> {
  SafeIterationResult<Token> consume(TokenTemplate tokenTemplate);

  TokenStream consumeAll(TokenTemplate tokenTemplate);

  Result<Token> peek(int offset);

  List<Token> tokens();

  Integer pointer();

  TokenStream withToken(Token token);

  TokenStream reset();

  TokenStream sliceFromPointer();

  TokenStream advanceBy(TokenStream subStream);
}
