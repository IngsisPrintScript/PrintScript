package parser.ast.builders.expression.binary.operators;

import common.TokenInterface;
import expression.binary.BinaryExpression;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;
import factories.NodeFactory;
import factories.tokens.TokenFactory;
import stream.TokenStreamInterface;

public class AdditionOperatorBuilder extends BinaryOperatorBuilder {
    private final TokenInterface template = new TokenFactory().createAdditionToken();

    public AdditionOperatorBuilder() {
    }

    @Override
    public Boolean canBuild(TokenStreamInterface tokenStream) {
        Result<TokenInterface> peekResult = tokenStream.peek();
        if (!peekResult.isSuccessful()) return false;
        TokenInterface token = peekResult.result();
        return token.equals(template);
    }

    @Override
    public Result<BinaryExpression> build(TokenStreamInterface tokenStream) {
        if (!canBuild(tokenStream)) return new IncorrectResult<>("Cannot build addition operator.");

        if (!tokenStream.consume(template).isSuccessful()) return new IncorrectResult<>("Cannot consume addition token.");

        return new CorrectResult<>((BinaryExpression) new NodeFactory().createAdditionNode());
    }
}