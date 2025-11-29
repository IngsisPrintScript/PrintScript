/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers.identifier;

import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.nodes.factories.NodeFactory;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.tokens.Token;
import com.ingsis.utils.token.tokens.factories.TokenFactory;
import com.ingsis.utils.token.tokenstream.TokenStream;

public final class IdentifierParser implements Parser<IdentifierNode> {
    private final Token IDENTIFIER_TOKEN_TEMPLATE;
    private final NodeFactory NODE_FACTORY;

    public IdentifierParser(TokenFactory TOKEN_FACTORY, NodeFactory NODE_FACTORY) {
        this.IDENTIFIER_TOKEN_TEMPLATE = TOKEN_FACTORY.createIdentifierToken("");
        this.NODE_FACTORY = NODE_FACTORY;
    }

    @Override
    public Result<IdentifierNode> parse(TokenStream stream) {
        Result<Token> consumeIdentifierResult = stream.consume(IDENTIFIER_TOKEN_TEMPLATE);
        if (!consumeIdentifierResult.isCorrect()) {
            return new IncorrectResult<>(consumeIdentifierResult);
        }
        Token identifierToken = consumeIdentifierResult.result();
        return new CorrectResult<>(
                NODE_FACTORY.createIdentifierNode(
                        identifierToken.value(), identifierToken.line(), identifierToken.column()));
    }
}
