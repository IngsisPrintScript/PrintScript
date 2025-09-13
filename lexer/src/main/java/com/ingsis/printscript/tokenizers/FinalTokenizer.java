/*
 * My Project
 */

package com.ingsis.printscript.tokenizers;

import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.tokens.TokenInterface;

public class FinalTokenizer implements TokenizerInterface {
    @Override
    public Boolean canTokenize(String input) {
        return false;
    }

    @Override
    public Result<TokenInterface> tokenize(String input) {
        return new IncorrectResult<>(
                "There was no tokenizer able to tokenize the string: " + input);
    }
}
