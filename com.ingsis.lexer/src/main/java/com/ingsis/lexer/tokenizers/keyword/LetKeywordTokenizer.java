/*
 * My Project
 */

package com.ingsis.lexer.tokenizers.keyword;

import com.ingsis.lexer.tokenizers.Tokenizer;
import com.ingsis.result.Result;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.tokens.Token;
import com.ingsis.tokens.factories.TokenFactory;

public final class LetKeywordTokenizer implements Tokenizer {
    private final String template;
    private final TokenFactory tokenFactory;
    private final ResultFactory resultFactory;

    public LetKeywordTokenizer(TokenFactory tokenFactory, ResultFactory resultFactory) {
        this.template = "let";
        this.tokenFactory = tokenFactory;
        this.resultFactory = resultFactory;
    }

    private Boolean canTokenize(String input) {
        return input.equals(template);
    }

    @Override
    public Result<Token> tokenize(String input, Integer line, Integer column) {
        if (!canTokenize(input)) {
            return resultFactory.createIncorrectResult(
                    String.format("Unknown token on line:%d and column:%d", line, column));
        }
        return resultFactory.createCorrectResult(
                tokenFactory.createKeywordToken(input, line, column));
    }
}
