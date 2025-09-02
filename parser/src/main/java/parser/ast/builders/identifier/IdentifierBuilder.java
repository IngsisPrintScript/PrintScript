package parser.ast.builders.identifier;

import common.Node;
import common.TokenInterface;
import expression.ExpressionNode;
import expression.identifier.IdentifierNode;
import parser.ast.builders.expression.ExpressionBuilder;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;
import factories.NodeFactory;
import factories.tokens.TokenFactory;
import parser.ast.builders.ASTreeBuilderInterface;
import stream.TokenStreamInterface;

public class IdentifierBuilder extends ExpressionBuilder {
    private static final TokenInterface template = new TokenFactory().createIdentifierToken("placeholder");

    @Override
    public Boolean canBuild(TokenStreamInterface tokenStream) {
        Result<TokenInterface> peekResult = tokenStream.peek();
        if (!peekResult.isSuccessful()) return false;
        TokenInterface token = peekResult.result();
        return token.equals(template);
    }

    @Override
    public Result<ExpressionNode> build(TokenStreamInterface tokenStream) {
        if (!canBuild(tokenStream)) {
            return new IncorrectResult<>("Cannot build identifier node.");
        }
        Result<TokenInterface> consumeResult = tokenStream.consume(template);
        if (!consumeResult.isSuccessful()) {
            return new IncorrectResult<>("Cannot build identifier node.");
        }
        TokenInterface token = consumeResult.result();
        Node identifierNode = new NodeFactory().createIdentifierNode(token.value());
        return new CorrectResult<>((IdentifierNode) identifierNode);
    }
}
