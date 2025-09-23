/*
 * My Project
 */

package com.ingsis.semantic;

import com.ingsis.peekableiterator.PeekableIterator;
import com.ingsis.result.Result;
import com.ingsis.visitors.Interpretable;

public interface SemanticParser extends PeekableIterator<Interpretable> {
    Result<Interpretable> parse();
}
