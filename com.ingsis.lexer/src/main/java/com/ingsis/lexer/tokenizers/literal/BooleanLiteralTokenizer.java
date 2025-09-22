/*
 * My Project
 */

package com.ingsis.lexer.tokenizers.literal;

import com.ingsis.lexer.tokenizers.Tokenizer;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.tokens.Token;
import com.ingsis.tokens.factories.TokenFactory;

public final class BooleanLiteralTokenizer implements Tokenizer {
    private final TokenFactory tokenFactory;

    public BooleanLiteralTokenizer(TokenFactory tokenFactory) {
        this.tokenFactory = tokenFactory;
    }

    private Boolean canTokenize(String input) {
        return input.equals("True") || input.equals("False");
    }

    @Override
    public Result<Token> tokenize(String input) {
        if (!canTokenize(input)) {
            new IncorrectResult<>("Input is not a valid boolean: " + input);
        }
        return new CorrectResult<>(tokenFactory.createLiteralToken(input));
    }
}
