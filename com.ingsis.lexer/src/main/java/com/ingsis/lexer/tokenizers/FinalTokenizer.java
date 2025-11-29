/*
 * My Project
 */

package com.ingsis.lexer.tokenizers;

import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.tokens.Token;

public final class FinalTokenizer implements Tokenizer {
    @Override
    public Result<Token> tokenize(String input, Integer line, Integer column) {
        return new IncorrectResult<>(
                String.format("Unkwon token on line: %d and column: %d", line, column));
    }
}
