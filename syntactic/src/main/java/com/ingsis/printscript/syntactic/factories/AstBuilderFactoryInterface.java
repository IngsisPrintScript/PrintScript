package com.ingsis.printscript.syntactic.factories;

import com.ingsis.printscript.syntactic.ast.builders.ASTreeBuilderInterface;

public interface AstBuilderFactoryInterface {
    ASTreeBuilderInterface createLetBuilder();

    ASTreeBuilderInterface createAscriptionBuilder();

    ASTreeBuilderInterface createIdentifierBuilder();

    ASTreeBuilderInterface createLiteralBuilder();

    ASTreeBuilderInterface createTypeBuilder();

    ASTreeBuilderInterface createBinaryExpressionBuilder();

    ASTreeBuilderInterface createOperatorBuilder();

    ASTreeBuilderInterface createExpressionBuilder();

    ASTreeBuilderInterface createFinalBuilder();
}
