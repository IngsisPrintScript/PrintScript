/*
 * My Project
 */

package com.ingsis.lexer.tokenizers.literal;

import com.ingsis.lexer.tokenizers.Tokenizer;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.token.tokens.Token;
import com.ingsis.utils.token.tokens.factories.TokenFactory;
import com.ingsis.utils.type.types.Types;

public final class NumberLiteralTokenizer implements Tokenizer {
    private final String regExPattern;
    private final TokenFactory tokenFactory;
    private final ResultFactory resultFactory;

    public NumberLiteralTokenizer(TokenFactory tokenFactory, ResultFactory resultFactory) {
        this.regExPattern = Types.NUMBER.regEx();
        this.tokenFactory = tokenFactory;
        this.resultFactory = resultFactory;
    }

    private Boolean canTokenize(String input) {
        return input.matches(regExPattern);
    }

    @Override
    public Result<Token> tokenize(String input, Integer line, Integer column) {
        if (!canTokenize(input)) {
            return resultFactory.createIncorrectResult(
                    String.format("Unknown token on line:%d and column:%d", line, column));
        }
        return resultFactory.createCorrectResult(
                tokenFactory.createLiteralToken(input, line, column));
    }
}
