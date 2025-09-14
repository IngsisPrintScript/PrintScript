/*
 * My Project
 */

package com.ingsis.printscript.syntactic.ast.builders.expression;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.astnodes.expression.ExpressionNode;
import com.ingsis.printscript.astnodes.expression.literal.LiteralNode;
import com.ingsis.printscript.astnodes.factories.NodeFactory;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.tokens.factories.TokenFactory;
import com.ingsis.printscript.tokens.stream.TokenStreamInterface;

public class LiteralBuilder extends ExpressionBuilder {
    private static final TokenInterface TEMPLATE =
            new TokenFactory().createLiteralToken("placeholder");

    @Override
    public Boolean canBuild(TokenStreamInterface tokenStream) {
        Result<TokenInterface> peekResult = tokenStream.peek();
        if (!peekResult.isSuccessful()) return false;
        TokenInterface token = peekResult.result();
        return token.equals(TEMPLATE);
    }

    @Override
    public Result<ExpressionNode> build(TokenStreamInterface tokenStream) {
        if (!canBuild(tokenStream)) {
            return new IncorrectResult<>("Cannot build literal node.");
        }
        Result<TokenInterface> consumeResult = tokenStream.consume(TEMPLATE);
        if (!consumeResult.isSuccessful()) {
            return new IncorrectResult<>("Cannot build literal node.");
        }
        ;
        TokenInterface token = consumeResult.result();
        Node literalNode = new NodeFactory().createLiteralNode(token.value());
        return new CorrectResult<>((LiteralNode) literalNode);
    }
}
