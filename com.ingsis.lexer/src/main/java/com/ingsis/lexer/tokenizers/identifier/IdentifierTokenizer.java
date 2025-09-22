/*
 * My Project
 */

package com.ingsis.lexer.tokenizers.identifier;

import com.ingsis.lexer.tokenizers.Tokenizer;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.tokens.Token;
import com.ingsis.tokens.factories.TokenFactory;

public final class IdentifierTokenizer implements Tokenizer {
    String regExPattern;
    TokenFactory tokenFactory;

    public IdentifierTokenizer(TokenFactory tokenFactory) {
        this.regExPattern = "^[A-Za-z_][A-Za-z0-9_]*$";
        this.tokenFactory = tokenFactory;
    }

    private Boolean canTokenize(String input) {
        return input.matches(regExPattern);
    }

    @Override
    public Result<Token> tokenize(String input) {
        if (!canTokenize(input)) {
            return new IncorrectResult<>("Invalid identifier: " + input);
        }
        return new CorrectResult<>(tokenFactory.createIdentifierToken(input));
    }
}
