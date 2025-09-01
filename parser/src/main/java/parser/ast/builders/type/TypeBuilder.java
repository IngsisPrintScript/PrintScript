package parser.ast.builders.type;

import common.Node;
import common.TokenInterface;
import declaration.TypeNode;
import responses.CorrectResult;
import responses.IncorrectResult;
import responses.Result;
import factories.NodeFactory;
import factories.tokens.TokenFactory;
import parser.ast.builders.ASTreeBuilderInterface;
import stream.TokenStreamInterface;

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
