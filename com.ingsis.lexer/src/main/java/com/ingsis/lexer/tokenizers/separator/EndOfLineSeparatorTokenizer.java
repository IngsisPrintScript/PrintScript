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

public final class EndOfLineSeparatorTokenizer implements Tokenizer {
    String template;
    private final TokenFactory tokenFactory;

    public EndOfLineSeparatorTokenizer(TokenFactory tokenFactory) {
        this.template = ";";
        this.tokenFactory = tokenFactory;
    }

    private Boolean canTokenize(String input) {
        return input.equals(template);
    }

    @Override
    public Result<Token> tokenize(String input) {
        if (!canTokenize(input)) {
            return new IncorrectResult<>("Input is not valid end of line: " + input);
        }
        return new CorrectResult<>(tokenFactory.createEndOfLineToken(input));
    }
}
