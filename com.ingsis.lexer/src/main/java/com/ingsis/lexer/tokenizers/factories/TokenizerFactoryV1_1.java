/*
 * My Project
 */

package com.ingsis.lexer.tokenizers.factories;

import com.ingsis.lexer.tokenizers.ExactMatchTokenizer;
import com.ingsis.lexer.tokenizers.PatternMatchTokenizer;
import com.ingsis.lexer.tokenizers.categories.TokenCategory;
import com.ingsis.lexer.tokenizers.registry.TokenizersRegistry;
import com.ingsis.utils.token.factories.TokenFactory;
import com.ingsis.utils.token.type.TokenType;

public final class TokenizerFactoryV1_1 implements TokenizerFactory {
    private final TokenFactory tokenFactory;
    private final TokenizerFactory previousVerionFactory;

    public TokenizerFactoryV1_1(TokenFactory tokenFactory) {
        this.tokenFactory = tokenFactory;
        this.previousVerionFactory = new TokenizerFactoryV1_0(tokenFactory);
    }

    @Override
    public TokenizersRegistry createTokenizer() {
        TokenizersRegistry registry = previousVerionFactory.createTokenizer();
        registry = keywordTokenizer(registry);
        registry = typeTokenizer(registry);
        registry = literalTokenizer(registry);
        return registry;
    }

    private TokenizersRegistry keywordTokenizer(TokenizersRegistry registry) {
        registry =
                registry.registerTokenizer(
                        new ExactMatchTokenizer(
                                TokenType.CONST, TokenCategory.KEYWORD, this.tokenFactory));
        registry =
                registry.registerTokenizer(
                        new ExactMatchTokenizer(
                                TokenType.IF, TokenCategory.KEYWORD, this.tokenFactory));
        registry =
                registry.registerTokenizer(
                        new ExactMatchTokenizer(
                                TokenType.ELSE, TokenCategory.KEYWORD, this.tokenFactory));
        return registry;
    }

    private TokenizersRegistry typeTokenizer(TokenizersRegistry registry) {
        registry =
                registry.registerTokenizer(
                        new ExactMatchTokenizer(
                                TokenType.BOOLEAN, TokenCategory.TYPE, this.tokenFactory));
        return registry;
    }

    private TokenizersRegistry literalTokenizer(TokenizersRegistry registry) {
        registry =
                registry.registerTokenizer(
                        new PatternMatchTokenizer(
                                TokenType.BOOLEAN_LITERAL,
                                TokenCategory.BOOLEAN_LITERAL,
                                this.tokenFactory));
        return registry;
    }
}
