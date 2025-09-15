/*
 * My Project
 */

package com.ingsis.printscript.syntactic.ast.builders.keywords;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.astnodes.declaration.AscriptionNode;
import com.ingsis.printscript.astnodes.expression.ExpressionNode;
import com.ingsis.printscript.astnodes.factories.NodeFactory;
import com.ingsis.printscript.astnodes.statements.LetStatementNode;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.syntactic.ast.builders.ascription.AscriptionBuilder;
import com.ingsis.printscript.syntactic.ast.builders.expression.ExpressionBuilder;
import com.ingsis.printscript.syntactic.factories.AstBuilderFactory;
import com.ingsis.printscript.syntactic.factories.AstBuilderFactoryInterface;
import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.tokens.factories.TokenFactory;
import com.ingsis.printscript.tokens.stream.TokenStreamInterface;

public final class LetBuilder extends KeywordBuilder {
    private static final TokenInterface LET_TOKEN_TEMPLATE =
            new TokenFactory().createLetKeywordToken();
    private static final TokenInterface ASSIGNATION_TOKEN_TEMPLATE =
            new TokenFactory().createAssignationToken();
    private static final TokenInterface EOL_TOKEN_TEMPLATE =
            new TokenFactory().createEndOfLineToken();
    private static final AstBuilderFactoryInterface BUILDER_FACTORY = new AstBuilderFactory();

    @Override
    public Boolean canBuild(TokenStreamInterface tokenStream) {
        Result<TokenInterface> peekResult = tokenStream.peek();
        if (!peekResult.isSuccessful()) return false;
        TokenInterface token = peekResult.result();
        return token.equals(LET_TOKEN_TEMPLATE);
    }

    @Override
    public Result<? extends Node> build(TokenStreamInterface tokenStream) {
        if (!canBuild(tokenStream)) {
            return new IncorrectResult<>("That isn't a let keyword.");
        }

        tokenStream.consume(LET_TOKEN_TEMPLATE);

        Result<AscriptionNode> buildAscritionResult =
                ((AscriptionBuilder) BUILDER_FACTORY.createAscriptionBuilder()).build(tokenStream);
        if (!buildAscritionResult.isSuccessful()) {
            return new IncorrectResult<>("Cannot build let ast.");
        }

        LetStatementNode letNode = (LetStatementNode) new NodeFactory().createLetStatementNode();
        letNode.setAscription(buildAscritionResult.result());

        if (tokenStream.consume(ASSIGNATION_TOKEN_TEMPLATE).isSuccessful()) {
            Result<? extends ExpressionNode> buildLiteralResult =
                    ((ExpressionBuilder) BUILDER_FACTORY.createExpressionBuilder())
                            .build(tokenStream);
            if (!buildLiteralResult.isSuccessful()) return buildLiteralResult;
            ExpressionNode expressionNode = buildLiteralResult.result();
            letNode.setExpression(expressionNode);
        }

        if (!tokenStream.consume(new TokenFactory().createEndOfLineToken()).isSuccessful()) {
            return new IncorrectResult<>("Did not have an End Of Line character.");
        }

        return new CorrectResult<>(letNode);
    }
}
