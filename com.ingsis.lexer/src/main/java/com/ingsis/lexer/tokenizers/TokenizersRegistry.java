/*
 * My Project
 */

package com.ingsis.lexer.tokenizers;

public interface TokenizersRegistry extends Tokenizer {
    void registerTokenizer(Tokenizer tokenizer);
}
