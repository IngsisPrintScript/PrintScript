/*
 * My Project
 */

package com.ingsis.lexer.tokenizers.factories;

import com.ingsis.lexer.tokenizers.registry.TokenizersRegistry;

public interface TokenizerFactory {
    TokenizersRegistry createTokenizer();

    TokenizersRegistry createTriviaTokenizer();
}
