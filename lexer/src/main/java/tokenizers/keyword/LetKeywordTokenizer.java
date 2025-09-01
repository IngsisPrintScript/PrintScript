package tokenizers.keyword;

import common.TokenInterface;
import results.CorrectResult;
import results.Result;
import factories.tokens.TokenFactory;
import tokenizers.FinalTokenizer;
import tokenizers.TokenizerInterface;

public final class LetKeywordTokenizer extends KeywordTokenizer {
    public LetKeywordTokenizer() {
        super(new FinalTokenizer());
    }
    public LetKeywordTokenizer(TokenizerInterface nextTokenizer) {
        super(nextTokenizer);
    }

    @Override
    public Boolean canTokenize(String input) {
        return input.equals("let");
    }

    @Override
    public Result<TokenInterface> tokenize(String input) {
        return new CorrectResult<>(new TokenFactory().createLetKeywordToken());
    }
}
