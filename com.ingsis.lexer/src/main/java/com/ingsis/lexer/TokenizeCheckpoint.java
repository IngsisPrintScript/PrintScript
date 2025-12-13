/*
 * My Project
 */

package com.ingsis.lexer;

import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.metachar.MetaChar;
import com.ingsis.utils.token.Token;

public sealed interface TokenizeCheckpoint {
    public record INITIALIZED(Token token, SafeIterator<MetaChar> nextIterator)
            implements TokenizeCheckpoint {}

    public record UNINITIALIZED() implements TokenizeCheckpoint {}
}
