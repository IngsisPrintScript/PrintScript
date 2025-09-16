/*
 * My Project
 */

package com.ingsis.printscript.semantic;

import com.ingsis.printscript.peekableiterator.PeekableIterator;
import com.ingsis.printscript.visitor.InterpretableNode;
import com.ingsis.printscript.visitor.SemanticallyCheckable;

public interface SemanticInterface extends PeekableIterator<InterpretableNode> {
    Boolean isSemanticallyValid(SemanticallyCheckable tree);
}
