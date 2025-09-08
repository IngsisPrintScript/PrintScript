package parser;

import common.Node;
import common.TokenInterface;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;
import parser.ast.builders.ASTreeBuilderInterface;
import stream.TokenStream;
import stream.TokenStreamInterface;
import visitor.SemanticallyCheckable;

import java.util.ArrayList;
import java.util.List;

public class Syntactic implements SyntacticInterface {

    private List<TokenInterface> tokens = new ArrayList<>();
    private TokenStreamInterface tokenStreams;
    private final ASTreeBuilderInterface treeBuilder;

    public Syntactic(ASTreeBuilderInterface treeBuilder, ){
        this.treeBuilder = treeBuilder;
        this.tokenStreams = new TokenStream(tokens);

    }

    @Override
    public Result<SemanticallyCheckable> buildAbstractSyntaxTree() {
        tokenStreams = new TokenStream(tokens);
        Result<? extends Node> buildResult = treeBuilder.build(tokenStreams);
        if (!buildResult.isSuccessful()) {
            if(nextToken.hasNext()){
                return buildAbstractSyntaxTree();
            }
            return new IncorrectResult<>(buildResult.errorMessage());
        }
        Node root = buildResult.result();
        if (!(root instanceof SemanticallyCheckable semanticallyCheckableNode)) {
            return new IncorrectResult<>("Has built a tree which is not semantically checkable");
        }
        return new CorrectResult<>(semanticallyCheckableNode);
    }
}
