package parser.ast.builders.let;

import common.Node;
import common.TokenInterface;
import common.responses.CorrectResult;
import common.responses.Result;
import factories.NodeFactory;
import factories.tokens.TokenFactory;
import parser.ast.builders.ASTreeBuilderInterface;
import parser.factories.AstBuilderFactory;
import parser.factories.AstBuilderFactoryInterface;
import statements.LetStatementNode;
import stream.TokenStreamInterface;

public record LetBuilder(ASTreeBuilderInterface nextBuilder) implements ASTreeBuilderInterface {
    final private static TokenInterface letTokenTemplate = new TokenFactory().createLetKeywordToken();
    final private static TokenInterface assignationTokenTemplate = new TokenFactory().createAssignationToken();
    final private static TokenInterface eolTokenTemplate = new TokenFactory().createEndOfLineToken();
    private static final AstBuilderFactoryInterface builderFactory = new AstBuilderFactory();


    public LetBuilder() {
        this(builderFactory.createFinalBuilder());
    }

    @Override
    public Boolean canBuild(TokenStreamInterface tokenStream) {
        Result peekResult = tokenStream.peek();
        if (!peekResult.isSuccessful()) return false;
        TokenInterface token = ((CorrectResult<TokenInterface>) peekResult).newObject();
        return token.equals(letTokenTemplate);
    }

    @Override
    public Result build(TokenStreamInterface tokenStream) {
        if (!canBuild(tokenStream)) return nextBuilder().build(tokenStream);

        if (!tokenStream.consume(letTokenTemplate).isSuccessful()) return nextBuilder().build(tokenStream);

        Result buildAscritionResult = builderFactory.createAscriptionBuilder().build(tokenStream);
        if (!buildAscritionResult.isSuccessful()) return buildAscritionResult;

        LetStatementNode letNode = (LetStatementNode) new NodeFactory().createLetStatementNode();
        Node ascriptionNode = ((CorrectResult<Node>) buildAscritionResult).newObject();
        letNode.setAscription(ascriptionNode);

        if (tokenStream.consume(assignationTokenTemplate).isSuccessful()) {
            Result buildLiteralResult = builderFactory.createBinaryExpressionBuilder().build(tokenStream);
            if (!buildLiteralResult.isSuccessful()) return buildLiteralResult;
            Node expressionNode = ((CorrectResult<Node>) buildLiteralResult).newObject();
            letNode.setExpression(expressionNode);
        }

        if (!tokenStream.consume(eolTokenTemplate).isSuccessful()) return nextBuilder().build(tokenStream);

        return new CorrectResult<>(letNode);
    }
}
