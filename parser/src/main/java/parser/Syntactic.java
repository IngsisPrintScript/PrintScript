package parser;

import responses.Result;
import parser.ast.builders.ASTreeBuilderInterface;
import stream.TokenStream;

public record Syntactic(TokenStream tokenStream, ASTreeBuilderInterface treeBuilder) implements SyntacticInterface {
    @Override
    public Result buildAbstractSyntaxTree() {
        return treeBuilder().build(tokenStream);
    }
}
