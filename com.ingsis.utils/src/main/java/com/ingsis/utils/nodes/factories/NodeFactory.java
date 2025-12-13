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

    IdentifierNode createIdentifierNode(String name, Integer line, Integer column);

    NumberLiteralNode createNumberLiteralNode(BigDecimal value, Integer line, Integer column);

    StringLiteralNode createStringLiteralNode(String value, Integer line, Integer column);

    BooleanLiteralNode createBooleanLiteralNode(Boolean value, Integer line, Integer column);

    NilExpressionNode createNilExpressionNode();

    OperatorNode createOperatorNode(
            OperatorType operatorType, List<ExpressionNode> children, Integer line, Integer column);

    CallFunctionNode createCallFunctionNode(
            IdentifierNode identifierNode, List<ExpressionNode> argumentNodes);
}
