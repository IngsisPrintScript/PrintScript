package lexer.tokenizers.eol;

import common.factories.tokens.TokenFactory;
import common.responses.CorrectResult;
import common.responses.Result;
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
