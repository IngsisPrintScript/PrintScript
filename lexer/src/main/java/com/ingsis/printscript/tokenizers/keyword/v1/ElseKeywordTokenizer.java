/*
 * My Project
 */

package com.ingsis.printscript.tokenizers.keyword.v1;

import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.tokenizers.FinalTokenizer;
import com.ingsis.printscript.tokenizers.TokenizerInterface;
import com.ingsis.printscript.tokenizers.keyword.KeywordTokenizer;
import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.tokens.factories.TokenFactory;

public class ElseKeywordTokenizer extends KeywordTokenizer {

    public ElseKeywordTokenizer() {
        super(new FinalTokenizer());
    }

    public ElseKeywordTokenizer(TokenizerInterface nextTokenizer) {
        super(nextTokenizer);
    }

    @Override
    public Boolean canTokenize(String input) {
        return input.equals("else");
    }

    @Override
    public Result<TokenInterface> tokenize(String input) {
        return new CorrectResult<>(new TokenFactory().createLetKeywordToken());
    }
}
