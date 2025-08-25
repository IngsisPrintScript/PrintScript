package lexer.tokenizers.keyword;

import common.factories.tokens.TokenFactory;
import common.responses.CorrectResult;
import common.responses.Result;
import lexer.tokenizers.FinalTokenizer;
import lexer.tokenizers.TokenizerInterface;

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
    public Result tokenize(String input) {
        return new CorrectResult<>(new TokenFactory().createPrintlnKeywordToken());
    }
}
