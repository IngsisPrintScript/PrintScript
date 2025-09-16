/*
 * My Project
 */

package com.ingsis.printscript.syntactic;

import com.ingsis.printscript.astnodes.visitor.SemanticallyCheckable;
import com.ingsis.printscript.peekableiterator.PeekableIterator;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.tokens.stream.TokenStreamInterface;

public interface SyntacticInterface extends PeekableIterator<SemanticallyCheckable> {
    Result<SemanticallyCheckable> buildAbstractSyntaxTree(TokenStreamInterface tokenStream);
}
