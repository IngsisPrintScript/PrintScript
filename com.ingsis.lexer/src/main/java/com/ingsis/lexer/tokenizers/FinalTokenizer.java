/*
 * My Project
 */

package com.ingsis.lexer.tokenizers;

import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.tokens.Token;

public final class FinalTokenizer implements Tokenizer {
    @Override
    public Result<Token> tokenize(String input) {
        return new IncorrectResult<>(
                "There was no tokenizer able to tokenize that input: " + input);
    }
}
