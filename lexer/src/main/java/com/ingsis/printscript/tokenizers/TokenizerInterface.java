/*
 * My Project
 */

package com.ingsis.printscript.tokenizers;

import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.tokens.TokenInterface;

public interface TokenizerInterface {
    Boolean canTokenize(String input);

    Result<TokenInterface> tokenize(String input);
}
