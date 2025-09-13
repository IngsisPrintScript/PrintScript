/*
 * My Project
 */

package com.ingsis.printscript.lexer;

import com.ingsis.printscript.peekableiterator.PeekableIterator;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.tokens.TokenInterface;

public interface LexicalInterface extends PeekableIterator<TokenInterface> {
    Result<TokenInterface> analyze(String word);
}
