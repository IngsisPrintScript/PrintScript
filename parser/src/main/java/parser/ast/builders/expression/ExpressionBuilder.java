package parser.ast.builders.expression;

import java.util.List;
import nodes.expression.ExpressionNode;
import parser.ast.builders.ASTreeBuilderInterface;
import parser.ast.builders.FinalBuilder;
import parser.ast.builders.expression.binary.BinaryExpressionBuilder;
import parser.ast.builders.identifier.IdentifierBuilder;
import parser.ast.builders.literal.LiteralBuilder;
import results.IncorrectResult;
import results.Result;
import stream.TokenStreamInterface;

public class ExpressionBuilder implements ASTreeBuilderInterface {
  private final List<Class<? extends ExpressionBuilder>> expressionsBuilders =
      List.of(BinaryExpressionBuilder.class, LiteralBuilder.class, IdentifierBuilder.class);
  private final ASTreeBuilderInterface nextBuilder;

  public ExpressionBuilder(ASTreeBuilderInterface nextBuilder) {
    this.nextBuilder = nextBuilder;
  }

  public ExpressionBuilder() {
    this.nextBuilder = new FinalBuilder();
  }

  @Override
  public Boolean canBuild(TokenStreamInterface tokenStream) {
    try {
      for (Class<? extends ExpressionBuilder> expressionBuilderClass : expressionsBuilders) {
        ExpressionBuilder builder = expressionBuilderClass.getDeclaredConstructor().newInstance();
        if (builder.canBuild(tokenStream)) {
          return true;
        }
      }
    } catch (Exception exception) {
      return false;
    }
    return false;
  }

  @Override
  public Result<ExpressionNode> build(TokenStreamInterface tokenStream) {
    try {
      for (Class<? extends ExpressionBuilder> expressionBuilderClass : expressionsBuilders) {
        ExpressionBuilder builder = expressionBuilderClass.getDeclaredConstructor().newInstance();
        if (builder.canBuild(tokenStream)) {
          return builder.build(tokenStream);
        }
      }
    } catch (Exception exception) {
      return new IncorrectResult<>(exception.getMessage());
    }
    return new IncorrectResult<>("That was not a valid expression.");
  }
}
