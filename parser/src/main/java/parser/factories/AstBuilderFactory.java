package parser.factories;

import parser.ast.builders.ASTreeBuilderInterface;
import parser.ast.builders.FinalBuilder;
import parser.ast.builders.ascription.AscriptionBuilder;
import parser.ast.builders.expression.ExpressionBuilder;
import parser.ast.builders.expression.binary.BinaryExpressionBuilder;
import parser.ast.builders.expression.binary.operators.BinaryOperatorBuilder;
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
        return new BinaryOperatorBuilder();
    }

    @Override
    public ASTreeBuilderInterface createExpressionBuilder() {
        return new ExpressionBuilder();
    }

    @Override
    public ASTreeBuilderInterface createFinalBuilder() {
        return new FinalBuilder();
    }
}
