/*
 * My Project
 */

package com.ingsis.lexer.tokenizers.type;

import com.ingsis.lexer.tokenizers.Tokenizer;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.token.tokens.Token;
import com.ingsis.utils.token.tokens.factories.TokenFactory;
import com.ingsis.utils.type.types.Types;

public final class GenericTypeTokenizer implements Tokenizer {
    private final Types type;
    private final TokenFactory tokenFactory;
    private final ResultFactory resultFactory;

    public GenericTypeTokenizer(
            TokenFactory tokenFactory, Types type, ResultFactory resultFactory) {
        this.type = type;
        this.tokenFactory = tokenFactory;
        this.resultFactory = resultFactory;
    }

    private boolean canTokenize(String input) {
        return type.keyword().equals(input);
    }

    @Override
    public Result<Token> tokenize(String input, Integer line, Integer column) {
        if (!canTokenize(input)) {
            return resultFactory.createIncorrectResult(
                    String.format("Unknown token on line:%d and column:%d", line, column));
        }
        return resultFactory.createCorrectResult(
                tokenFactory.createTypeToken(type.keyword(), line, column));
    }
}
