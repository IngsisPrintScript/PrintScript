/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers.literal;

import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.utils.nodes.nodes.expression.literal.LiteralNode;
import com.ingsis.utils.nodes.nodes.factories.NodeFactory;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.tokens.Token;
import com.ingsis.utils.token.tokens.factories.TokenFactory;
import com.ingsis.utils.token.tokenstream.TokenStream;

public final class LiteralParser implements Parser<LiteralNode> {
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
        return new CorrectResult<>(
                NODE_FACTORY.createLiteralNode(
                        literalToken.value(), literalToken.line(), literalToken.column()));
    }
}
