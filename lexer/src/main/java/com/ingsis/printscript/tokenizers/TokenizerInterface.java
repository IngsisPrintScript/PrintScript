package com.ingsis.printscript.tokenizers;

import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.results.Result;

public interface TokenizerInterface {
    Boolean canTokenize(String input);
    Result<TokenInterface> tokenize(String input);
}
