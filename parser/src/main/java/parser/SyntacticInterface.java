package parser;

import common.Node;
import results.Result;
import stream.TokenStreamInterface;
import visitor.SemanticallyCheckable;

import java.util.Iterator;

public interface SyntacticInterface extends Iterator<Node> {
    Result<SemanticallyCheckable> buildAbstractSyntaxTree(TokenStreamInterface tokenStream);
}
