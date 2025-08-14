package lexical.tokenizer.type;

import common.factories.tokens.TokenFactory;
import common.responses.CorrectResult;
import common.responses.Result;
import lexical.tokenizer.TokenizerInterface;

public final class StringTypeTokenizer extends TypeTokenizer {
    public StringTypeTokenizer(TokenizerInterface nextTokenizer) {
        super(nextTokenizer);
    }

    @Override
    public Boolean canTokenize(String token) {
        return token.equals("String");
    }

    @Override
    public Result tokenize(String token) {
        return new CorrectResult<>(new TokenFactory().createTypeToken(token));
    }
}
