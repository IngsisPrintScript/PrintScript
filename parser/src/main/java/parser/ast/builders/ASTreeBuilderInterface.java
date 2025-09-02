package parser.ast.builders;

import common.Node;
import results.Result;
import stream.TokenStreamInterface;

public interface ASTreeBuilderInterface {
    Boolean canBuild(TokenStreamInterface tokenStream);

    Result<? extends Node> build(TokenStreamInterface tokenStream);
}
