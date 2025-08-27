package parser.ast.builders;

import responses.IncorrectResult;
import responses.Result;
import stream.TokenStreamInterface;

public record FinalBuilder() implements ASTreeBuilderInterface {
    @Override
    public Boolean canBuild(TokenStreamInterface tokenStream) {
        return false;
    }

    @Override
    public Result build(TokenStreamInterface tokenStream) {
        return new IncorrectResult("There was no builder able to handle that token stream.");
    }
}
