package parser.ast.builders.expression.binary;


import common.TokenInterface;
import nodes.expression.ExpressionNode;
import nodes.expression.identifier.IdentifierNode;
import nodes.expression.literal.LiteralNode;
import parser.ast.builders.expression.ExpressionBuilder;
import parser.ast.builders.expression.binary.operators.BinaryOperatorBuilder;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;
import nodes.expression.binary.BinaryExpression;
import nodes.factories.NodeFactory;
import nodes.factories.NodeFactoryInterface;
import factories.tokens.TokenFactory;
import parser.factories.AstBuilderFactory;
import parser.factories.AstBuilderFactoryInterface;
import stream.TokenStreamInterface;

public class BinaryExpressionBuilder extends ExpressionBuilder {
    private final AstBuilderFactoryInterface builderFactory = new AstBuilderFactory();
    private final NodeFactoryInterface nodeFactory = new NodeFactory();
    private final TokenInterface literalTemplate = new TokenFactory().createLiteralToken("placeholder");
    private final TokenInterface identifierTemplate = new TokenFactory().createIdentifierToken("placeholder");

    @Override
    public Boolean canBuild(TokenStreamInterface tokenStream) {
        Result<TokenInterface> peekResult = tokenStream.peek();
        if (!peekResult.isSuccessful()) return false;
        TokenInterface token = peekResult.result();
        return token.equals(literalTemplate) || token.equals(identifierTemplate);
    }

    @Override
    public Result<ExpressionNode> build(TokenStreamInterface tokenStream) {
        if (!canBuild(tokenStream)) {
            return new IncorrectResult<>("Cannot build expression.");
        }

        Result<TokenInterface> consumeResult = tokenStream.consume();
        if (!consumeResult.isSuccessful()) {
            return new IncorrectResult<>("Cannot consume id or var token.");
        }
        TokenInterface consumedToken = consumeResult.result();

        ExpressionNode expressionLeftChild;
        if (consumedToken.equals(literalTemplate)) {
            expressionLeftChild = (LiteralNode) nodeFactory.createLiteralNode(consumedToken.value());
        } else {
            expressionLeftChild = (IdentifierNode) nodeFactory.createIdentifierNode(consumedToken.value());
        }

        Result<BinaryExpression> buildOperatorResult =
                ((BinaryOperatorBuilder) builderFactory.createOperatorBuilder()).build(tokenStream);

        if (buildOperatorResult.isSuccessful()) {
            BinaryExpression root = buildOperatorResult.result();
            root.setLeftChild(expressionLeftChild);

            Result<ExpressionNode> buildRightChildResult =
                    ((ExpressionBuilder) builderFactory.createExpressionBuilder()).build(tokenStream);

            if (!buildRightChildResult.isSuccessful()) {
                return new IncorrectResult<>("Cannot build expression.");
            }

            ExpressionNode rightChild = buildRightChildResult.result();
            root.setRightChild(rightChild);

            return new CorrectResult<>(root);
        }

        return new CorrectResult<>(expressionLeftChild);
    }
}
