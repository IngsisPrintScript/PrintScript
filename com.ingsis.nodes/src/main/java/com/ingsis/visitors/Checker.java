/*
 * My Project
 */

package com.ingsis.visitors;

import com.ingsis.nodes.identifier.IdentifierNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.nodes.keyword.LetKeywordNode;
import com.ingsis.nodes.literal.LiteralNode;
import com.ingsis.nodes.operator.TypeAssignationOperatorNode;
import com.ingsis.nodes.operator.ValueAssignationOperatorNode;
import com.ingsis.nodes.type.TypeNode;
import com.ingsis.result.Result;

public interface Checker {
    Result<String> check(IfKeywordNode ifKeywordNode);

    Result<String> check(LetKeywordNode letKeywordNode);

    Result<String> check(TypeAssignationOperatorNode typeAssignationOperatorNode);

    Result<String> check(ValueAssignationOperatorNode valueAssignationOperatorNode);

    Result<String> check(IdentifierNode identifierNode);

    Result<String> check(LiteralNode literalNode);

    Result<String> check(TypeNode typeNode);
}
