package lexical.tokenizer.keyword;

import common.factories.tokens.TokenFactory;
import common.responses.CorrectResult;
import common.responses.Result;
import lexical.tokenizer.TokenizerInterface;

public final class LetKeywordTokenizer extends KeywordTokenizer {
    public LetKeywordTokenizer(TokenizerInterface nextTokenizer) {
        super(nextTokenizer);
    }

    @Override
    public Boolean canTokenize(String input) {
        return input.equals("let");
    }

    @Override
    public Result tokenize(String input) {
        return new CorrectResult<>(new TokenFactory().createTypeToken(input));
    }
}
