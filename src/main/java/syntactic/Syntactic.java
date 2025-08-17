package syntactic;

import common.responses.Result;
import common.tokens.stream.TokenStream;
import syntactic.ast.builders.ASTreeBuilderInterface;

public record Syntactic(TokenStream tokenStream, ASTreeBuilderInterface treeBuilder) implements SyntacticInterface {
    @Override
    public Result buildAbstractSyntaxTree() {
        return treeBuilder().build(tokenStream);
    }
}
