/*
 * My Project
 */

package com.ingsis.printscript.tokenizers.separator;

import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.tokens.factories.TokenFactory;
import com.ingsis.printscript.tokens.factories.TokenFactoryInterface;

public final class NewLineSeparatorTokenizer extends SeparatorTokenizer {
    private final TokenFactoryInterface TOKEN_FACTORY = new TokenFactory();

    @Override
    public Boolean canTokenize(String input) {
        return input.equals("\n");
    }

    @Override
    public Result<TokenInterface> tokenize(String input) {
        if (canTokenize(input)) {
            return new CorrectResult<>(TOKEN_FACTORY.createSeparatorToken("\n"));
        }
        return new IncorrectResult<>("This is not a new line separator.");
    }
}
