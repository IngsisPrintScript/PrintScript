package com.ingsis.printscript.tokenizers.type;


import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.tokens.factories.TokenFactory;
import com.ingsis.printscript.tokenizers.FinalTokenizer;
import com.ingsis.printscript.tokenizers.TokenizerInterface;

public final class StringTypeTokenizer extends TypeTokenizer {
    public StringTypeTokenizer() {
        super(new FinalTokenizer());
    }
    public StringTypeTokenizer(TokenizerInterface nextTokenizer) {
        super(nextTokenizer);
    }

    @Override
    public Boolean canTokenize(String input) {
        return input.equals("String");
    }

    @Override
    public Result<TokenInterface> tokenize(String input) {
        return new CorrectResult<>(new TokenFactory().createTypeToken(input));
    }
}
