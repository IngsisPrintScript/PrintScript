package com.ingsis.printscript.tokenizers.literal;

import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.tokens.factories.TokenFactory;
import com.ingsis.printscript.tokenizers.TokenizerInterface;

public record LiteralTokenizer(TokenizerInterface nextTokenizer) implements TokenizerInterface {
    @Override
    public Boolean canTokenize(String input) {
        char[] tokenChars = input.toCharArray();
        if (input.startsWith("'") && input.endsWith("'")) {
            return input.length() >=2;
        } else if (input.startsWith("\"") && input.endsWith("\"")) {
            return input.length() >=2;
        }

        try{
            if (input.contains("+") ||  input.contains("-")) {
                return false;
            }
            Double.parseDouble(input);
        } catch(Exception ignore){
            return false;
        }

        return true;
    }

    @Override
    public Result<TokenInterface> tokenize(String input) {
        if (canTokenize(input)) {
            return new CorrectResult<>(new TokenFactory().createLiteralToken(input));
        } else {
            return nextTokenizer.tokenize(input);
        }
    }
}
