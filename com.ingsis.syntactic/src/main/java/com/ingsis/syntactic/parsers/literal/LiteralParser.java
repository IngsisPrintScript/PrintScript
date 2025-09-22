/*
 * My Project
 */

package com.ingsis.syntactic.parsers.literal;

import com.ingsis.nodes.factories.NodeFactory;
import com.ingsis.nodes.literal.LiteralNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.syntactic.parsers.Parser;
import com.ingsis.tokens.Token;
import com.ingsis.tokens.factories.TokenFactory;
import com.ingsis.tokenstream.TokenStream;

public final class LiteralParser implements Parser {
    private final Token LITERAL_TOKEN_TEMPLATE;
    private final NodeFactory NODE_FACTORY;

    public LiteralParser(TokenFactory TOKEN_FACTORY, NodeFactory NODE_FACTORY) {
        this.NODE_FACTORY = NODE_FACTORY;
        this.LITERAL_TOKEN_TEMPLATE = TOKEN_FACTORY.createLiteralToken("");
    }

    @Override
    public Result<LiteralNode> parse(TokenStream stream) {
        Result<Token> consumeLiteralResult = stream.consume(LITERAL_TOKEN_TEMPLATE);
        if (!consumeLiteralResult.isCorrect()) {
            return new IncorrectResult<>(consumeLiteralResult);
        }
        Token literalToken = consumeLiteralResult.result();
        return new CorrectResult<>(NODE_FACTORY.createLiteralNode(literalToken.value()));
    }
}
