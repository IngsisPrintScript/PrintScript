package parser.ast.builders;

import nodes.common.Node;
import results.IncorrectResult;
import results.Result;
import stream.TokenStreamInterface;

public record FinalBuilder() implements ASTreeBuilderInterface {
    @Override
    public Boolean canBuild(TokenStreamInterface tokenStream) {
        return false;
    }

    @Override
    public Result<Node> build(TokenStreamInterface tokenStream) {
        return new IncorrectResult<>("There was no builder able to handle token stream " + tokenStream);
    }
}
