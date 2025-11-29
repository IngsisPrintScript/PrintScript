/*
 * My Project
 */

package com.ingsis.utils.nodes.visitors; /*
                                          * My Project
                                          */

import com.ingsis.utils.nodes.nodes.expression.function.CallFunctionNode;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.nodes.expression.literal.LiteralNode;
import com.ingsis.utils.nodes.nodes.expression.operator.BinaryOperatorNode;
import com.ingsis.utils.nodes.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.utils.nodes.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.nodes.nodes.type.TypeNode;
import com.ingsis.utils.result.Result;

public interface Visitor {
    Result<String> visit(IfKeywordNode ifKeywordNode);

    Result<String> visit(DeclarationKeywordNode declarationKeywordNode);

    Result<String> visit(CallFunctionNode callFunctionNode);

    Result<String> visit(BinaryOperatorNode binaryOperatorNode);

    Result<String> visit(TypeAssignationNode typeAssignationNode);

    Result<String> visit(ValueAssignationNode valueAssignationNode);

    Result<String> visit(IdentifierNode identifierNode);

    Result<String> visit(LiteralNode literalNode);

    Result<String> visit(TypeNode typeNode);
}
