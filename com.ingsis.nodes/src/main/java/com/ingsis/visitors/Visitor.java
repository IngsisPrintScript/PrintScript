/*
 * My Project
 */

package com.ingsis.visitors;

import com.ingsis.nodes.expression.function.CallFunctionNode;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.nodes.expression.operator.BinaryOperatorNode;
import com.ingsis.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.nodes.keyword.LetKeywordNode;
import com.ingsis.nodes.type.TypeNode;
import com.ingsis.result.Result;

public interface Visitor {
    Result<String> visit(IfKeywordNode ifKeywordNode);

    Result<String> visit(LetKeywordNode letKeywordNode);

    Result<String> visit(CallFunctionNode callFunctionNode);

    Result<String> visit(BinaryOperatorNode binaryOperatorNode);

    Result<String> visit(TypeAssignationNode typeAssignationNode);

    Result<String> visit(ValueAssignationNode valueAssignationNode);

    Result<String> visit(IdentifierNode identifierNode);

    Result<String> visit(LiteralNode literalNode);

    Result<String> visit(TypeNode typeNode);
}
