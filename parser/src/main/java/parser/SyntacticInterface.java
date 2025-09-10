package parser;

import common.PeekableIterator;
import results.Result;
import stream.TokenStreamInterface;
import visitor.SemanticallyCheckable;

public interface SyntacticInterface extends PeekableIterator<SemanticallyCheckable> {
    Result<SemanticallyCheckable> buildAbstractSyntaxTree(TokenStreamInterface tokenStream);
}
