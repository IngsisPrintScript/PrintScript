package parser;

import results.Result;
import stream.TokenStreamInterface;
import visitor.InterpretableNode;
import visitor.SemanticallyCheckable;

import java.util.Iterator;

public interface SyntacticInterface extends Iterator<InterpretableNode> {
    Result<InterpretableNode> buildAbstractSyntaxTree(TokenStreamInterface tokenStream);
}
