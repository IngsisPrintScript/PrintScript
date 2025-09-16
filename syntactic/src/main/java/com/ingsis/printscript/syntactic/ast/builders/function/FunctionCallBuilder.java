package com.ingsis.printscript.syntactic.ast.builders.function;

import com.ingsis.printscript.astnodes.expression.function.CallFunctionNode;
import com.ingsis.printscript.astnodes.expression.function.argument.CallArgumentNode;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.syntactic.ast.builders.ASTreeBuilderInterface;
import com.ingsis.printscript.syntactic.ast.builders.expression.IdentifierBuilder;
import com.ingsis.printscript.syntactic.ast.builders.function.argument.ArgumentCallBuilder;
import com.ingsis.printscript.syntactic.factories.AstBuilderFactory;
import com.ingsis.printscript.syntactic.factories.AstBuilderFactoryInterface;
import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.tokens.factories.TokenFactory;
import com.ingsis.printscript.tokens.factories.TokenFactoryInterface;
import com.ingsis.printscript.tokens.stream.TokenStreamInterface;

import java.util.ArrayList;
import java.util.Collection;

public final class FunctionCallBuilder implements ASTreeBuilderInterface {
    private final ASTreeBuilderInterface NEXT_BUILDER;
    private final TokenInterface IDENTIFIER_TEMPLATE;
    private final TokenInterface LEFT_PARENTHESIS_TEMPLATE;
    private final TokenInterface RIGHT_PARENTHESIS_TEMPLATE;
    private final TokenInterface COMMA_TEMPLATE;
    private final TokenInterface EOL_TEMPLATE;
    private final IdentifierBuilder IDENTIFIER_BUILDER;
    private final ArgumentCallBuilder ARGUMENT_CALL_BUILDER;

    public FunctionCallBuilder(ASTreeBuilderInterface nextBuilder) {
        TokenFactoryInterface tokenFactory = new TokenFactory();
        IDENTIFIER_TEMPLATE = tokenFactory.createIdentifierToken("placeholder");
        LEFT_PARENTHESIS_TEMPLATE = tokenFactory.createLeftParenthesisToken();
        RIGHT_PARENTHESIS_TEMPLATE = tokenFactory.createRightParenthesisToken();
        COMMA_TEMPLATE = tokenFactory.createSeparatorToken("COMMA");
        EOL_TEMPLATE = tokenFactory.createEndOfLineToken();
        AstBuilderFactoryInterface builderFactory = new AstBuilderFactory();
        NEXT_BUILDER = nextBuilder;
        IDENTIFIER_BUILDER = (IdentifierBuilder) builderFactory.createIdentifierBuilder();
        ARGUMENT_CALL_BUILDER = (ArgumentCallBuilder) builderFactory.createCallArgumentBuilder();
    }

    public FunctionCallBuilder() {
        TokenFactoryInterface tokenFactory = new TokenFactory();
        IDENTIFIER_TEMPLATE = tokenFactory.createIdentifierToken("placeholder");
        LEFT_PARENTHESIS_TEMPLATE = tokenFactory.createLeftParenthesisToken();
        RIGHT_PARENTHESIS_TEMPLATE = tokenFactory.createRightParenthesisToken();
        COMMA_TEMPLATE = tokenFactory.createSeparatorToken("COMMA");
        EOL_TEMPLATE = tokenFactory.createEndOfLineToken();
        AstBuilderFactoryInterface builderFactory = new AstBuilderFactory();
        NEXT_BUILDER = builderFactory.createFinalBuilder();
        IDENTIFIER_BUILDER = (IdentifierBuilder) builderFactory.createIdentifierBuilder();
        ARGUMENT_CALL_BUILDER = (ArgumentCallBuilder) builderFactory.createCallArgumentBuilder();
    }

    @Override
    public Boolean canBuild(TokenStreamInterface tokenStream) {
        Result<TokenInterface> peekResult = tokenStream.peek();
        if (!peekResult.isSuccessful()){
            return false;
        }
        TokenInterface token = peekResult.result();
        return token.equals(IDENTIFIER_TEMPLATE);
    }

    @Override
    public Result<CallFunctionNode> build(TokenStreamInterface tokenStream) {
        if (!canBuild(tokenStream)){
            return new IncorrectResult<>("This is not a function call stream.");
        }
        Result<IdentifierNode> identifierBuildResult = IDENTIFIER_BUILDER.build(tokenStream);
        if (!identifierBuildResult.isSuccessful()){
            return new IncorrectResult<>(identifierBuildResult.errorMessage());
        }
        IdentifierNode identifierNode = identifierBuildResult.result();

        if (!tokenStream.consume(LEFT_PARENTHESIS_TEMPLATE).isSuccessful()){
            return new IncorrectResult<>("This is not a function call stream.");
        }
        Collection<CallArgumentNode> argumentNodes = new ArrayList<>();
        while (peekIsNotRightParenthesis(tokenStream)){
            Result<CallArgumentNode> argumentBuildResult  = ARGUMENT_CALL_BUILDER.build(tokenStream);
            if (!argumentBuildResult.isSuccessful()){
                return new IncorrectResult<>(argumentBuildResult.errorMessage());
            }
            CallArgumentNode callArgumentNode = argumentBuildResult.result();
            argumentNodes.add(callArgumentNode);
            if (peekIsComma(tokenStream)){
                tokenStream.consume();
            }
        }

        if (!tokenStream.consume(RIGHT_PARENTHESIS_TEMPLATE).isSuccessful()){
            return new IncorrectResult<>("This is not a function call stream.");
        }

        if (!tokenStream.consume(EOL_TEMPLATE).isSuccessful()){
            return new IncorrectResult<>("This is not a function call stream.");
        }

        return new CorrectResult<>(new CallFunctionNode(identifierNode, argumentNodes));
    }

    private Boolean peekIsNotRightParenthesis(TokenStreamInterface tokenStream) {
        Result<TokenInterface> peekResult = tokenStream.peek();
        if (!peekResult.isSuccessful()){
            return false;
        }
        TokenInterface token = peekResult.result();
        return !token.equals(RIGHT_PARENTHESIS_TEMPLATE);
    }

    private Boolean peekIsComma(TokenStreamInterface tokenStream) {
        Result<TokenInterface> peekResult = tokenStream.peek();
        if (!peekResult.isSuccessful()){
            return false;
        }
        TokenInterface token = peekResult.result();
        return token.equals(COMMA_TEMPLATE);
    }
}
