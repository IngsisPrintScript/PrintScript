package parser.ast.builders.expression.operators;

import common.TokenInterface;
import common.responses.CorrectResult;
import common.responses.IncorrectResult;
import common.responses.Result;
import factories.NodeFactory;
import factories.tokens.TokenFactory;
import stream.TokenStreamInterface;

public class AdditionOperatorBuilder extends OperatorBuilder {
    private final TokenInterface template = new TokenFactory().createAdditionToken();

    public AdditionOperatorBuilder() {
    }

    @Override
    public Boolean canBuild(TokenStreamInterface tokenStream) {
        Result peekResult = tokenStream.peek();
        if (!peekResult.isSuccessful()) return false;
        TokenInterface token = ((CorrectResult<TokenInterface>) peekResult).newObject();
        return token.equals(template);
    }

    @Override
    public Result build(TokenStreamInterface tokenStream) {
        if (!canBuild(tokenStream)) return new IncorrectResult("Cannot build addition operator.");

        if (!tokenStream.consume(template).isSuccessful()) return new IncorrectResult("Cannot consume addition token.");

        return new CorrectResult<>(new NodeFactory().createAdditionNode());
    }
}