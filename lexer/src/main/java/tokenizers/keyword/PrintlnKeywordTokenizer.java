package tokenizers.keyword;

import common.TokenInterface;
import results.CorrectResult;
import results.Result;
import factories.tokens.TokenFactory;
import tokenizers.FinalTokenizer;
import tokenizers.TokenizerInterface;

public final class PrintlnKeywordTokenizer extends KeywordTokenizer {
    public PrintlnKeywordTokenizer() {
        super(new FinalTokenizer());
    }
    public PrintlnKeywordTokenizer(TokenizerInterface nextTokenizer) {
        super(nextTokenizer);
    }

    @Override
    public Boolean canTokenize(String input) {
        return input.equals("println");
    }

    @Override
    public Result<TokenInterface> tokenize(String input) {
        return new CorrectResult<>(new TokenFactory().createPrintlnKeywordToken());
    }
}
