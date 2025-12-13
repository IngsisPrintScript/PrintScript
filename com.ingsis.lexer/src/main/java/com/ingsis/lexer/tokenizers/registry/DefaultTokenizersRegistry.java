/*
 * My Project
 */

package com.ingsis.lexer.tokenizers.registry;

import com.ingsis.lexer.TokenizeResult;
import com.ingsis.lexer.tokenizers.Tokenizer;
import com.ingsis.utils.metachar.string.builder.MetaCharStringBuilder;
import com.ingsis.utils.token.Token;
import java.util.ArrayList;
import java.util.List;

public final class DefaultTokenizersRegistry implements TokenizersRegistry {
    private final List<Tokenizer> tokenizers;

    public DefaultTokenizersRegistry(List<Tokenizer> tokenizers) {
        this.tokenizers = List.copyOf(tokenizers);
    }

    public DefaultTokenizersRegistry() {
        this(List.of());
    }

    @Override
    public TokenizeResult tokenize(MetaCharStringBuilder sb, List<Token> trailingTrivia) {
        TokenizeResult result = new TokenizeResult.INVALID();
        for (Tokenizer tokenizer : tokenizers) {
            result = result.comparePriority(tokenizer.tokenize(sb, trailingTrivia));
        }
        return result;
    }

    @Override
    public TokenizersRegistry registerTokenizer(Tokenizer tokenizer) {
        List<Tokenizer> newTokenizers = new ArrayList<>(this.tokenizers);
        newTokenizers.add(tokenizer);
        return new DefaultTokenizersRegistry(newTokenizers);
    }
}
