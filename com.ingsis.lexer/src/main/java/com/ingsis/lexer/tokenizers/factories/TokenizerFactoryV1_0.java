/*
 * My Project
 */

package com.ingsis.lexer.tokenizers.factories;

import com.ingsis.lexer.tokenizers.ExactMatchTokenizer;
import com.ingsis.lexer.tokenizers.PatternMatchTokenizer;
import com.ingsis.lexer.tokenizers.PrefixAwarePatternTokenizer;
import com.ingsis.lexer.tokenizers.categories.TokenCategory;
import com.ingsis.lexer.tokenizers.registry.DefaultTokenizersRegistry;
import com.ingsis.lexer.tokenizers.registry.TokenizersRegistry;
import com.ingsis.utils.token.factories.TokenFactory;
import com.ingsis.utils.token.type.TokenType;

public final class TokenizerFactoryV1_0 implements TokenizerFactory {
    private final TokenFactory tokenFactory;

    public TokenizerFactoryV1_0(TokenFactory tokenFactory) {
        this.tokenFactory = tokenFactory;
    }

    @Override
    public TokenizersRegistry createTokenizer() {
        TokenizersRegistry registry = new DefaultTokenizersRegistry();
        registry = keywordTokenizer(registry);
        registry = operatorTokenizer(registry);
        registry = separatorTokenizer(registry);
        registry = typeTokenizer(registry);
        registry = literalTokenizer(registry);
        registry = identifierTokenizer(registry);
        return registry;
    }

    @Override
    public TokenizersRegistry createTriviaTokenizer() {
        TokenizersRegistry registry = new DefaultTokenizersRegistry();
        for (TokenType trivia : TokenType.TRIVIA) {
            registry =
                    registry.registerTokenizer(
                            new ExactMatchTokenizer(
                                    trivia, TokenCategory.TRIVIA, this.tokenFactory));
        }
        return registry;
    }

    private TokenizersRegistry keywordTokenizer(TokenizersRegistry registry) {
        registry =
                registry.registerTokenizer(
                        new ExactMatchTokenizer(
                                TokenType.LET, TokenCategory.KEYWORD, this.tokenFactory));
        return registry;
    }

    private TokenizersRegistry operatorTokenizer(TokenizersRegistry registry) {
        for (TokenType operatorType : TokenType.OPERATORS) {
            registry =
                    registry.registerTokenizer(
                            new ExactMatchTokenizer(
                                    operatorType, TokenCategory.OPERATOR, this.tokenFactory));
        }
        return registry;
    }

    private TokenizersRegistry separatorTokenizer(TokenizersRegistry registry) {
        for (TokenType separatorsType : TokenType.SEPARATORS) {
            registry =
                    registry.registerTokenizer(
                            new ExactMatchTokenizer(
                                    separatorsType, TokenCategory.SEPARATOR, this.tokenFactory));
        }
        return registry;
    }

    private TokenizersRegistry typeTokenizer(TokenizersRegistry registry) {
        for (TokenType type : TokenType.TYPES) {
            registry =
                    registry.registerTokenizer(
                            new ExactMatchTokenizer(type, TokenCategory.TYPE, this.tokenFactory));
        }
        return registry;
    }

    private TokenizersRegistry literalTokenizer(TokenizersRegistry registry) {
        registry =
                registry.registerTokenizer(
                        new PrefixAwarePatternTokenizer(
                                TokenType.STRING_LITERAL,
                                TokenCategory.STRING_LITERAL,
                                this.tokenFactory));
        registry =
                registry.registerTokenizer(
                        new PrefixAwarePatternTokenizer(
                                TokenType.NUMBER_LITERAL,
                                TokenCategory.NUMBER_LITERAL,
                                this.tokenFactory));
        return registry;
    }

    private TokenizersRegistry identifierTokenizer(TokenizersRegistry registry) {
        registry =
                registry.registerTokenizer(
                        new PatternMatchTokenizer(
                                TokenType.IDENTIFIER, TokenCategory.IDENTIFIER, this.tokenFactory));
        return registry;
    }
}
