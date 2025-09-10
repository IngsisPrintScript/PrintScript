package com.ingsis.printscript.syntactic;

import com.ingsis.printscript.peekableiterator.PeekableIterator;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.tokens.stream.TokenStreamInterface;
import com.ingsis.printscript.astnodes.visitor.SemanticallyCheckable;

public interface SyntacticInterface extends PeekableIterator<SemanticallyCheckable> {
    Result<SemanticallyCheckable> buildAbstractSyntaxTree(TokenStreamInterface tokenStream);
}
