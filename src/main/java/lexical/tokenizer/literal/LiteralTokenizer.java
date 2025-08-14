package lexical.tokenizer.literal;

import common.factories.tokens.TokenFactory;
import common.responses.CorrectResult;
import common.responses.Result;
import lexical.tokenizer.TokenizerInterface;

public record LiteralTokenizer(TokenizerInterface nextTokenizer) implements TokenizerInterface {
    @Override
    public Boolean canTokenize(String token) {
        char[] tokenChars = token.toCharArray();
        if (token.startsWith("'") && token.endsWith("'")) {
            return true;
        } else if (token.startsWith("\"") && token.endsWith("\"")) {
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
    public Result tokenize(String token) {
        if (canTokenize(token)) {
            return new CorrectResult<>(new TokenFactory().createLiteralToken(token));
        } else {
            return nextTokenizer.tokenize(token);
        }
    }
}
