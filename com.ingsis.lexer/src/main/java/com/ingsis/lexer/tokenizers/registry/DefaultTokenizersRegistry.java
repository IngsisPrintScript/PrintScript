/*
 * My Project
 */

package com.ingsis.lexer.tokenizers.registry;

import com.ingsis.lexer.tokenizers.Tokenizer;
import com.ingsis.utils.process.result.ProcessResult;
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
    public ProcessResult<Token> tokenize(String input, Integer line, Integer column) {
        ProcessResult<Token> bestResult = ProcessResult.INVALID();
        for (Tokenizer tokenizer : tokenizers) {
            ProcessResult<Token> tempResult = tokenizer.tokenize(input, line, column);
            switch (tempResult.status()) {
                case COMPLETE, PREFIX -> bestResult = tempResult.comparePriority(bestResult);
                case INVALID -> {}
            }
        }
        return bestResult;
    }

    @Override
    public TokenizersRegistry registerTokenizer(Tokenizer tokenizer) {
        List<Tokenizer> newTokenizers = new ArrayList<>(this.tokenizers);
        newTokenizers.add(tokenizer);
        return new DefaultTokenizersRegistry(newTokenizers);
    }
}
