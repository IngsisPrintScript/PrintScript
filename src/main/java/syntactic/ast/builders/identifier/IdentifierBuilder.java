package syntactic.ast.builders.identifier;

import common.factories.nodes.NodeFactory;
import common.factories.tokens.TokenFactory;
import common.nodes.Node;
import common.responses.CorrectResult;
import common.responses.IncorrectResult;
import common.responses.Result;
import common.tokens.TokenInterface;
import common.tokens.stream.TokenStream;
import syntactic.ast.builders.ASTreeBuilderInterface;

public record IdentifierBuilder() implements ASTreeBuilderInterface {
    private static final TokenInterface template = new TokenFactory().createIdentifierToken("placeholder");

    @Override
    public Boolean canBuild(TokenStream tokenStream) {
        Result peekResult = tokenStream.peek();
        if (!peekResult.isSuccessful()) return false;
        TokenInterface token = ( (CorrectResult<TokenInterface>) peekResult).newObject();
        return token.equals(template);
    }

    @Override
    public Result build(TokenStream tokenStream) {
        if (!canBuild(tokenStream)) return new IncorrectResult("Cannot build identifier node.");
        Result consumeResult = tokenStream.consume(template);
        if (!consumeResult.isSuccessful()) return consumeResult;
        TokenInterface token = ( (CorrectResult<TokenInterface>) consumeResult).newObject();
        Node identifierNode = new NodeFactory().createIdentifierNode(token.value());
        return new CorrectResult<>(identifierNode);
    }
}
