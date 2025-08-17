package syntactic.ast.builders;

import common.responses.IncorrectResult;
import common.responses.Result;
import common.tokens.stream.TokenStream;

public record FinalBuilder() implements ASTreeBuilderInterface {
    @Override
    public Boolean canBuild(TokenStream tokenStream) {
        return false;
    }

    @Override
    public Result build(TokenStream tokenStream) {
        return new IncorrectResult("There was no builder able to handle that token stream.");
    }
}
