package syntactic.factories.builders.cor;

import syntactic.ast.builders.ASTreeBuilderInterface;
import syntactic.ast.builders.FinalBuilder;
import syntactic.ast.builders.let.LetBuilder;
import syntactic.ast.builders.print.PrintBuilder;

public record ChanBuilder() implements ChainBuilderInterface {
    @Override
    public ASTreeBuilderInterface createDefaultChain() {
        ASTreeBuilderInterface builder = new FinalBuilder();
        builder = new LetBuilder(builder);
        builder = new PrintBuilder(builder);
        return builder;
    }
}
