package lexer.tokenizers.identifier;

import common.responses.CorrectResult;
import common.responses.Result;
import factories.tokens.TokenFactory;
import lexer.tokenizers.TokenizerInterface;

public record IdentifierTokenizer(TokenizerInterface nextTokenizer) implements TokenizerInterface {
    @Override
    public Boolean canTokenize(String input) {
        char[] chars = input.toCharArray();
        for (Character c : chars) {
            if (!Character.isLetter(c) || c == '_') {
                return false;
            }
        }
        return true;
    }

    @Override
    public Result tokenize(String input) {
        if (canTokenize(input)) {
            return new CorrectResult<>(new TokenFactory().createIdentifierToken(input));
        } else {
            return nextTokenizer().tokenize(input);
        }
    }
}
