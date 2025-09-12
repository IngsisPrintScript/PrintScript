package com.ingsis.printscript.semantic;


import com.ingsis.printscript.astnodes.visitor.InterpretableNode;
import com.ingsis.printscript.astnodes.visitor.SemanticallyCheckable;
import com.ingsis.printscript.peekableiterator.PeekableIterator;

public interface SemanticInterface extends PeekableIterator<InterpretableNode> {
    Boolean isSemanticallyValid(SemanticallyCheckable tree);
}
