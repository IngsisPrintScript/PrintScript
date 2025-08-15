package lexical.tokenizers.literal;

import common.factories.tokens.TokenFactory;
import common.responses.CorrectResult;
import common.responses.Result;
import lexical.tokenizers.TokenizerInterface;

public record LiteralTokenizer(TokenizerInterface nextTokenizer) implements TokenizerInterface {
    @Override
    public Boolean canTokenize(String input) {
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
    public Result tokenize(String input) {
        if (canTokenize(input)) {
            return new CorrectResult<>(new TokenFactory().createLiteralToken(input));
        } else {
            return nextTokenizer.tokenize(input);
        }
    }
}
