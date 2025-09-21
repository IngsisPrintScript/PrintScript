/*
 * My Project
 */

package com.ingsis.lexer.tokenizers;

import com.ingsis.tokens.Token;

public interface Tokenizer {
    Boolean canTokenize(String input);

    Token tokenize(String input);
}
