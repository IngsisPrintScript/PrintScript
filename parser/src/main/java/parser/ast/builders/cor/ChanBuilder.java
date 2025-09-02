package parser.ast.builders.cor;

import parser.ast.builders.ASTreeBuilderInterface;
import parser.ast.builders.FinalBuilder;
import parser.ast.builders.expression.ExpressionBuilder;
import parser.ast.builders.let.LetBuilder;
import parser.ast.builders.print.PrintBuilder;

public record ChanBuilder() implements ChainBuilderInterface {
  @Override
  public ASTreeBuilderInterface createDefaultChain() {
    ASTreeBuilderInterface builder = new FinalBuilder();
    builder = new ExpressionBuilder(builder);
    builder = new LetBuilder(builder);
    builder = new PrintBuilder(builder);
    return builder;
  }
}
