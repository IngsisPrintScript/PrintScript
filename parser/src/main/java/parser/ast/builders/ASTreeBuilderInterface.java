package parser.ast.builders;

import responses.Result;
import stream.TokenStreamInterface;

public interface ASTreeBuilderInterface {
    Boolean canBuild(TokenStreamInterface tokenStream);

    Result build(TokenStreamInterface tokenStream);
}
