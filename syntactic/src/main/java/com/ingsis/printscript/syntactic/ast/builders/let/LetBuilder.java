package com.ingsis.printscript.syntactic.ast.builders.let;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.astnodes.declaration.AscriptionNode;
import com.ingsis.printscript.astnodes.expression.ExpressionNode;
import com.ingsis.printscript.syntactic.ast.builders.ascription.AscriptionBuilder;
import com.ingsis.printscript.syntactic.ast.builders.expression.ExpressionBuilder;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.astnodes.factories.NodeFactory;
import com.ingsis.printscript.tokens.factories.TokenFactory;
import com.ingsis.printscript.syntactic.ast.builders.ASTreeBuilderInterface;
import com.ingsis.printscript.syntactic.factories.AstBuilderFactory;
import com.ingsis.printscript.syntactic.factories.AstBuilderFactoryInterface;
import com.ingsis.printscript.astnodes.statements.LetStatementNode;
import com.ingsis.printscript.tokens.stream.TokenStreamInterface;

public record LetBuilder(ASTreeBuilderInterface nextBuilder) implements ASTreeBuilderInterface {
    final private static TokenInterface letTokenTemplate = new TokenFactory().createLetKeywordToken();
    final private static TokenInterface assignationTokenTemplate = new TokenFactory().createAssignationToken();
    final private static TokenInterface eolTokenTemplate = new TokenFactory().createEndOfLineToken();
    private static final AstBuilderFactoryInterface builderFactory = new AstBuilderFactory();


    public LetBuilder() {
        this(builderFactory.createFinalBuilder());
    }

    @Override
    public Boolean canBuild(TokenStreamInterface tokenStream) {
        Result<TokenInterface> peekResult = tokenStream.peek();
        if (!peekResult.isSuccessful()) return false;
        TokenInterface token = peekResult.result();
        return token.equals(letTokenTemplate);
    }

    @Override
    public Result<? extends Node> build(TokenStreamInterface tokenStream) {
        if (!canBuild(tokenStream)) {
            return nextBuilder().build(tokenStream);
        }

        if (!tokenStream.consume(letTokenTemplate).isSuccessful()) {
            return nextBuilder().build(tokenStream);
        }

        Result<AscriptionNode> buildAscritionResult =
                ((AscriptionBuilder) builderFactory.createAscriptionBuilder()).build(tokenStream);
        if (!buildAscritionResult.isSuccessful()) {
            return new IncorrectResult<>("Cannot build let ast.");
        }

        LetStatementNode letNode = (LetStatementNode) new NodeFactory().createLetStatementNode();
        letNode.setAscription(buildAscritionResult.result());

        if (tokenStream.consume(assignationTokenTemplate).isSuccessful()) {
            Result<ExpressionNode> buildLiteralResult =
                    ((ExpressionBuilder) builderFactory.createExpressionBuilder()).build(tokenStream);
            if (!buildLiteralResult.isSuccessful()) return buildLiteralResult;
            ExpressionNode expressionNode = buildLiteralResult.result();
            letNode.setExpression(expressionNode);
        }

        return new CorrectResult<>(letNode);
    }
}
