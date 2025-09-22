/*
 * My Project
 */

package com.ingsis.lexer.tokenizers.separator;

import com.ingsis.lexer.tokenizers.Tokenizer;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.tokens.Token;
import com.ingsis.tokens.factories.TokenFactory;

public final class SpaceSeparatorTokenizer implements Tokenizer {
    Character template;
    TokenFactory tokenFactory;

    public SpaceSeparatorTokenizer(TokenFactory tokenFactory, Character template) {
        this.template = template;
        this.tokenFactory = tokenFactory;
    }

    private Boolean canTokenize(String input) {
        for (char c : input.toCharArray()) {
            if (c != template) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Result<Token> tokenize(String input) {
        if (!canTokenize(input)) {
            return new IncorrectResult<>(
                    String.format("Input is not a whitespace separator '%s': %s", template, input));
        }
        return new CorrectResult<>(tokenFactory.createSeparatorToken(input));
    }
}
