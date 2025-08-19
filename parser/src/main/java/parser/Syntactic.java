package parser;

import common.responses.Result;
import common.tokens.stream.TokenStream;
import parser.ast.builders.ASTreeBuilderInterface;

public record Syntactic(TokenStream tokenStream, ASTreeBuilderInterface treeBuilder) implements SyntacticInterface {
    @Override
    public Result buildAbstractSyntaxTree() {
        return treeBuilder().build(tokenStream);
    }
}
