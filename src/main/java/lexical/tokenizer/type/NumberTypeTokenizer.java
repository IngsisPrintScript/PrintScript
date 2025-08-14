package lexical.tokenizer.type;

import common.factories.tokens.TokenFactory;
import common.responses.CorrectResult;
import common.responses.Result;
import lexical.tokenizer.TokenizerInterface;

public final class NumberTypeTokenizer extends TypeTokenizer {
    public NumberTypeTokenizer(TokenizerInterface nextTokenizer) {
        super(nextTokenizer);
    }

    @Override
    public Boolean canTokenize(String token) {
        return token.equals("Number");
    }

    @Override
    public Result tokenize(String token) {
        return new CorrectResult<>(new TokenFactory().createTypeToken(token));
    }
}
