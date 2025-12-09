/*
 * My Project
 */

package com.ingsis.utils.nodes.factories;

import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.expressions.AssignationNode;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.expressions.atomic.identifier.IdentifierNode;
import com.ingsis.utils.nodes.expressions.atomic.literal.BooleanLiteralNode;
import com.ingsis.utils.nodes.expressions.atomic.literal.NumberLiteralNode;
import com.ingsis.utils.nodes.expressions.atomic.literal.StringLiteralNode;
import com.ingsis.utils.nodes.expressions.atomic.nil.NilExpressionNode;
import com.ingsis.utils.nodes.expressions.function.CallFunctionNode;
import com.ingsis.utils.nodes.expressions.operator.OperatorNode;
import com.ingsis.utils.nodes.expressions.operator.OperatorType;
import com.ingsis.utils.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.token.tokenstream.TokenStream;
import com.ingsis.utils.type.types.Types;
import java.math.BigDecimal;
import java.util.List;

public final class DefaultNodeFactory implements NodeFactory {
    @Override
    public IfKeywordNode createConditionalNode(
            ExpressionNode condition,
            List<Node> thenBody,
            List<Node> elseBody,
            TokenStream stream,
            Integer line,
            Integer column) {
        return new IfKeywordNode(condition, thenBody, elseBody, stream, line, column);
    }

    @Override
    public DeclarationKeywordNode createDeclarationNode(
            IdentifierNode identifierNode,
            ExpressionNode expressionNode,
            Types declaredType,
            Boolean isMutable,
            TokenStream stream,
            Integer line,
            Integer column) {
        return new DeclarationKeywordNode(
                identifierNode, expressionNode, declaredType, isMutable, stream, line, column);
    }

    @Override
    public IdentifierNode createIdentifierNode(
            String name, TokenStream stream, Integer line, Integer column) {
        return new IdentifierNode(name, stream, line, column);
    }

    @Override
    public NumberLiteralNode createNumberLiteralNode(
            BigDecimal value, TokenStream stream, Integer line, Integer column) {
        return new NumberLiteralNode(value, stream, line, column);
    }

    @Override
    public StringLiteralNode createStringLiteralNode(
            String value, TokenStream stream, Integer line, Integer column) {
        return new StringLiteralNode(value, stream, line, column);
    }

    @Override
    public BooleanLiteralNode createBooleanLiteralNode(
            Boolean value, TokenStream stream, Integer line, Integer column) {
        return new BooleanLiteralNode(value, stream, line, column);
    }

    @Override
    public NilExpressionNode createNilExpressionNode() {
        return new NilExpressionNode();
    }

    @Override
    public OperatorNode createOperatorNode(
            OperatorType operatorType,
            List<ExpressionNode> children,
            TokenStream stream,
            Integer line,
            Integer column) {
        return new OperatorNode(operatorType, children, stream, line, column);
    }

    @Override
    public CallFunctionNode createCallFunctionNode(
            IdentifierNode identifierNode,
            List<ExpressionNode> argumentNodes,
            TokenStream stream,
            Integer line,
            Integer column) {
        return new CallFunctionNode(identifierNode, argumentNodes, stream, line, column);
    }

    @Override
    public AssignationNode createAssignationNode(
            IdentifierNode identifierNode,
            ExpressionNode expressionNode,
            TokenStream stream,
            Integer line,
            Integer column) {
        return new AssignationNode(identifierNode, stream, expressionNode, line, column);
    }
}
