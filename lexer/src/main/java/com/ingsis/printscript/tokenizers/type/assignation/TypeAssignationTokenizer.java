package com.ingsis.printscript.tokenizers.type.assignation;

import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.tokens.factories.TokenFactory;
import com.ingsis.printscript.tokenizers.TokenizerInterface;

public record TypeAssignationTokenizer(TokenizerInterface nextTokenizer) implements TokenizerInterface {

    @Override
    public Boolean canTokenize(String input) {
        return input.equals(":");
    }

    @Override
    public Result<TokenInterface> tokenize(String input) {
        if (canTokenize(input)) {
            return new CorrectResult<>(new TokenFactory().createTypeAssignationToken());
        } else {
            return nextTokenizer.tokenize(input);
        }
    }
}
