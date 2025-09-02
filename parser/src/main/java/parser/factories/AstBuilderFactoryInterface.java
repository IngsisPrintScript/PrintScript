package parser.factories;

import parser.ast.builders.ASTreeBuilderInterface;

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
