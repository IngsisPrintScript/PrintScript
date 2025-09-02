package parser.ast.builders.literal;


import common.Node;
import common.TokenInterface;
import expression.ExpressionNode;
import expression.literal.LiteralNode;
import parser.ast.builders.expression.ExpressionBuilder;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;
import factories.NodeFactory;
import factories.tokens.TokenFactory;
import parser.ast.builders.ASTreeBuilderInterface;
import stream.TokenStreamInterface;

public class LiteralBuilder extends ExpressionBuilder {
    private static final TokenInterface template = new TokenFactory().createLiteralToken("placeholder");

    @Override
    public Boolean canBuild(TokenStreamInterface tokenStream) {
        Result<TokenInterface> peekResult = tokenStream.peek();
        if (!peekResult.isSuccessful()) return false;
        TokenInterface token = peekResult.result();
        return token.equals(template);
    }

    @Override
    public Result<ExpressionNode> build(TokenStreamInterface tokenStream) {
        if (!canBuild(tokenStream)){
            return new IncorrectResult<>("Cannot build literal node.");
        }
        Result<TokenInterface> consumeResult = tokenStream.consume(template);
        if (!consumeResult.isSuccessful()){
            return new IncorrectResult<>("Cannot build literal node.");
        };
        TokenInterface token = consumeResult.result();
        Node literalNode = new NodeFactory().createLiteralNode(token.value());
        return new CorrectResult<>((LiteralNode) literalNode);
    }
}
