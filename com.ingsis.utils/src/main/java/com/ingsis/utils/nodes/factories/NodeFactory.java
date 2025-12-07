/*
 * My Project
 */

package com.ingsis.utils.nodes.factories;

import com.ingsis.utils.nodes.Node;
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
import com.ingsis.utils.type.types.Types;
import java.math.BigDecimal;
import java.util.List;

public interface NodeFactory {
    IfKeywordNode createConditionalNode(
            ExpressionNode condition,
            List<Node> thenBody,
            List<Node> elseBody,
            Integer line,
            Integer column);

    DeclarationKeywordNode createDeclarationNode(
            IdentifierNode identifierNode,
            ExpressionNode expressionNode,
            Types declaredType,
            Boolean isMutable,
            Integer line,
            Integer column);

    OperatorNode createOperatorNode(
            OperatorType operatorType, List<ExpressionNode> children, Integer line, Integer column);

    CallFunctionNode createCallFunctionNode(
            IdentifierNode identifierNode,
            List<ExpressionNode> arguments,
            Integer line,
            Integer column);

    IdentifierNode createIdentifierNode(String name, Integer line, Integer column);

    NumberLiteralNode createNumberLiteralNode(BigDecimal value, Integer line, Integer column);

    StringLiteralNode createStringLiteralNode(String value, Integer line, Integer column);

    BooleanLiteralNode createBooleanLiteralNode(Boolean value, Integer line, Integer column);

    NilExpressionNode createNilExpressionNode();
}
