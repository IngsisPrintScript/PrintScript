package parser;

import responses.Result;
import stream.TokenStreamInterface;
import visitor.SemanticallyCheckable;

public interface SyntacticInterface {
    Result<SemanticallyCheckable> buildAbstractSyntaxTree(TokenStreamInterface tokenStream);
}
