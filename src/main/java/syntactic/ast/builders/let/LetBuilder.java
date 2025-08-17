package syntactic.ast.builders.let;

import common.factories.nodes.NodeFactory;
import common.factories.tokens.TokenFactory;
import common.nodes.Node;
import common.nodes.statements.LetStatementNode;
import common.responses.CorrectResult;
import common.responses.IncorrectResult;
import common.responses.Result;
import common.tokens.TokenInterface;
import common.tokens.stream.TokenStream;
import syntactic.ast.builders.ASTreeBuilderInterface;
import syntactic.ast.builders.ascription.AscriptionBuilder;
import syntactic.ast.builders.literal.LiteralBuilder;

public record LetBuilder() implements ASTreeBuilderInterface {
    final private static TokenInterface letTokenTemplate = new TokenFactory().createLetKeywordToken();
    final private static TokenInterface assignationTokenTemplate = new TokenFactory().createAssignationToken();
    final private static TokenInterface eolTokenTemplate = new TokenFactory().createEndOfLineToken();

    @Override
    public Boolean canBuild(TokenStream tokenStream) {
        Result peekResult = tokenStream.peek();
        if (!peekResult.isSuccessful()) return false;
        TokenInterface token = ((CorrectResult<TokenInterface>) peekResult).newObject();
        return token.equals(letTokenTemplate);
    }

    @Override
    public Result build(TokenStream tokenStream) {
        if (!canBuild(tokenStream)) return new IncorrectResult("Cannot build Let node.");

        if (!tokenStream.consume(letTokenTemplate).isSuccessful()) return new IncorrectResult("Token is not a let token.");

        Result buildAscritionResult = new AscriptionBuilder().build(tokenStream);
        if (!buildAscritionResult.isSuccessful()) return buildAscritionResult;

        if (!tokenStream.consume(assignationTokenTemplate).isSuccessful()) return new IncorrectResult("Token is not an assignation token.");

        Result buildLiteralResult = new LiteralBuilder().build(tokenStream);
        if (!buildLiteralResult.isSuccessful()) return buildLiteralResult;

        if (!tokenStream.consume(eolTokenTemplate).isSuccessful()) return new IncorrectResult("Token is not an eol token.");

        LetStatementNode letNode = (LetStatementNode) new NodeFactory().createLetStatementNode();
        Node expressionNode = ( (CorrectResult<Node>) buildLiteralResult).newObject();
        Node ascriptionNode = ( (CorrectResult<Node>) buildAscritionResult).newObject();

        letNode.addDeclaration(ascriptionNode);
        letNode.addExpression(expressionNode);

        return new CorrectResult<>(letNode);
    }
}
