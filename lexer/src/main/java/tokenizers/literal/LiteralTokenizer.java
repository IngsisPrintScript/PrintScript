package tokenizers.literal;

import common.TokenInterface;
import responses.CorrectResult;
import responses.Result;
import factories.tokens.TokenFactory;
import tokenizers.TokenizerInterface;

public record LiteralTokenizer(TokenizerInterface nextTokenizer) implements TokenizerInterface {
    @Override
    public Boolean canTokenize(String input) {
        if (input.length() <2) return false;
        char[] tokenChars = input.toCharArray();
        if (input.startsWith("'") && input.endsWith("'")) {
            return true;
        } else if (input.startsWith("\"") && input.endsWith("\"")) {
            return true;
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
