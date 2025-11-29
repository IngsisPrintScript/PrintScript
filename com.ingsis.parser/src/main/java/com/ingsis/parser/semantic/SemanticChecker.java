/*
 * My Project
 */

package com.ingsis.parser.semantic;

import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.peekableiterator.PeekableIterator;
import com.ingsis.utils.result.Result;

public interface SemanticChecker extends PeekableIterator<Interpretable> {
    Result<Interpretable> parse();
}
