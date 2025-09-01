package parser;

import common.Node;
import responses.CorrectResult;
import responses.IncorrectResult;
import responses.Result;
import parser.ast.builders.ASTreeBuilderInterface;
import stream.TokenStream;
import stream.TokenStreamInterface;
import visitor.SemanticallyCheckable;

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
