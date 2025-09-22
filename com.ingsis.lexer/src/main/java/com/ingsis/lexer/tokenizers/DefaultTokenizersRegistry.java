/*
 * My Project
 */

package com.ingsis.lexer.tokenizers;

import com.ingsis.result.Result;
import com.ingsis.tokens.Token;
import java.util.ArrayList;

public final class DefaultTokenizersRegistry implements TokenizersRegistry {
    private final Tokenizer nextTokenizer;
    private final ArrayList<Tokenizer> tokenizers;

    public DefaultTokenizersRegistry(Tokenizer nextTokenizer) {
        this.nextTokenizer = nextTokenizer;
        this.tokenizers = new ArrayList<>();
    }

    public DefaultTokenizersRegistry() {
        this(new FinalTokenizer());
    }

    @Override
    public Result<Token> tokenize(String input) {
        for (Tokenizer tokenizer : tokenizers) {
            Result<Token> result = tokenizer.tokenize(input);
            if (result.isCorrect()) {
                return result;
            }
        }
        return nextTokenizer.tokenize(input);
    }

    @Override
    public void registerTokenizer(Tokenizer tokenizer) {
        tokenizers.add(tokenizer);
    }
}
