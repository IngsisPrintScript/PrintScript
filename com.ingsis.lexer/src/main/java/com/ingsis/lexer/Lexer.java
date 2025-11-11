/*
 * My Project
 */

package com.ingsis.lexer;

import com.ingsis.metachar.string.builder.MetaCharStringBuilder;
import com.ingsis.peekableiterator.PeekableIterator;
import com.ingsis.result.Result;
import com.ingsis.tokens.Token;

public interface Lexer extends PeekableIterator<Token> {
    Result<Token> analyze(MetaCharStringBuilder stringBuilder);
}
