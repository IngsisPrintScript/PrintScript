/*
 * My Project
 */

package com.ingsis.printscript.syntactic.ast.builders.expression.operators.binary;

import com.ingsis.printscript.astnodes.expression.binary.BinaryExpression;
import com.ingsis.printscript.astnodes.factories.NodeFactory;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.tokens.factories.TokenFactory;
import com.ingsis.printscript.tokens.stream.TokenStreamInterface;

public class AdditionOperatorBuilder extends BinaryOperatorBuilder {
    private final TokenInterface TEMPLATE = new TokenFactory().createAdditionToken();

    public AdditionOperatorBuilder() {}

    @Override
    public Boolean canBuild(TokenStreamInterface tokenStream) {
        Result<TokenInterface> peekResult = tokenStream.peek();
        if (!peekResult.isSuccessful()) return false;
        TokenInterface token = peekResult.result();
        return token.equals(TEMPLATE);
    }

    @Override
    public Result<BinaryExpression> build(TokenStreamInterface tokenStream) {
        if (!canBuild(tokenStream)) return new IncorrectResult<>("Cannot build addition operator.");

        Result<TokenInterface> consumeResult = tokenStream.consume(TEMPLATE);
        if (!consumeResult.isSuccessful()) {
            return new IncorrectResult<>(consumeResult.errorMessage());
        }

        return new CorrectResult<>((BinaryExpression) new NodeFactory().createAdditionNode());
    }
}
