package tokenizers.identifier;

import common.TokenInterface;
import results.CorrectResult;
import results.Result;
import factories.tokens.TokenFactory;
import tokenizers.TokenizerInterface;

public record IdentifierTokenizer(TokenizerInterface nextTokenizer) implements TokenizerInterface {
    @Override
    public Boolean canTokenize(String input) {
        char[] chars = input.toCharArray();
        for (Character c : chars) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Result<TokenInterface> tokenize(String input) {
        if (canTokenize(input)) {
            return new CorrectResult<>(new TokenFactory().createIdentifierToken(input));
        } else {
            return nextTokenizer().tokenize(input);
        }
    }
}
