/*
 * My Project
 */

package com.ingsis.printscript.syntactic.ast.builders.keywords;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.astnodes.expression.ExpressionNode;
import com.ingsis.printscript.astnodes.factories.NodeFactory;
import com.ingsis.printscript.astnodes.statements.PrintStatementNode;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.syntactic.ast.builders.expression.ExpressionBuilder;
import com.ingsis.printscript.syntactic.factories.AstBuilderFactory;
import com.ingsis.printscript.syntactic.factories.AstBuilderFactoryInterface;
import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.tokens.factories.TokenFactory;
import com.ingsis.printscript.tokens.stream.TokenStreamInterface;

public final class PrintBuilder extends KeywordBuilder {
    private static final AstBuilderFactoryInterface BUILDER_FACTORY = new AstBuilderFactory();
    private static final TokenInterface PRINT_TEMPLATE =
            new TokenFactory().createPrintlnKeywordToken();
    private static final TokenInterface LEFT_PARENTHESIS_TEMPLATE =
            new TokenFactory().createLeftParenthesisToken();
    private static final TokenInterface RIGHT_PARENTHESIS_TEMPLATE =
            new TokenFactory().createRightParenthesisToken();

    @Override
    public Boolean canBuild(TokenStreamInterface tokenStream) {
        Result<TokenInterface> peekResult = tokenStream.peek();
        if (!peekResult.isSuccessful()) {
            return false;
        }
        TokenInterface token = peekResult.result();
        return token.equals(PRINT_TEMPLATE);
    }

    @Override
    public Result<? extends Node> build(TokenStreamInterface tokenStream) {
        if (!canBuild(tokenStream)) {
            return new IncorrectResult<>("That isn't a println keyword.");
        }

        Result<TokenInterface> consumeResult = tokenStream.consume(PRINT_TEMPLATE);
        if (!consumeResult.isSuccessful()) {
            return new IncorrectResult<>(consumeResult.errorMessage());
        }
        PrintStatementNode root =
                (PrintStatementNode) new NodeFactory().createPrintlnStatementNode();

        consumeResult = tokenStream.consume(LEFT_PARENTHESIS_TEMPLATE);
        if (!consumeResult.isSuccessful()) {
            return new IncorrectResult<>(consumeResult.errorMessage());
        }

        Result<ExpressionNode> buildExpressionResult =
                ((ExpressionBuilder) BUILDER_FACTORY.createExpressionBuilder()).build(tokenStream);
        if (!buildExpressionResult.isSuccessful()) {
            return buildExpressionResult;
        }
        ExpressionNode expression = buildExpressionResult.result();
        root.setExpression(expression);

        consumeResult = tokenStream.consume(RIGHT_PARENTHESIS_TEMPLATE);
        if (!consumeResult.isSuccessful()) {
            return new IncorrectResult<>(consumeResult.errorMessage());
        }
        return new CorrectResult<>(root);
    }
}
