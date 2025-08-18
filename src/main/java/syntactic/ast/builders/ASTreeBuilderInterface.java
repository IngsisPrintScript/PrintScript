package syntactic.ast.builders;

import common.responses.Result;
import common.tokens.stream.TokenStreamInterface;

public interface ASTreeBuilderInterface {
    Boolean canBuild(TokenStreamInterface tokenStream);
    Result build(TokenStreamInterface tokenStream);
}
