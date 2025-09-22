/*
 * My Project
 */

package com.ingsis.visitors;

import com.ingsis.nodes.identifier.IdentifierNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.nodes.keyword.LetKeywordNode;
import com.ingsis.nodes.literal.LiteralNode;
import com.ingsis.nodes.operator.TypeAssignationNode;
import com.ingsis.nodes.operator.ValueAssignationNode;
import com.ingsis.nodes.type.TypeNode;
import com.ingsis.result.Result;

public interface Visitor {
    Result<String> visit(IfKeywordNode ifKeywordNode);

    Result<String> visit(LetKeywordNode letKeywordNode);

    Result<String> visit(TypeAssignationNode typeAssignationNode);

    Result<String> visit(ValueAssignationNode valueAssignationNode);

    Result<String> visit(IdentifierNode identifierNode);

    Result<String> visit(LiteralNode literalNode);

    Result<String> visit(TypeNode typeNode);
}
