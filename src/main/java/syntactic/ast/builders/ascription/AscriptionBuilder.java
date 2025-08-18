package syntactic.ast.builders.ascription;

import common.factories.nodes.NodeFactory;
import common.factories.tokens.TokenFactory;
import common.nodes.Node;
import common.nodes.declaration.DeclarationNode;
import common.responses.CorrectResult;
import common.responses.IncorrectResult;
import common.responses.Result;
import common.tokens.TokenInterface;
import common.tokens.stream.TokenStreamInterface;
import syntactic.ast.builders.ASTreeBuilderInterface;
import syntactic.factories.builders.AstBuilderFactory;
import syntactic.factories.builders.AstBuilderFactoryInterface;

public record AscriptionBuilder() implements ASTreeBuilderInterface {
    private static final TokenInterface template = new TokenFactory().createTypeAssignationToken();
    private static final AstBuilderFactoryInterface builderFactory = new AstBuilderFactory();

    @Override
    public Boolean canBuild(TokenStreamInterface tokenStream) {
        Result peekResult = tokenStream.peek(1);
        if (!peekResult.isSuccessful()) return false;
        TokenInterface token = ( (CorrectResult<TokenInterface>) peekResult).newObject();
        return token.equals(template);
    }

    @Override
    public Result build(TokenStreamInterface tokenStream) {
        if (!canBuild(tokenStream)) return new IncorrectResult("Cannot build identifier node.");

        Result buildIdentifierResult = builderFactory.createIdentifierBuilder().build(tokenStream);
        if (!buildIdentifierResult.isSuccessful()) return buildIdentifierResult;

        if (!tokenStream.consume(template).isSuccessful()) return new IncorrectResult("Token is not an ascription token.");

        Result buildTypeResult = builderFactory.createTypeBuilder().build(tokenStream);
        if (!buildTypeResult.isSuccessful()) return buildTypeResult;

        Node identifierNode = ( (CorrectResult<Node>) buildIdentifierResult).newObject();
        DeclarationNode ascriptionNode = (DeclarationNode) new NodeFactory().createDeclarationNode();
        Node typeNode = ( (CorrectResult<Node>) buildTypeResult).newObject();

        ascriptionNode.addLeftChild(typeNode);
        ascriptionNode.addRightChild(identifierNode);

        return new CorrectResult<>(ascriptionNode);
    }
}
