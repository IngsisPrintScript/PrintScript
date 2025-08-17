package syntactic.factories.builders;

import syntactic.ast.builders.ASTreeBuilderInterface;

public interface AstBuilderFactoryInterface {
    ASTreeBuilderInterface createLetBuilder();
    ASTreeBuilderInterface createAscriptionBuilder();
    ASTreeBuilderInterface createIdentifierBuilder();
    ASTreeBuilderInterface createLiteralBuilder();
    ASTreeBuilderInterface createTypeBuilder();
    ASTreeBuilderInterface createFinalBuilder();
}
