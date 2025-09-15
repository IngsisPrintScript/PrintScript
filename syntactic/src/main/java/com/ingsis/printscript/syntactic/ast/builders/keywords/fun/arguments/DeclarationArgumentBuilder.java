package com.ingsis.printscript.syntactic.ast.builders.keywords.fun.arguments;

import com.ingsis.printscript.astnodes.declaration.TypeNode;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.statements.function.argument.DeclareArgumentNode;
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
import com.ingsis.printscript.tokens.factories.TokenFactoryInterface;
import com.ingsis.printscript.tokens.stream.TokenStreamInterface;

public class DeclarationArgumentBuilder implements ASTreeBuilderInterface {
    private final TokenInterface IDENTIFIER_TOKEN_TEMPLATE;
    private final TokenInterface ASCRIPTION_TOKEN_TEMPLATE;
    private final IdentifierBuilder IDENTIFIER_BUILDER;
    private final TypeBuilder TYPE_BUILDER;


    public DeclarationArgumentBuilder() {
        AstBuilderFactoryInterface BUILDER_FACTORY = new AstBuilderFactory();
        TokenFactoryInterface TOKEN_FACTORY = new TokenFactory();
        IDENTIFIER_TOKEN_TEMPLATE = TOKEN_FACTORY.createIdentifierToken("placeholder");
        ASCRIPTION_TOKEN_TEMPLATE = TOKEN_FACTORY.createTypeAssignationToken();
        IDENTIFIER_BUILDER = (IdentifierBuilder) BUILDER_FACTORY.createIdentifierBuilder();
        TYPE_BUILDER = (TypeBuilder) BUILDER_FACTORY.createTypeBuilder();
    }

    @Override
    public Boolean canBuild(TokenStreamInterface tokenStream) {
        Result<TokenInterface> peekResult = tokenStream.peek();
        if (!peekResult.isSuccessful()){
            return false;
        }
        TokenInterface token = peekResult.result();
        return token.equals(IDENTIFIER_TOKEN_TEMPLATE);
    }

    @Override
    public Result<DeclareArgumentNode> build(TokenStreamInterface tokenStream) {
        if (!canBuild(tokenStream)){
            return new IncorrectResult<>("This stream is not a declaration argument.");
        }

        Result<IdentifierNode> buildIdentifierResult = IDENTIFIER_BUILDER.build(tokenStream);
        if (!buildIdentifierResult.isSuccessful()){
            return new IncorrectResult<>("This stream is not a declaration argument.");
        }
        IdentifierNode identifier = buildIdentifierResult.result();

        if (!tokenStream.consume(ASCRIPTION_TOKEN_TEMPLATE).isSuccessful()){
            return new IncorrectResult<>("This stream is not a declaration argument.");
        }

        Result<TypeNode> buildTypeResult = TYPE_BUILDER.build(tokenStream);
        if (!buildTypeResult.isSuccessful()){
            return new IncorrectResult<>("This stream is not a declaration argument.");
        }
        TypeNode type = buildTypeResult.result();

        DeclareArgumentNode argumentNode = new DeclareArgumentNode(identifier, type);

        return new CorrectResult<>(argumentNode);
    }
}
