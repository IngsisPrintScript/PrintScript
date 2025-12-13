/*
 * My Project
 */

package com.ingsis.parser.syntactic;

import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.token.Token;

public sealed interface ParseCheckpoint {
    public record INITIALIZED(Checkable checkable, SafeIterator<Token> tokenIterator)
            implements ParseCheckpoint {}

    public record UNINITIALIZED() implements ParseCheckpoint {}
}
