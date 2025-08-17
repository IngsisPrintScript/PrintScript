package syntactic.ast.builders.ascription;

import common.factories.nodes.NodeFactory;
import common.factories.tokens.TokenFactory;
import common.nodes.Node;
import common.nodes.declaration.DeclarationNode;
import common.responses.CorrectResult;
import common.responses.IncorrectResult;
import common.responses.Result;
import common.tokens.TokenInterface;
import common.tokens.stream.TokenStream;
import syntactic.ast.builders.ASTreeBuilderInterface;
import syntactic.ast.builders.identifier.IdentifierBuilder;
import syntactic.ast.builders.type.TypeBuilder;

public record AscriptionBuilder() implements ASTreeBuilderInterface {
    private static final TokenInterface template = new TokenFactory().createTypeAssignationToken();

    @Override
    public Boolean canBuild(TokenStream tokenStream) {
        Result peekResult = tokenStream.peek(1);
        if (!peekResult.isSuccessful()) return false;
        TokenInterface token = ( (CorrectResult<TokenInterface>) peekResult).newObject();
        return token.equals(template);
    }

    @Override
    public Result build(TokenStream tokenStream) {
        if (!canBuild(tokenStream)) return new IncorrectResult("Cannot build identifier node.");

        Result buildIdentifierResult = new IdentifierBuilder().build(tokenStream);
        if (!buildIdentifierResult.isSuccessful()) return buildIdentifierResult;

        if (!tokenStream.consume(template).isSuccessful()) return new IncorrectResult("Token is not an ascription token.");

        Result buildTypeResult = new TypeBuilder().build(tokenStream);
        if (!buildTypeResult.isSuccessful()) return buildTypeResult;

        Node identifierNode = ( (CorrectResult<Node>) buildIdentifierResult).newObject();
        DeclarationNode ascriptionNode = (DeclarationNode) new NodeFactory().createDeclarationNode();
        Node typeNode = ( (CorrectResult<Node>) buildTypeResult).newObject();

        ascriptionNode.addLeftChild(typeNode);
        ascriptionNode.addRightChild(identifierNode);

        return new CorrectResult<>(ascriptionNode);
    }
}
