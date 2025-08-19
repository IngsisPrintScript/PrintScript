package lexer.tokenizers.type;

import common.factories.tokens.TokenFactory;
import common.responses.CorrectResult;
import common.responses.Result;
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
    public Result tokenize(String input) {
        return new CorrectResult<>(new TokenFactory().createTypeToken(input));
    }
}
