/*
 * My Project
 */

package com.ingsis.printscript.syntactic.ast.builders.expression;

import com.ingsis.printscript.astnodes.expression.ExpressionNode;
import com.ingsis.printscript.astnodes.expression.binary.BinaryExpression;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.expression.literal.LiteralNode;
import com.ingsis.printscript.astnodes.factories.NodeFactory;
import com.ingsis.printscript.astnodes.factories.NodeFactoryInterface;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.syntactic.ast.builders.expression.operators.binary.BinaryOperatorBuilder;
import com.ingsis.printscript.syntactic.factories.AstBuilderFactory;
import com.ingsis.printscript.syntactic.factories.AstBuilderFactoryInterface;
import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.tokens.factories.TokenFactory;
import com.ingsis.printscript.tokens.stream.TokenStreamInterface;

public class BinaryExpressionBuilder extends ExpressionBuilder {
    private final AstBuilderFactoryInterface BUILDER_FACTORY = new AstBuilderFactory();
    private final NodeFactoryInterface NODE_FACTORY = new NodeFactory();
    private final TokenInterface LITERAL_TEMPLATE =
            new TokenFactory().createLiteralToken("placeholder");
    private final TokenInterface IDENTIFIER_TEMPLATE =
            new TokenFactory().createIdentifierToken("placeholder");

    @Override
    public Boolean canBuild(TokenStreamInterface tokenStream) {
        Result<TokenInterface> peekResult = tokenStream.peek();
        if (!peekResult.isSuccessful()) return false;
        TokenInterface token = peekResult.result();
        return token.equals(LITERAL_TEMPLATE) || token.equals(IDENTIFIER_TEMPLATE);
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
        if (consumedToken.equals(LITERAL_TEMPLATE)) {
            expressionLeftChild =
                    (LiteralNode) NODE_FACTORY.createLiteralNode(consumedToken.value());
        } else {
            expressionLeftChild =
                    (IdentifierNode) NODE_FACTORY.createIdentifierNode(consumedToken.value());
        }

        Result<BinaryExpression> buildOperatorResult =
                ((BinaryOperatorBuilder) BUILDER_FACTORY.createOperatorBuilder())
                        .build(tokenStream);

        if (buildOperatorResult.isSuccessful()) {
            BinaryExpression root = buildOperatorResult.result();
            root.setLeftChild(expressionLeftChild);

            Result<? extends ExpressionNode> buildRightChildResult =
                    ((ExpressionBuilder) BUILDER_FACTORY.createExpressionBuilder())
                            .build(tokenStream);

            if (!buildRightChildResult.isSuccessful()) {
                return new IncorrectResult<>(buildRightChildResult.errorMessage());
            }

            ExpressionNode rightChild = buildRightChildResult.result();
            root.setRightChild(rightChild);

            return new CorrectResult<>(root);
        }

        return new CorrectResult<>(expressionLeftChild);
    }
}
