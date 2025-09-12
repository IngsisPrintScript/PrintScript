package com.ingsis.printscript.tokenizers.separator;

import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.tokens.factories.TokenFactory;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.tokenizers.TokenizerInterface;

public record SeparatorTokenizer(TokenizerInterface nextTokenizer) implements TokenizerInterface {
    @Override
    public Boolean canTokenize(String input) {
        return input.matches("\\s+");
    }

    @Override
    public Result<TokenInterface> tokenize(String input) {
        if (this.canTokenize(input)) {
            return new CorrectResult<>(new TokenFactory().createSeparatorToken("SEPARATOR"));
        }
        return nextTokenizer.tokenize(input);
    }
}
