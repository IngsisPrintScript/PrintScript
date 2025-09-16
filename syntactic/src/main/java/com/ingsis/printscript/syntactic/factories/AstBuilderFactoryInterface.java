/*
 * My Project
 */

package com.ingsis.printscript.syntactic.factories;

import com.ingsis.printscript.syntactic.ast.builders.ASTreeBuilderInterface;

public interface AstBuilderFactoryInterface {
    ASTreeBuilderInterface createLetBuilder();

    ASTreeBuilderInterface createAscriptionBuilder();

    ASTreeBuilderInterface createCallFunctionBuilder();

    ASTreeBuilderInterface createBinaryExpressionBuilder();

    ASTreeBuilderInterface createCallArgumentBuilder();

    ASTreeBuilderInterface createOperatorBuilder();

    ASTreeBuilderInterface createExpressionBuilder();

    ASTreeBuilderInterface createFinalBuilder();

    ASTreeBuilderInterface createIdentifierBuilder();

    ASTreeBuilderInterface createLiteralBuilder();

    ASTreeBuilderInterface createTypeBuilder();
}
