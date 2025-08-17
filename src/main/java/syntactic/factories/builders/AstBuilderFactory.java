package syntactic.factories.builders;

import syntactic.ast.builders.ASTreeBuilderInterface;
import syntactic.ast.builders.let.LetBuilder;

public record AstBuilderFactory() implements AstBuilderFactoryInterface {
    @Override
    public ASTreeBuilderInterface createDefaultBuilder() {
        return new LetBuilder();
    }
}
