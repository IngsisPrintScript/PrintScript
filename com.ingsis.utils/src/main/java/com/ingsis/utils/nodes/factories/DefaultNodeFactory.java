/*
 * My Project
 */

package com.ingsis.utils.nodes.factories;

import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.expressions.BooleanLiteralNode;
import com.ingsis.utils.nodes.expressions.CallFunctionNode;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.expressions.IdentifierNode;
import com.ingsis.utils.nodes.expressions.NilExpressionNode;
import com.ingsis.utils.nodes.expressions.NumberLiteralNode;
import com.ingsis.utils.nodes.expressions.OperatorNode;
import com.ingsis.utils.nodes.expressions.OperatorType;
import com.ingsis.utils.nodes.expressions.StringLiteralNode;
import com.ingsis.utils.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.type.types.Types;
import java.math.BigDecimal;
import java.util.List;

public final class DefaultNodeFactory implements NodeFactory {
    @Override
    public IfKeywordNode createConditionalNode(
            ExpressionNode condition,
            List<Node> thenBody,
            List<Node> elseBody,
            Integer line,
            Integer column) {
        return new IfKeywordNode(condition, thenBody, elseBody, line, column);
    }

    @Override
    public DeclarationKeywordNode createDeclarationNode(
            IdentifierNode identifierNode,
            ExpressionNode expressionNode,
            Types declaredType,
            Boolean isMutable,
            Integer line,
            Integer column) {
        return new DeclarationKeywordNode(
                identifierNode, expressionNode, declaredType, isMutable, line, column);
    }

    @Override
    public IdentifierNode createIdentifierNode(String name, Integer line, Integer column) {
        return new IdentifierNode(name, line, column);
    }

    @Override
    public NumberLiteralNode createNumberLiteralNode(
            BigDecimal value, Integer line, Integer column) {
        return new NumberLiteralNode(value, line, column);
    }

    @Override
    public StringLiteralNode createStringLiteralNode(String value, Integer line, Integer column) {
        return new StringLiteralNode(value, line, column);
    }

    @Override
    public BooleanLiteralNode createBooleanLiteralNode(
            Boolean value, Integer line, Integer column) {
        return new BooleanLiteralNode(value, line, column);
    }

    @Override
    public NilExpressionNode createNilExpressionNode() {
        return new NilExpressionNode();
    }

    @Override
    public OperatorNode createOperatorNode(
            OperatorType operatorType,
            List<ExpressionNode> children,
            Integer line,
            Integer column) {
        return new OperatorNode(operatorType, children, line, column);
    }

    @Override
    public CallFunctionNode createCallFunctionNode(
            IdentifierNode identifierNode, List<ExpressionNode> argumentNodes) {
        return new CallFunctionNode(
                identifierNode, argumentNodes, identifierNode.line(), identifierNode.column());
    }
}
