package parser.ast.builders.expression.binary.operators;

import common.TokenInterface;
import factories.tokens.TokenFactory;
import nodes.expression.binary.AssignationNode;
import nodes.expression.binary.BinaryExpression;
import nodes.factories.NodeFactory;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;
import stream.TokenStreamInterface;

public class AssignationOperatorBuilder extends BinaryOperatorBuilder {
  private final TokenInterface assignationTemplate = new TokenFactory().createAssignationToken();
  private final TokenInterface identifierTemplate =
      new TokenFactory().createIdentifierToken("placeholder");
  private final TokenInterface literalTemplate =
      new TokenFactory().createLiteralToken("placeholder");

  @Override
  public Boolean canBuild(TokenStreamInterface tokenStream) {
    Result<TokenInterface> peekResult = tokenStream.peek();
    if (!peekResult.isSuccessful()) {
      return false;
    }
    TokenInterface token = peekResult.result();
    return token.equals(assignationTemplate);
  }

  @Override
  public Result<BinaryExpression> build(TokenStreamInterface tokenStream) {
    if (!canBuild(tokenStream)) {
      return new IncorrectResult<>("Cannot build this token stream.");
    }

    if (!tokenStream.consume(assignationTemplate).isSuccessful()) {
      return new IncorrectResult<>("Cannot build this token stream.");
    }
    AssignationNode assignationNode = (AssignationNode) new NodeFactory().createAssignationNode();
    return new CorrectResult<>(assignationNode);
  }
}
