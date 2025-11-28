/*
 * My Project
 */

package com.ingsis.syntactic.parsers;

import com.ingsis.nodes.factories.NodeFactory;
import com.ingsis.nodes.type.TypeNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.tokens.Token;
import com.ingsis.tokens.factories.TokenFactory;
import com.ingsis.tokenstream.TokenStream;

public final class TypeParser implements Parser<TypeNode> {
    private final NodeFactory NODE_FACTORY;
    private final Token TYPE_TEMPLATE;

    public TypeParser(NodeFactory NODE_FACTORY, TokenFactory TOKEN_FACTORY) {
        this.NODE_FACTORY = NODE_FACTORY;
        this.TYPE_TEMPLATE = TOKEN_FACTORY.createTypeToken("");
    }

    @Override
    public Result<TypeNode> parse(TokenStream stream) {
        Result<Token> consumeTypeResult = stream.consume(TYPE_TEMPLATE);
        if (!consumeTypeResult.isCorrect()) {
            return new IncorrectResult<>(consumeTypeResult);
        }
        Token typeToken = consumeTypeResult.result();
        return new CorrectResult<>(
                NODE_FACTORY.createTypeNode(
                        typeToken.value(), typeToken.line(), typeToken.column()));
    }
}
