/*
 * My Project
 */

package com.ingsis.printscript.syntactic.ast.builders.ascription;

import com.ingsis.printscript.astnodes.declaration.AscriptionNode;
import com.ingsis.printscript.astnodes.declaration.TypeNode;
import com.ingsis.printscript.astnodes.expression.ExpressionNode;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.factories.NodeFactory;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.syntactic.ast.builders.ASTreeBuilderInterface;
import com.ingsis.printscript.syntactic.ast.builders.expression.IdentifierBuilder;
import com.ingsis.printscript.syntactic.ast.builders.type.TypeBuilder;
import com.ingsis.printscript.syntactic.factories.AstBuilderFactory;
import com.ingsis.printscript.syntactic.factories.AstBuilderFactoryInterface;
import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.tokens.factories.TokenFactory;
import com.ingsis.printscript.tokens.stream.TokenStreamInterface;

public record AscriptionBuilder() implements ASTreeBuilderInterface {
    private static final TokenInterface TEMPLATE = new TokenFactory().createTypeAssignationToken();
    private static final AstBuilderFactoryInterface BUILDER_FACTORY = new AstBuilderFactory();

    @Override
    public Boolean canBuild(TokenStreamInterface tokenStream) {
        Result<TokenInterface> peekResult = tokenStream.peek(1);
        if (!peekResult.isSuccessful()) return false;
        TokenInterface token = peekResult.result();
        return token.equals(TEMPLATE);
    }

    @Override
    public Result<AscriptionNode> build(TokenStreamInterface tokenStream) {
        if (!canBuild(tokenStream)) {
            return new IncorrectResult<>("Cannot build identifier node.");
        }

        Result<ExpressionNode> buildIdentifierResult =
                ((IdentifierBuilder) BUILDER_FACTORY.createIdentifierBuilder()).build(tokenStream);
        if (!buildIdentifierResult.isSuccessful()) {
            return new IncorrectResult<>(buildIdentifierResult.errorMessage());
        }
        Result<TokenInterface> consumeResult = tokenStream.consume(TEMPLATE);
        if (!consumeResult.isSuccessful()) {
            return new IncorrectResult<>(consumeResult.errorMessage());
        }

        Result<TypeNode> buildTypeResult =
                ((TypeBuilder) BUILDER_FACTORY.createTypeBuilder()).build(tokenStream);

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
