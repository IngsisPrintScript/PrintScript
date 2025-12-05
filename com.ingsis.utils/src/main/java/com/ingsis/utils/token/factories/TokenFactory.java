/*
 * My Project
 */

package com.ingsis.utils.token.factories;

import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.Token;

public interface TokenFactory {
  Result<Token> createToken(String keyword, Integer line, Integer column);
}
