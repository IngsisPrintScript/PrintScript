/*
 * My Project
 */

package com.ingsis.printscript.tokenizers.keyword;

import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.tokenizers.FinalTokenizer;
import com.ingsis.printscript.tokenizers.TokenizerInterface;
import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.tokens.factories.TokenFactory;

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
