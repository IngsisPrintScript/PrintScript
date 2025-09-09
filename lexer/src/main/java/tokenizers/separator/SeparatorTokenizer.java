package tokenizers.separator;

import common.TokenInterface;
import factories.tokens.TokenFactory;
import results.CorrectResult;
import results.Result;
import tokenizers.TokenizerInterface;

public record SeparatorTokenizer(TokenizerInterface nextTokenizer) implements TokenizerInterface {
    @Override
    public Boolean canTokenize(String input) {
        return input.matches("[\\\\s]+");
    }

    @Override
    public Result<TokenInterface> tokenize(String input) {
        if (this.canTokenize(input)) {
            return new CorrectResult<>(new TokenFactory().createSeparatorToken("SEPARATOR"));
        }
        return nextTokenizer.tokenize(input);
    }
}
