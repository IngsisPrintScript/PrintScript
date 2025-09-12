package com.ingsis.printscript.lexer;

import com.ingsis.printscript.peekableiterator.PeekableIterator;
import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.results.Result;

public interface LexicalInterface extends PeekableIterator<TokenInterface> {
    Result<TokenInterface> analyze(String word);
}
