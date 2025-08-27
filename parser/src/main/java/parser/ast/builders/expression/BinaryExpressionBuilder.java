package parser.ast.builders.expression;


import common.Node;
import common.TokenInterface;
import common.responses.CorrectResult;
import common.responses.IncorrectResult;
import common.responses.Result;
import expression.binary.BinaryExpression;
import factories.NodeFactory;
import factories.NodeFactoryInterface;
import factories.tokens.TokenFactory;
import parser.ast.builders.ASTreeBuilderInterface;
import parser.factories.AstBuilderFactory;
import parser.factories.AstBuilderFactoryInterface;
import stream.TokenStreamInterface;

public class BinaryExpressionBuilder implements ASTreeBuilderInterface {
    private final AstBuilderFactoryInterface builderFactory = new AstBuilderFactory();
    private final NodeFactoryInterface nodeFactory = new NodeFactory();
    private final TokenInterface literalTemplate = new TokenFactory().createLiteralToken("placeholder");
    private final TokenInterface identifierTemplate = new TokenFactory().createIdentifierToken("placeholder");

    @Override
    public Boolean canBuild(TokenStreamInterface tokenStream) {
        Result peekResult = tokenStream.peek();
        if (!peekResult.isSuccessful()) return false;
        TokenInterface token = ((CorrectResult<TokenInterface>) peekResult).newObject();
        return token.equals(literalTemplate) || token.equals(identifierTemplate);
    }

    @Override
    public Result build(TokenStreamInterface tokenStream) {
        if (!canBuild(tokenStream)) return new IncorrectResult("Cannot build expression.");

        Result consumeResult = tokenStream.consume();
        if (!consumeResult.isSuccessful()) {
            return new IncorrectResult("Cannot consume id or var token.");
        }
        TokenInterface consumedToken = ((CorrectResult<TokenInterface>) consumeResult).newObject();

        Node expressionLeftChild;
        if (consumedToken.equals(literalTemplate)) {
            expressionLeftChild = nodeFactory.createLiteralNode(consumedToken.value());
        } else {
            expressionLeftChild = nodeFactory.createIdentifierNode(consumedToken.value());
        }

        Result buildOperatorResult = builderFactory.createOperatorBuilder().build(tokenStream);

        if (buildOperatorResult.isSuccessful()) {
            BinaryExpression root = ((CorrectResult<BinaryExpression>) buildOperatorResult).newObject();
            root.addLeftChild(expressionLeftChild);
            Result buildRightChildResult = this.build(tokenStream);
            if (!buildRightChildResult.isSuccessful()) {
                return buildRightChildResult;
            }
            Node rightChild = ((CorrectResult<Node>) buildRightChildResult).newObject();
            root.addRightChild(rightChild);
            return new CorrectResult<>(root);
        }

        return new CorrectResult<>(expressionLeftChild);
    }
}
