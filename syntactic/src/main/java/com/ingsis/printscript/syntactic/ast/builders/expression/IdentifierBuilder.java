/*
 * My Project
 */

package com.ingsis.printscript.syntactic.ast.builders.expression;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.factories.NodeFactory;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.tokens.factories.TokenFactory;
import com.ingsis.printscript.tokens.stream.TokenStreamInterface;

public class IdentifierBuilder extends ExpressionBuilder {
    private static final TokenInterface TEMPLATE =
            new TokenFactory().createIdentifierToken("placeholder");

    @Override
    public Boolean canBuild(TokenStreamInterface tokenStream) {
        Result<TokenInterface> peekResult = tokenStream.peek();
        if (!peekResult.isSuccessful()) return false;
        TokenInterface token = peekResult.result();
        return token.equals(TEMPLATE);
    }

    @Override
    public Result<IdentifierNode> build(TokenStreamInterface tokenStream) {
        if (!canBuild(tokenStream)) {
            return new IncorrectResult<>("Cannot build identifier node.");
        }
        Result<TokenInterface> consumeResult = tokenStream.consume(TEMPLATE);
        if (!consumeResult.isSuccessful()) {
            return new IncorrectResult<>("Cannot build identifier node.");
        }
        TokenInterface token = consumeResult.result();
        Node identifierNode = new NodeFactory().createIdentifierNode(token.value());
        return new CorrectResult<>((IdentifierNode) identifierNode);
    }
}
