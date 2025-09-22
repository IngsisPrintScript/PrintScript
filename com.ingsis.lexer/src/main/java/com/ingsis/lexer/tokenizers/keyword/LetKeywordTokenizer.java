/*
 * My Project
 */

package com.ingsis.lexer.tokenizers.keyword;

import com.ingsis.lexer.tokenizers.Tokenizer;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.tokens.Token;
import com.ingsis.tokens.factories.TokenFactory;

public final class LetKeywordTokenizer implements Tokenizer {
    String template;
    TokenFactory tokenFactory;

    public LetKeywordTokenizer(TokenFactory tokenFactory) {
        this.template = "let";
        this.tokenFactory = tokenFactory;
    }

    private Boolean canTokenize(String input) {
        return input.equals(template);
    }

    @Override
    public Result<Token> tokenize(String input) {
        if (!canTokenize(input)) {
            return new IncorrectResult<>("Input is different from let: " + input);
        }
        return new CorrectResult<>(tokenFactory.createKeywordToken(input));
    }
}
