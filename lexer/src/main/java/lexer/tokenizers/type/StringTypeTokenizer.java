package lexer.tokenizers.type;


import responses.CorrectResult;
import responses.Result;
import factories.tokens.TokenFactory;
import lexer.tokenizers.FinalTokenizer;
import lexer.tokenizers.TokenizerInterface;

public final class StringTypeTokenizer extends TypeTokenizer {
    public StringTypeTokenizer() {
        super(new FinalTokenizer());
    }
    public StringTypeTokenizer(TokenizerInterface nextTokenizer) {
        super(nextTokenizer);
    }

    @Override
    public Boolean canTokenize(String input) {
        return input.equals("String");
    }

    @Override
    public Result tokenize(String input) {
        return new CorrectResult<>(new TokenFactory().createTypeToken(input));
    }
}
