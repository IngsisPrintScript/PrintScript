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
import com.ingsis.types.Types;

public final class NumberLiteralTokenizer implements Tokenizer {
    private final String regExPattern;
    private final TokenFactory tokenFactory;

    public NumberLiteralTokenizer(TokenFactory tokenFactory) {
        this.regExPattern = Types.NUMBER.regEx();
        this.tokenFactory = tokenFactory;
    }

    private Boolean canTokenize(String input) {
        return input.matches(regExPattern);
    }

    @Override
    public Result<Token> tokenize(String input) {
        if (!canTokenize(input)) {
            return new IncorrectResult<>("Input was invalid number: " + input);
        }
        return new CorrectResult<>(tokenFactory.createLiteralToken(input));
    }
}
