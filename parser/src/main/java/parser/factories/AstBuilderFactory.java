package parser.factories;

import parser.ast.builders.ASTreeBuilderInterface;
import parser.ast.builders.FinalBuilder;
import parser.ast.builders.ascription.AscriptionBuilder;
import parser.ast.builders.expression.BinaryExpressionBuilder;
import parser.ast.builders.expression.operators.OperatorBuilder;
import parser.ast.builders.identifier.IdentifierBuilder;
import parser.ast.builders.let.LetBuilder;
import parser.ast.builders.literal.LiteralBuilder;
import parser.ast.builders.type.TypeBuilder;

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
