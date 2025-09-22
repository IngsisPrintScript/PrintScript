/*
 * My Project
 */

package com.ingsis.syntactic;

import com.ingsis.peekableiterator.PeekableIterator;
import com.ingsis.result.Result;
import com.ingsis.visitors.Checkable;

public interface SyntacticParser extends PeekableIterator<Checkable> {
    Result<Checkable> parse();
}
