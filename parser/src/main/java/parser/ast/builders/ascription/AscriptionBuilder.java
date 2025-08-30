package parser.ast.builders.ascription;


import common.Node;
import common.TokenInterface;
import responses.CorrectResult;
import responses.IncorrectResult;
import responses.Result;
import declaration.AscriptionNode;
import factories.NodeFactory;
import factories.tokens.TokenFactory;
import parser.ast.builders.ASTreeBuilderInterface;
import parser.factories.AstBuilderFactory;
import parser.factories.AstBuilderFactoryInterface;
import stream.TokenStreamInterface;

public record AscriptionBuilder() implements ASTreeBuilderInterface {
    private static final TokenInterface template = new TokenFactory().createTypeAssignationToken();
    private static final AstBuilderFactoryInterface builderFactory = new AstBuilderFactory();

    @Override
    public Boolean canBuild(TokenStreamInterface tokenStream) {
        Result peekResult = tokenStream.peek(1);
        if (!peekResult.isSuccessful()) return false;
        TokenInterface token = ((CorrectResult<TokenInterface>) peekResult).result();
        return token.equals(template);
    }

    @Override
    public Result build(TokenStreamInterface tokenStream) {
        if (!canBuild(tokenStream)) return new IncorrectResult("Cannot build identifier node.");

        Result buildIdentifierResult = builderFactory.createIdentifierBuilder().build(tokenStream);
        if (!buildIdentifierResult.isSuccessful()) return buildIdentifierResult;

        if (!tokenStream.consume(template).isSuccessful())
            return new IncorrectResult("Token is not an ascription token.");

        Result buildTypeResult = builderFactory.createTypeBuilder().build(tokenStream);
        if (!buildTypeResult.isSuccessful()) return buildTypeResult;

        Node identifierNode = ((CorrectResult<Node>) buildIdentifierResult).result();
        AscriptionNode ascriptionNode = (AscriptionNode) new NodeFactory().createAscriptionNode();
        Node typeNode = ((CorrectResult<Node>) buildTypeResult).result();

        ascriptionNode.setType(typeNode);
        ascriptionNode.setIdentifier(identifierNode);

        return new CorrectResult<>(ascriptionNode);
    }
}
