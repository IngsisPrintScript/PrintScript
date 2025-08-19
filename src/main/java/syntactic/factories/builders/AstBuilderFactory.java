package syntactic.factories.builders;

import syntactic.ast.builders.ASTreeBuilderInterface;
import syntactic.ast.builders.FinalBuilder;
import syntactic.ast.builders.ascription.AscriptionBuilder;
import syntactic.ast.builders.expression.BinaryExpressionBuilder;
import syntactic.ast.builders.expression.operators.OperatorBuilder;
import syntactic.ast.builders.identifier.IdentifierBuilder;
import syntactic.ast.builders.let.LetBuilder;
import syntactic.ast.builders.literal.LiteralBuilder;
import syntactic.ast.builders.type.TypeBuilder;

public record AstBuilderFactory() implements AstBuilderFactoryInterface {
    @Override
    public ASTreeBuilderInterface createLetBuilder() {
        return new LetBuilder();
    }

    @Override
    public ASTreeBuilderInterface createAscriptionBuilder() {
        return new AscriptionBuilder();
    }

    @Override
    public ASTreeBuilderInterface createIdentifierBuilder() {
        return new IdentifierBuilder();
    }

    @Override
    public ASTreeBuilderInterface createLiteralBuilder() {
        return new LiteralBuilder();
    }

    @Override
    public ASTreeBuilderInterface createTypeBuilder() {
        return new TypeBuilder();
    }

    @Override
    public ASTreeBuilderInterface createBinaryExpressionBuilder() {
        return new BinaryExpressionBuilder();
    }

    @Override
    public ASTreeBuilderInterface createOperatorBuilder() {
        return new OperatorBuilder();
    }

    @Override
    public ASTreeBuilderInterface createFinalBuilder() {
        return new FinalBuilder();
    }
}
