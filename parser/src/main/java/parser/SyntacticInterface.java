package parser;

import nodes.visitor.SemanticallyCheckable;
import results.Result;
import stream.TokenStreamInterface;

public interface SyntacticInterface {
  Result<SemanticallyCheckable> buildAbstractSyntaxTree(TokenStreamInterface tokenStream);
}
