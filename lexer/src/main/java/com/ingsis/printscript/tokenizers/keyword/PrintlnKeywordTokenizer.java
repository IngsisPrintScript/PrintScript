package com.ingsis.printscript.tokenizers.keyword;

import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.tokens.factories.TokenFactory;
import com.ingsis.printscript.tokenizers.FinalTokenizer;
import com.ingsis.printscript.tokenizers.TokenizerInterface;

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
