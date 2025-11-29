/*
 * My Project
 */

package com.ingsis.parser.syntactic;

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.peekableiterator.PeekableIterator;
import com.ingsis.utils.result.Result;

public interface SyntacticParser extends PeekableIterator<Checkable> {
    Result<? extends Node> parse();
}
