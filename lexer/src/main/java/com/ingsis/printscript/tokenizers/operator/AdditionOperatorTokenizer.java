package com.ingsis.printscript.tokenizers.operator;

import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.tokens.factories.TokenFactory;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.tokenizers.FinalTokenizer;
import com.ingsis.printscript.tokenizers.TokenizerInterface;

public final class AdditionOperatorTokenizer extends OperatorTokenizer {
    public AdditionOperatorTokenizer() {
        super(new FinalTokenizer());
    }
    public AdditionOperatorTokenizer(TokenizerInterface nextTokenizer) {
        super(nextTokenizer);
    }

    @Override
    public Boolean canTokenize(String input) {
        return input.equals("+");
    }

    @Override
    public Result<TokenInterface> tokenize(String input) {
        return new CorrectResult<>(new TokenFactory().createAdditionToken());
    }
}
