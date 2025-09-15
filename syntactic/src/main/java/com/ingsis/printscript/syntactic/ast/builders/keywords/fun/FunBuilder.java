/*
 * My Project
 */

package com.ingsis.printscript.syntactic.ast.builders.keywords.fun;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.astnodes.declaration.TypeNode;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.statements.function.DeclareFunctionNode;
import com.ingsis.printscript.astnodes.statements.function.argument.DeclarationArgumentNode;
import com.ingsis.printscript.astnodes.visitor.InterpretableNode;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.syntactic.ast.builders.ASTreeBuilderInterface;
import com.ingsis.printscript.syntactic.ast.builders.cor.ChainBuilderInterface;
import com.ingsis.printscript.syntactic.ast.builders.cor.NodeBuilderChain;
import com.ingsis.printscript.syntactic.ast.builders.expression.IdentifierBuilder;
import com.ingsis.printscript.syntactic.ast.builders.keywords.KeywordBuilder;
import com.ingsis.printscript.syntactic.ast.builders.keywords.fun.arguments.DeclarationArgumentBuilder;
import com.ingsis.printscript.syntactic.ast.builders.type.TypeBuilder;
import com.ingsis.printscript.syntactic.factories.AstBuilderFactory;
import com.ingsis.printscript.syntactic.factories.AstBuilderFactoryInterface;
import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.tokens.factories.TokenFactory;
import com.ingsis.printscript.tokens.factories.TokenFactoryInterface;
import com.ingsis.printscript.tokens.stream.TokenStreamInterface;
import java.util.ArrayList;
import java.util.Collection;

public final class FunBuilder extends KeywordBuilder {
    private final TokenInterface FUN_TOKEN_TEMPLATE;
    private final TokenInterface LEFT_PARENTHESIS_TOKEN_TEMPLATE;
    private final TokenInterface RIGHT_PARENTHESIS_TOKEN_TEMPLATE;
    private final TokenInterface LEFT_BRACE_TOKEN_TEMPLATE;
    private final TokenInterface RIGHT_BRACE_TOKEN_TEMPLATE;
    private final TokenInterface ASCRIPTION_TOKEN_TEMPLATE;
    private final TokenInterface COMMA_TOKEN_TEMPLATE;
    private final DeclarationArgumentBuilder DECLARATION_ARGUMENT_BUILDER;
    private final IdentifierBuilder IDENTIFIER_BUILDER;
    private final TypeBuilder TYPE_BUILDER;
    private final ASTreeBuilderInterface CHAIN_BUILDER;

    public FunBuilder() {
        TokenFactoryInterface tokenFactory = new TokenFactory();
        FUN_TOKEN_TEMPLATE = tokenFactory.createKeywordToken("FUN", "fun");
        LEFT_PARENTHESIS_TOKEN_TEMPLATE = tokenFactory.createLeftParenthesisToken();
        RIGHT_PARENTHESIS_TOKEN_TEMPLATE = tokenFactory.createRightParenthesisToken();
        LEFT_BRACE_TOKEN_TEMPLATE = tokenFactory.createPunctuationToken("LEFT_BRACE", "{");
        RIGHT_BRACE_TOKEN_TEMPLATE = tokenFactory.createPunctuationToken("RIGHT_BRACE", "}");
        ASCRIPTION_TOKEN_TEMPLATE = tokenFactory.createTypeAssignationToken();
        COMMA_TOKEN_TEMPLATE = tokenFactory.createPunctuationToken("COMMA", ",");
        AstBuilderFactoryInterface builderFactory = new AstBuilderFactory();
        DECLARATION_ARGUMENT_BUILDER =
                (DeclarationArgumentBuilder) builderFactory.createDeclarationArgumentBuilder();
        IDENTIFIER_BUILDER = (IdentifierBuilder) builderFactory.createIdentifierBuilder();
        TYPE_BUILDER = (TypeBuilder) builderFactory.createTypeBuilder();
        ChainBuilderInterface chainBuilder = new NodeBuilderChain();
        CHAIN_BUILDER = chainBuilder.createDefaultChain();
    }

    @Override
    public Boolean canBuild(TokenStreamInterface tokenStream) {
        Result<TokenInterface> peekResult = tokenStream.peek();
        if (!peekResult.isSuccessful()) {
            return false;
        }
        TokenInterface token = peekResult.result();
        return token.equals(FUN_TOKEN_TEMPLATE);
    }

    @Override
    public Result<DeclareFunctionNode> build(TokenStreamInterface tokenStream) {
        tokenStream.consume(FUN_TOKEN_TEMPLATE);

        Result<IdentifierNode> buildfunctionIdentifierResult =
                IDENTIFIER_BUILDER.build(tokenStream);
        if (!buildfunctionIdentifierResult.isSuccessful()) {
            return new IncorrectResult<>("This stream is not a function declaration.");
        }
        IdentifierNode functionIdentifierNode = buildfunctionIdentifierResult.result();

        Result<Collection<DeclarationArgumentNode>> getArguments = getArgumentsSection(tokenStream);
        if (!getArguments.isSuccessful()) {
            return new IncorrectResult<>(getArguments.errorMessage());
        }
        Collection<DeclarationArgumentNode> arguments = getArguments.result();

        if (!tokenStream.consume(ASCRIPTION_TOKEN_TEMPLATE).isSuccessful()) {
            return new IncorrectResult<>("This stream is not a function declaration.");
        }

        Result<TypeNode> buildTypeResult = TYPE_BUILDER.build(tokenStream);
        if (!buildTypeResult.isSuccessful()) {
            return new IncorrectResult<>("This stream is not a function declaration.");
        }
        TypeNode returnTypeNode = buildTypeResult.result();

        if (!tokenStream.consume(LEFT_BRACE_TOKEN_TEMPLATE).isSuccessful()) {
            return new IncorrectResult<>("This stream is not a function declaration.");
        }

        Result<Collection<InterpretableNode>> getBodyResult = getBody(tokenStream);
        if (!getBodyResult.isSuccessful()) {
            return new IncorrectResult<>(getBodyResult.errorMessage());
        }
        Collection<InterpretableNode> body = getBodyResult.result();

        if (!tokenStream.consume(RIGHT_BRACE_TOKEN_TEMPLATE).isSuccessful()) {
            return new IncorrectResult<>("This stream is not a function declaration.");
        }

        DeclareFunctionNode node =
                new DeclareFunctionNode(functionIdentifierNode, arguments, body, returnTypeNode);
        return new CorrectResult<>(node);
    }

    private Result<Collection<DeclarationArgumentNode>> getArgumentsSection(
            TokenStreamInterface tokenStream) {
        if (!tokenStream.consume(LEFT_PARENTHESIS_TOKEN_TEMPLATE).isSuccessful()) {
            return new IncorrectResult<>("This stream is not a function declaration.");
        }

        Result<Collection<DeclarationArgumentNode>> getArgumentsResult = getArguments(tokenStream);
        if (!getArgumentsResult.isSuccessful()) {
            return new IncorrectResult<>(getArgumentsResult.errorMessage());
        }
        Collection<DeclarationArgumentNode> arguments = getArgumentsResult.result();

        if (!tokenStream.consume(RIGHT_PARENTHESIS_TOKEN_TEMPLATE).isSuccessful()) {
            return new IncorrectResult<>("This stream is not a function declaration.");
        }
        return new CorrectResult<>(arguments);
    }

    private Result<Collection<DeclarationArgumentNode>> getArguments(
            TokenStreamInterface tokenStream) {
        Collection<DeclarationArgumentNode> arguments = new ArrayList<>();
        while (peekIsNotRightParenthesis(tokenStream)) {
            Result<DeclarationArgumentNode> buildArgumentResult =
                    DECLARATION_ARGUMENT_BUILDER.build(tokenStream);
            if (!buildArgumentResult.isSuccessful()) {
                return new IncorrectResult<>(buildArgumentResult.errorMessage());
            }
            DeclarationArgumentNode argumentNode = buildArgumentResult.result();
            arguments.add(argumentNode);
            if (peekIsComma(tokenStream)) {
                tokenStream.consume();
            }
        }
        return new CorrectResult<>(arguments);
    }

    private Result<Collection<InterpretableNode>> getBody(TokenStreamInterface tokenStream) {
        Collection<InterpretableNode> body = new ArrayList<>();
        while (peekIsNotRightBrace(tokenStream)) {
            Result<? extends Node> buildResult = CHAIN_BUILDER.build(tokenStream);
            if (!buildResult.isSuccessful()) {
                return new IncorrectResult<>("This stream is not a function declaration.");
            }
            body.add((InterpretableNode) buildResult.result());
        }
        return new CorrectResult<>(body);
    }

    private boolean peekIsComma(TokenStreamInterface tokenStream) {
        Result<TokenInterface> peekResult = tokenStream.peek();
        if (!peekResult.isSuccessful()) {
            return false;
        }
        TokenInterface token = peekResult.result();
        return token.equals(COMMA_TOKEN_TEMPLATE);
    }

    private boolean peekIsNotRightParenthesis(TokenStreamInterface tokenStream) {
        Result<TokenInterface> peekResult = tokenStream.peek();
        if (!peekResult.isSuccessful()) {
            return false;
        }
        TokenInterface token = peekResult.result();
        return !token.equals(RIGHT_PARENTHESIS_TOKEN_TEMPLATE);
    }

    private boolean peekIsNotRightBrace(TokenStreamInterface tokenStream) {
        Result<TokenInterface> peekResult = tokenStream.peek();
        if (!peekResult.isSuccessful()) {
            return false;
        }
        TokenInterface token = peekResult.result();
        return !token.equals(RIGHT_BRACE_TOKEN_TEMPLATE);
    }
}
