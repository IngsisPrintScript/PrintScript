/*
 * My Project
 */

package com.ingsis.lexer;

import com.ingsis.utils.metachar.string.builder.MetaCharStringBuilder;
import com.ingsis.utils.peekableiterator.PeekableIterator;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.tokens.Token;

public interface Lexer extends PeekableIterator<Token> {
    Result<Token> analyze(MetaCharStringBuilder stringBuilder);
}
