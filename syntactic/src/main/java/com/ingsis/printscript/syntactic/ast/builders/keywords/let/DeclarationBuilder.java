/*
 * My Project
 */

package com.ingsis.printscript.syntactic.ast.builders.keywords.let;

import com.ingsis.printscript.astnodes.declaration.AscriptionNode;
import com.ingsis.printscript.astnodes.factories.NodeFactory;
import com.ingsis.printscript.astnodes.statements.LetStatementNode;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.syntactic.ast.builders.ascription.AscriptionBuilder;
import com.ingsis.printscript.syntactic.factories.AstBuilderFactory;
import com.ingsis.printscript.syntactic.factories.AstBuilderFactoryInterface;
import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.tokens.factories.TokenFactory;
import com.ingsis.printscript.tokens.factories.TokenFactoryInterface;
import com.ingsis.printscript.tokens.stream.TokenStreamInterface;

public final class DeclarationBuilder extends LetBuilder {
    private final TokenInterface EOL_TOKEN_TEMPLATE;
    private final AscriptionBuilder ASCRIPTION_BUILDER;

    public DeclarationBuilder() {
        TokenFactoryInterface tokenFactory = new TokenFactory();
        EOL_TOKEN_TEMPLATE = tokenFactory.createEndOfLineToken();
        AstBuilderFactoryInterface builderFactory = new AstBuilderFactory();
        ASCRIPTION_BUILDER = (AscriptionBuilder) builderFactory.createAscriptionBuilder();
    }

    @Override
    public Result<LetStatementNode> build(TokenStreamInterface tokenStream) {
        Result<AscriptionNode> buildAscritionResult = ASCRIPTION_BUILDER.build(tokenStream);
        if (!buildAscritionResult.isSuccessful()) {
            return new IncorrectResult<>("Cannot build let ast.");
        }

        LetStatementNode letNode = (LetStatementNode) new NodeFactory().createLetStatementNode();
        letNode.setAscription(buildAscritionResult.result());

        if (!tokenStream.consume(EOL_TOKEN_TEMPLATE).isSuccessful()) {
            return new IncorrectResult<>("Stream is not declaration.");
        }
        ;

        return new CorrectResult<>(letNode);
    }
}
