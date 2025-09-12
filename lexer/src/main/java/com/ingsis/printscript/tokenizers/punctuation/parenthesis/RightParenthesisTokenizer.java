package com.ingsis.printscript.tokenizers.punctuation.parenthesis;

import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.tokens.factories.TokenFactory;
import com.ingsis.printscript.tokenizers.punctuation.PunctuationTokenizer;

public class RightParenthesisTokenizer extends PunctuationTokenizer {
    
    @Override
    public Boolean canTokenize(String input) {
        return input.equals(")");
    }

    @Override
    public Result<TokenInterface> tokenize(String input) {
        if (!canTokenize(input)) {return new IncorrectResult<>("Cannot tokenize provided input");}
        return new CorrectResult<>(new TokenFactory().createRightParenthesisToken());
    }
}