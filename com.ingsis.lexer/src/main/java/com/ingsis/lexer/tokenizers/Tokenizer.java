/*
 * My Project
 */

package com.ingsis.lexer.tokenizers;

import com.ingsis.result.Result;
import com.ingsis.tokens.Token;

public interface Tokenizer {
    Result<Token> tokenize(String input);
}
