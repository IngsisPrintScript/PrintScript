package com.ingsis.printscript.tokenizers.identifier;

import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.tokens.factories.TokenFactory;
import com.ingsis.printscript.tokenizers.TokenizerInterface;

public record IdentifierTokenizer(TokenizerInterface nextTokenizer) implements TokenizerInterface {
    @Override
    public Boolean canTokenize(String input) {
        char[] chars = input.toCharArray();
        for (Character c : chars) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Result<TokenInterface> tokenize(String input) {
        if (canTokenize(input)) {
            return new CorrectResult<>(new TokenFactory().createIdentifierToken(input));
        } else {
            return nextTokenizer().tokenize(input);
        }
    }
}
