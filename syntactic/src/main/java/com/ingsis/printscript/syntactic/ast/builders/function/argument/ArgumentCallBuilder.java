package com.ingsis.printscript.syntactic.ast.builders.function.argument;

import com.ingsis.printscript.astnodes.NilNode;
import com.ingsis.printscript.astnodes.expression.function.argument.CallArgumentNode;
import com.ingsis.printscript.astnodes.expression.literal.LiteralNode;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.syntactic.ast.builders.ASTreeBuilderInterface;
import com.ingsis.printscript.syntactic.ast.builders.expression.LiteralBuilder;
import com.ingsis.printscript.syntactic.factories.AstBuilderFactory;
import com.ingsis.printscript.syntactic.factories.AstBuilderFactoryInterface;
import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.tokens.factories.TokenFactory;
import com.ingsis.printscript.tokens.factories.TokenFactoryInterface;
import com.ingsis.printscript.tokens.stream.TokenStreamInterface;

public class ArgumentCallBuilder implements ASTreeBuilderInterface {
    private final TokenInterface LITERAL_TEMPLATE;
    private final LiteralBuilder LITERAL_BUILDER;

    public ArgumentCallBuilder() {
        TokenFactoryInterface tokenFactory = new TokenFactory();
        LITERAL_TEMPLATE = tokenFactory.createLiteralToken("placeholder");
        AstBuilderFactoryInterface builderFactory = new AstBuilderFactory();
        LITERAL_BUILDER = (LiteralBuilder) builderFactory.createLiteralBuilder();
    }

    @Override
    public Boolean canBuild(TokenStreamInterface tokenStream) {
        Result<TokenInterface> peekResult = tokenStream.peek();
        if (!peekResult.isSuccessful()){
            return false;
        }
        TokenInterface token = peekResult.result();
        return token.equals(LITERAL_TEMPLATE);
    }

    @Override
    public Result<CallArgumentNode> build(TokenStreamInterface tokenStream) {
        if (!canBuild(tokenStream)){
            return new IncorrectResult<>("This is not a call argument stream");
        }

        Result<LiteralNode> buildLiteralResult = LITERAL_BUILDER.build(tokenStream);
        if (!buildLiteralResult.isSuccessful()){
            return new IncorrectResult<>("This is not a call argument stream");
        }
        LiteralNode literalNode = buildLiteralResult.result();

        return new CorrectResult<>(new CallArgumentNode(null, literalNode));
    }
}
