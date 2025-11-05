/*
 * My Project
 */

package com.ingsis.nodes.factories;

import com.ingsis.nodes.Node;
import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.nodes.expression.operator.BinaryOperatorNode;
import com.ingsis.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.nodes.type.TypeNode;

import java.util.List;

public interface NodeFactory {
  IfKeywordNode createConditionalNode(
      ExpressionNode condition,
      List<Node> thenBody,
      List<Node> elseBody);

  DeclarationKeywordNode createLetNode(
      TypeAssignationNode typeAssignationNode, ValueAssignationNode valueAssignationNode);

  DeclarationKeywordNode createConstNode(
      TypeAssignationNode typeAssignationNode, ValueAssignationNode valueAssignationNode);

  BinaryOperatorNode createBinaryOperatorNode(
      String symbol, ExpressionNode leftChild, ExpressionNode rightChild);

  ValueAssignationNode createValueAssignationNode(
      IdentifierNode identifierNode, ExpressionNode expressionNode);

  TypeAssignationNode createTypeAssignationNode(IdentifierNode identifierNode, TypeNode typeNode);

  TypeNode createTypeNode(String type);

  IdentifierNode createIdentifierNode(String name);

  LiteralNode createLiteralNode(String value);
}
