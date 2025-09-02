package tokenizers.literal;

import common.TokenInterface;
import results.CorrectResult;
import results.Result;
import factories.tokens.TokenFactory;
import tokenizers.TokenizerInterface;

public record LiteralTokenizer(TokenizerInterface nextTokenizer) implements TokenizerInterface {
    @Override
    public Boolean canTokenize(String input) {
        char[] tokenChars = input.toCharArray();
        if (input.startsWith("'") && input.endsWith("'")) {
            return input.length() >=2;
        } else if (input.startsWith("\"") && input.endsWith("\"")) {
            return input.length() >=2;
        }

        for(Character c : tokenChars){
            if(!Character.isDigit(c)){
                return false;
            }
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
