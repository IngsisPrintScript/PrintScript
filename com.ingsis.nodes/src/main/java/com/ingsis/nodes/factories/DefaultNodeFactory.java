/*
 * My Project
 */

package com.ingsis.nodes.factories;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.nodes.expression.operator.BinaryOperatorNode;
import com.ingsis.nodes.expression.operator.OperatorNode;
import com.ingsis.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.nodes.type.TypeNode;
import com.ingsis.types.Types;
import com.ingsis.visitors.Interpretable;
import java.util.Collection;

public final class DefaultNodeFactory implements NodeFactory {
  @Override
  public IfKeywordNode createConditionalNode(
      OperatorNode booleanExpression,
      Collection<Interpretable> thenBody,
      Collection<Interpretable> elseBody) {
    return new IfKeywordNode(booleanExpression, thenBody, elseBody);
  }

  @Override
  public DeclarationKeywordNode createLetNode(
      TypeAssignationNode typeAssignationNode, ValueAssignationNode valueAssignationNode) {
    return new DeclarationKeywordNode(typeAssignationNode, valueAssignationNode, true);
  }

  @Override
  public DeclarationKeywordNode createConstNode(TypeAssignationNode typeAssignationNode,
      ValueAssignationNode valueAssignationNode) {
    return new DeclarationKeywordNode(typeAssignationNode, valueAssignationNode, false);
  }

  @Override
  public BinaryOperatorNode createBinaryOperatorNode(
      String symbol, ExpressionNode leftChild, ExpressionNode rightChild) {
    return new BinaryOperatorNode(symbol, leftChild, rightChild);
  }

  @Override
  public ValueAssignationNode createValueAssignationNode(
      IdentifierNode identifierNode, ExpressionNode expressionNode) {
    return new ValueAssignationNode(identifierNode, expressionNode);
  }

  @Override
  public TypeAssignationNode createTypeAssignationNode(
      IdentifierNode identifierNode, TypeNode typeNode) {
    return new TypeAssignationNode(identifierNode, typeNode);
  }

  @Override
  public TypeNode createTypeNode(String type) {
    return new TypeNode(Types.fromKeyword(type));
  }

  @Override
  public IdentifierNode createIdentifierNode(String name) {
    return new IdentifierNode(name);
  }

  @Override
  public LiteralNode createLiteralNode(String value) {
    return new LiteralNode(value);
  }
}
