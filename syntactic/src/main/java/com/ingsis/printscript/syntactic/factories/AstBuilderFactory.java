/*
 * My Project
 */

package com.ingsis.printscript.syntactic.factories;

import com.ingsis.printscript.syntactic.ast.builders.ASTreeBuilderInterface;
import com.ingsis.printscript.syntactic.ast.builders.FinalBuilder;
import com.ingsis.printscript.syntactic.ast.builders.ascription.AscriptionBuilder;
import com.ingsis.printscript.syntactic.ast.builders.expression.BinaryExpressionBuilder;
import com.ingsis.printscript.syntactic.ast.builders.expression.ExpressionBuilder;
import com.ingsis.printscript.syntactic.ast.builders.expression.IdentifierBuilder;
import com.ingsis.printscript.syntactic.ast.builders.expression.LiteralBuilder;
import com.ingsis.printscript.syntactic.ast.builders.expression.operators.binary.BinaryOperatorBuilder;
import com.ingsis.printscript.syntactic.ast.builders.keywords.let.LetBuilder;
import com.ingsis.printscript.syntactic.ast.builders.type.TypeBuilder;

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
