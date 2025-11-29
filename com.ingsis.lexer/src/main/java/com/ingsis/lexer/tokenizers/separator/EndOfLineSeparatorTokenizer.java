/*
 * My Project
 */

package com.ingsis.lexer.tokenizers.separator;

import com.ingsis.lexer.tokenizers.Tokenizer;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.token.tokens.Token;
import com.ingsis.utils.token.tokens.factories.TokenFactory;

public final class EndOfLineSeparatorTokenizer implements Tokenizer {
    private final String template;
    private final TokenFactory tokenFactory;
    private final ResultFactory resultFactory;

    public EndOfLineSeparatorTokenizer(TokenFactory tokenFactory, ResultFactory resultFactory) {
        this.template = ";";
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
                tokenFactory.createEndOfLineToken(input, line, column));
    }
}
