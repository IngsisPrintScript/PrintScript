package parser.ast.builders.ascription;


import common.TokenInterface;
import declaration.TypeNode;
import expression.ExpressionNode;
import expression.identifier.IdentifierNode;
import parser.ast.builders.identifier.IdentifierBuilder;
import parser.ast.builders.type.TypeBuilder;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;
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
        Result<TokenInterface> peekResult = tokenStream.peek(1);
        if (!peekResult.isSuccessful()) return false;
        TokenInterface token = peekResult.result();
        return token.equals(template);
    }

    @Override
    public Result<AscriptionNode> build(TokenStreamInterface tokenStream) {
        if (!canBuild(tokenStream)) {
            return new IncorrectResult<>("Cannot build identifier node.");
        }

        Result<ExpressionNode> buildIdentifierResult =
                ((IdentifierBuilder) builderFactory.createIdentifierBuilder()).build(tokenStream);
        if (!buildIdentifierResult.isSuccessful()) {
            return new IncorrectResult<>(buildIdentifierResult.errorMessage());
        }
        Result<TokenInterface> consumeResult = tokenStream.consume(template);
        if (!consumeResult.isSuccessful()) {
            return new IncorrectResult<>(consumeResult.errorMessage());
        }

        Result<TypeNode> buildTypeResult =
                ((TypeBuilder) builderFactory.createTypeBuilder()).build(tokenStream);

        if (!buildTypeResult.isSuccessful()) {
            return new IncorrectResult<>(buildTypeResult.errorMessage());
        }

        IdentifierNode identifierNode = (IdentifierNode) buildIdentifierResult.result();
        AscriptionNode ascriptionNode = (AscriptionNode) new NodeFactory().createAscriptionNode();
        TypeNode typeNode = buildTypeResult.result();

        ascriptionNode.setType(typeNode);
        ascriptionNode.setIdentifier(identifierNode);

        return new CorrectResult<>(ascriptionNode);
    }
}
