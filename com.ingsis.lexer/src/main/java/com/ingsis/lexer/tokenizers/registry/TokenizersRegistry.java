/*
 * My Project
 */

package com.ingsis.lexer.tokenizers.registry;

import com.ingsis.lexer.tokenizers.Tokenizer;

public interface TokenizersRegistry extends Tokenizer {
  TokenizersRegistry registerTokenizer(Tokenizer tokenizer);
}
