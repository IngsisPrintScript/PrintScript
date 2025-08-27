package lexer.tokenizers.keyword;

import common.responses.CorrectResult;
import common.responses.Result;
import factories.tokens.TokenFactory;
import lexer.tokenizers.FinalTokenizer;
import lexer.tokenizers.TokenizerInterface;

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
    public Result tokenize(String input) {
        return new CorrectResult<>(new TokenFactory().createLetKeywordToken());
    }
}
