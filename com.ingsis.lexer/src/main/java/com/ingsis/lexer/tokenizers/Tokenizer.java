/*
 * My Project
 */

package com.ingsis.lexer.tokenizers;

import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.tokens.Token;

public interface Tokenizer {
    Result<Token> tokenize(String input, Integer line, Integer column);
}
