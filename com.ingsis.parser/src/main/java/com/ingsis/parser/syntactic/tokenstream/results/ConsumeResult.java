/*
 * My Project
 */

package com.ingsis.parser.syntactic.tokenstream.results;

import com.ingsis.parser.syntactic.tokenstream.TokenStream;
import com.ingsis.utils.token.Token;

public sealed interface ConsumeResult {
    public record CORRECT(Token consumedToken, TokenStream finalTokenStream)
            implements ConsumeResult {}

    public record INCORRECT(String error) implements ConsumeResult {}
}
