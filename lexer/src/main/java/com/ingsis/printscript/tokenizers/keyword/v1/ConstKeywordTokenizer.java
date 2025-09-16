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

public class ConstKeywordTokenizer extends KeywordTokenizer {

    public ConstKeywordTokenizer() {
        super(new FinalTokenizer());
    }

    public ConstKeywordTokenizer(TokenizerInterface nextTokenizer) {
        super(nextTokenizer);
    }

    @Override
    public Boolean canTokenize(String input) {
        return input.equals("const");
    }

    @Override
    public Result<TokenInterface> tokenize(String input) {
        return new CorrectResult<>(new TokenFactory().createLetKeywordToken());
    }
}
