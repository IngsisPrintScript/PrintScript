package com.ingsis.printscript.syntactic.ast.builders;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.tokens.stream.TokenStreamInterface;

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
