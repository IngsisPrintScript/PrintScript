package lexer.tokenizers.type;

import common.TokenInterface;
import responses.CorrectResult;
import responses.Result;
import factories.tokens.TokenFactory;
import lexer.tokenizers.FinalTokenizer;
import lexer.tokenizers.TokenizerInterface;

public final class NumberTypeTokenizer extends TypeTokenizer {
    public NumberTypeTokenizer() {
        super(new FinalTokenizer());
    }
    public NumberTypeTokenizer(TokenizerInterface nextTokenizer) {
        super(nextTokenizer);
    }

    @Override
    public Boolean canTokenize(String input) {
        return input.equals("Number");
    }

    @Override
    public Result<TokenInterface> tokenize(String input) {
        return new CorrectResult<>(new TokenFactory().createTypeToken(input));
    }
}
