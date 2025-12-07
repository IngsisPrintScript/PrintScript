/*
 * My Project
 */

package com.ingsis.lexer.tokenizers;

import com.ingsis.utils.process.result.ProcessResult;
import com.ingsis.utils.token.Token;

public final class FinalTokenizer implements Tokenizer {
    @Override
    public ProcessResult<Token> tokenize(String input, Integer line, Integer column) {
        return ProcessResult.INVALID();
    }
}
