package parser.ast.builders.identifier;

import common.Node;
import common.TokenInterface;
import common.responses.CorrectResult;
import common.responses.IncorrectResult;
import common.responses.Result;
import factories.NodeFactory;
import factories.tokens.TokenFactory;
import parser.ast.builders.ASTreeBuilderInterface;
import stream.TokenStreamInterface;

public record IdentifierBuilder() implements ASTreeBuilderInterface {
    private static final TokenInterface template = new TokenFactory().createIdentifierToken("placeholder");

    @Override
    public Boolean canBuild(TokenStreamInterface tokenStream) {
        Result peekResult = tokenStream.peek();
        if (!peekResult.isSuccessful()) return false;
        TokenInterface token = ((CorrectResult<TokenInterface>) peekResult).newObject();
        return token.equals(template);
    }

    @Override
    public Result build(TokenStreamInterface tokenStream) {
        if (!canBuild(tokenStream)) return new IncorrectResult("Cannot build identifier node.");
        Result consumeResult = tokenStream.consume(template);
        if (!consumeResult.isSuccessful()) return consumeResult;
        TokenInterface token = ((CorrectResult<TokenInterface>) consumeResult).newObject();
        Node identifierNode = new NodeFactory().createIdentifierNode(token.value());
        return new CorrectResult<>(identifierNode);
    }
}
