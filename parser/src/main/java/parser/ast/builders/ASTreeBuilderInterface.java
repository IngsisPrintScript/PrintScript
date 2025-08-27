package parser.ast.builders;

import common.responses.Result;
import stream.TokenStreamInterface;

public interface ASTreeBuilderInterface {
    Boolean canBuild(TokenStreamInterface tokenStream);

    Result build(TokenStreamInterface tokenStream);
}
