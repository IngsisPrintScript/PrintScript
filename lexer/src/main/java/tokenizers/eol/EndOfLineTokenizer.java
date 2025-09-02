package tokenizers.eol;

import common.TokenInterface;
import results.CorrectResult;
import results.Result;
import factories.tokens.TokenFactory;
import tokenizers.TokenizerInterface;

public record EndOfLineTokenizer(TokenizerInterface nextTokenizer) implements TokenizerInterface {
    @Override
    public Boolean canTokenize(String input) {
        return input.equals(";");
    }

    @Override
    public Result<TokenInterface> tokenize(String input) {
        if (canTokenize(input)) {
            return new CorrectResult<>(new TokenFactory().createEndOfLineToken());
        } else {
            return nextTokenizer.tokenize(input);
        }
    }
}
