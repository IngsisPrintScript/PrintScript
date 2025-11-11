/*
 * My Project
 */

package com.ingsis.lexer.tokenizers.type;

import com.ingsis.lexer.tokenizers.Tokenizer;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.tokens.Token;
import com.ingsis.tokens.factories.TokenFactory;
import com.ingsis.types.Types;

public final class GenericTypeTokenizer implements Tokenizer {
    Types type;
    TokenFactory tokenFactory;

    public GenericTypeTokenizer(TokenFactory tokenFactory, Types type) {
        this.type = type;
        this.tokenFactory = tokenFactory;
    }

    private boolean canTokenize(String input) {
        return type.keyword().equals(input);
    }

    @Override
    public Result<Token> tokenize(String input, Integer line, Integer column) {
        if (!canTokenize(input)) {
            return new IncorrectResult<>("Input is not type: " + type.keyword());
        }
        return new CorrectResult<>(tokenFactory.createTypeToken(type.keyword(), line, column));
    }
}
