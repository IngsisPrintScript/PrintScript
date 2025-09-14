package com.ingsis.printscript.tokenizers.keyword;

import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.tokens.factories.TokenFactory;
import com.ingsis.printscript.tokens.factories.TokenFactoryInterface;

public class FunKeywordTokenizer extends KeywordTokenizer {
    private final TokenFactoryInterface TOKEN_FACTORY;

    public FunKeywordTokenizer() {
        super();
        this.TOKEN_FACTORY = new TokenFactory();
    }

    @Override
    public Boolean canTokenize(String input) {
        return input.equals("fun");
    }
    @Override
    public Result<TokenInterface> tokenize(String input) {
        if (!canTokenize(input)) {
            return new IncorrectResult<>("Can't tokenize input.");
        }
        return new CorrectResult<>(TOKEN_FACTORY.createKeywordToken("FUN", "fun"));
    }

}
