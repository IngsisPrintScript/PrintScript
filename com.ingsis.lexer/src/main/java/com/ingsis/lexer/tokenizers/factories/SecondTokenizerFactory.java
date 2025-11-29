/*
 * My Project
 */

package com.ingsis.lexer.tokenizers.factories;

import com.ingsis.lexer.tokenizers.DefaultTokenizersRegistry;
import com.ingsis.lexer.tokenizers.Tokenizer;
import com.ingsis.lexer.tokenizers.TokenizersRegistry;
import com.ingsis.lexer.tokenizers.identifier.IdentifierTokenizer;
import com.ingsis.lexer.tokenizers.keyword.ConstKeywordTokenizer;
import com.ingsis.lexer.tokenizers.keyword.ElseKeywordTokenizer;
import com.ingsis.lexer.tokenizers.keyword.IfKeywordTokenizer;
import com.ingsis.lexer.tokenizers.keyword.LetKeywordTokenizer;
import com.ingsis.lexer.tokenizers.literal.BooleanLiteralTokenizer;
import com.ingsis.lexer.tokenizers.literal.NumberLiteralTokenizer;
import com.ingsis.lexer.tokenizers.literal.StringLiteralTokenizer;
import com.ingsis.lexer.tokenizers.operator.GenericOperatorTokenizer;
import com.ingsis.lexer.tokenizers.separator.EndOfLineSeparatorTokenizer;
import com.ingsis.lexer.tokenizers.separator.GenericSeparatorTokenizer;
import com.ingsis.lexer.tokenizers.separator.SpaceSeparatorTokenizer;
import com.ingsis.lexer.tokenizers.type.GenericTypeTokenizer;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.token.tokens.factories.TokenFactory;
import com.ingsis.utils.type.types.Types;
import java.util.List;

public final class SecondTokenizerFactory implements TokenizerFactory {
    private final TokenFactory tokenFactory;
    private final ResultFactory resultFactory;

    public SecondTokenizerFactory(TokenFactory tokenFactory, ResultFactory resultFactory) {
        this.tokenFactory = tokenFactory;
        this.resultFactory = resultFactory;
    }

    @Override
    public Tokenizer createTokenizer() {
        return separatorTokenizer(
                operatorTokenizer(
                        keywordTokenizer(literalTokenizer(typeTokenizer(identifierTokenizer())))));
    }

    private Tokenizer identifierTokenizer() {
        TokenizersRegistry registry = new DefaultTokenizersRegistry();
        registry.registerTokenizer(new IdentifierTokenizer(tokenFactory, resultFactory));
        return registry;
    }

    private Tokenizer keywordTokenizer(Tokenizer nextTokenizer) {
        TokenizersRegistry registry = new DefaultTokenizersRegistry(nextTokenizer);
        registry.registerTokenizer(new LetKeywordTokenizer(tokenFactory, resultFactory));
        registry.registerTokenizer(new ConstKeywordTokenizer(tokenFactory, resultFactory));
        registry.registerTokenizer(new IfKeywordTokenizer(tokenFactory, resultFactory));
        registry.registerTokenizer(new ElseKeywordTokenizer(tokenFactory, resultFactory));
        return registry;
    }

    private Tokenizer literalTokenizer(Tokenizer nextTokenizer) {
        TokenizersRegistry registry = new DefaultTokenizersRegistry(nextTokenizer);
        registry.registerTokenizer(new StringLiteralTokenizer(tokenFactory, resultFactory));
        registry.registerTokenizer(new NumberLiteralTokenizer(tokenFactory, resultFactory));
        registry.registerTokenizer(new BooleanLiteralTokenizer(tokenFactory, resultFactory));
        return registry;
    }

    private Tokenizer operatorTokenizer(Tokenizer nextTokenizer) {
        TokenizersRegistry registry = new DefaultTokenizersRegistry(nextTokenizer);
        List<String> operators = List.of(":", "=", "+", "-", "/", "*");
        for (String operator : operators) {
            registry.registerTokenizer(
                    new GenericOperatorTokenizer(tokenFactory, operator, resultFactory));
        }
        return registry;
    }

    private Tokenizer separatorTokenizer(Tokenizer nextTokenizer) {
        TokenizersRegistry registry = new DefaultTokenizersRegistry(nextTokenizer);
        registry.registerTokenizer(new SpaceSeparatorTokenizer(tokenFactory, " ", resultFactory));
        registry.registerTokenizer(new SpaceSeparatorTokenizer(tokenFactory, "\t", resultFactory));
        registry.registerTokenizer(new SpaceSeparatorTokenizer(tokenFactory, "\f", resultFactory));
        registry.registerTokenizer(new SpaceSeparatorTokenizer(tokenFactory, "\r", resultFactory));
        registry.registerTokenizer(new SpaceSeparatorTokenizer(tokenFactory, "\n", resultFactory));
        List<String> separators = List.of(",", "(", ")", "[", "]", "{", "}");
        for (String separator : separators) {
            registry.registerTokenizer(
                    new GenericSeparatorTokenizer(tokenFactory, separator, resultFactory));
        }
        registry.registerTokenizer(new EndOfLineSeparatorTokenizer(tokenFactory, resultFactory));
        return registry;
    }

    private Tokenizer typeTokenizer(Tokenizer nextTokenizer) {
        TokenizersRegistry registry = new DefaultTokenizersRegistry(nextTokenizer);
        for (Types type : Types.values()) {
            registry.registerTokenizer(new GenericTypeTokenizer(tokenFactory, type, resultFactory));
        }
        return registry;
    }
}
