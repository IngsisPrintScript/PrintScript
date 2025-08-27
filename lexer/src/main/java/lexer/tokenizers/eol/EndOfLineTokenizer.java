package lexer.tokenizers.eol;

import responses.CorrectResult;
import responses.Result;
import factories.tokens.TokenFactory;
import lexer.tokenizers.TokenizerInterface;

public record EndOfLineTokenizer(TokenizerInterface nextTokenizer) implements TokenizerInterface {
    @Override
    public Boolean canTokenize(String input) {
        return input.equals(";");
    }

    @Override
    public Result tokenize(String input) {
        if (canTokenize(input)) {
            return new CorrectResult<>(new TokenFactory().createEndOfLineToken());
        } else {
            return nextTokenizer.tokenize(input);
        }
    }
}
