package parser;

import nodes.common.Node;
import nodes.visitor.SemanticallyCheckable;
import parser.ast.builders.ASTreeBuilderInterface;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;
import stream.TokenStreamInterface;

public record Syntactic(ASTreeBuilderInterface treeBuilder) implements SyntacticInterface {
  @Override
  public Result<SemanticallyCheckable> buildAbstractSyntaxTree(TokenStreamInterface tokenStream) {
    Result<? extends Node> buildResult = treeBuilder().build(tokenStream);
    Node root = buildResult.result();
    if (!(root instanceof SemanticallyCheckable semanticallyCheckableNode)) {
      return new IncorrectResult<>("Has built a tree which is not semantically checkable");
    }
    return new CorrectResult<>(semanticallyCheckableNode);
  }
}
