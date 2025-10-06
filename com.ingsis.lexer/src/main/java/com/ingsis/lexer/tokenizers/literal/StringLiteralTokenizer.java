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

public final class StringLiteralTokenizer implements Tokenizer {
    private final String regExPattern;
    private final TokenFactory tokenFactory;

    public StringLiteralTokenizer(TokenFactory tokenFactory) {
        this.regExPattern = "^(?:\\\"(?:[^\\\"\\\\]|\\\\.)*\\\"|'(?:[^'\\\\]|\\\\.)*')$";
        this.tokenFactory = tokenFactory;
    }

    private Boolean canTokenize(String input) {
        return input.matches(regExPattern);
    }

    @Override
    public Result<Token> tokenize(String input) {
        if (!canTokenize(input)) {
            return new IncorrectResult<>("Input is not a valid string: " + input);
        }
        return new CorrectResult<>(tokenFactory.createLiteralToken(input));
    }
}
