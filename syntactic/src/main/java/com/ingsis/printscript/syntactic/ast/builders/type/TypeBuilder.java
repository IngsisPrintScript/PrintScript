package com.ingsis.printscript.syntactic.ast.builders.type;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.astnodes.declaration.TypeNode;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.astnodes.factories.NodeFactory;
import com.ingsis.printscript.tokens.factories.TokenFactory;
import com.ingsis.printscript.syntactic.ast.builders.ASTreeBuilderInterface;
import com.ingsis.printscript.tokens.stream.TokenStreamInterface;

public record TypeBuilder() implements ASTreeBuilderInterface {
    private static final TokenInterface template = new TokenFactory().createTypeToken("placeholder");

    @Override
    public Boolean canBuild(TokenStreamInterface tokenStream) {
        Result<TokenInterface> peekResult = tokenStream.peek();
        if (!peekResult.isSuccessful()) return false;
        TokenInterface token = peekResult.result();
        return token.equals(template);
    }

    @Override
    public Result<TypeNode> build(TokenStreamInterface tokenStream) {
        if (!canBuild(tokenStream)){
            return new IncorrectResult<>("Cannot build type node.");
        }
        Result<TokenInterface> consumeResult = tokenStream.consume(template);
        if (!consumeResult.isSuccessful()) {
            return new IncorrectResult<>("Cannot build type node.");
        }
        TokenInterface token = consumeResult.result();
        Node typeNode = new NodeFactory().createTypeNode(token.value());
        return new CorrectResult<>((TypeNode) typeNode);
    }
}
