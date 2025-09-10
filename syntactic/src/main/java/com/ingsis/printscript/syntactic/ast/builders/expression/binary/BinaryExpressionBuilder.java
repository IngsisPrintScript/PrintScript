package com.ingsis.printscript.syntactic.ast.builders.expression.binary;


import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.astnodes.expression.ExpressionNode;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.expression.literal.LiteralNode;
import com.ingsis.printscript.syntactic.ast.builders.expression.ExpressionBuilder;
import com.ingsis.printscript.syntactic.ast.builders.expression.binary.operators.BinaryOperatorBuilder;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.astnodes.expression.binary.BinaryExpression;
import com.ingsis.printscript.astnodes.factories.NodeFactory;
import com.ingsis.printscript.astnodes.factories.NodeFactoryInterface;
import com.ingsis.printscript.tokens.factories.TokenFactory;
import com.ingsis.printscript.syntactic.factories.AstBuilderFactory;
import com.ingsis.printscript.syntactic.factories.AstBuilderFactoryInterface;
import com.ingsis.printscript.tokens.stream.TokenStreamInterface;

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
            return new IncorrectResult<>(consumeResult.errorMessage());
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
                return new IncorrectResult<>(buildOperatorResult.errorMessage());
            }

            ExpressionNode rightChild = buildRightChildResult.result();
            root.setRightChild(rightChild);

            return new CorrectResult<>(root);
        }

        return new CorrectResult<>(expressionLeftChild);
    }
}
