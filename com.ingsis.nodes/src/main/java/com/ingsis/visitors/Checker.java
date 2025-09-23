/*
 * My Project
 */

package com.ingsis.visitors;

import com.ingsis.nodes.identifier.IdentifierNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.nodes.keyword.LetKeywordNode;
import com.ingsis.nodes.literal.LiteralNode;
import com.ingsis.nodes.operator.BinaryOperatorNode;
import com.ingsis.nodes.operator.TypeAssignationNode;
import com.ingsis.nodes.operator.ValueAssignationNode;
import com.ingsis.nodes.type.TypeNode;
import com.ingsis.result.Result;

public interface Checker {
    Result<String> check(IfKeywordNode ifKeywordNode);

    Result<String> check(LetKeywordNode letKeywordNode);

    Result<String> check(BinaryOperatorNode binaryOperatorNode);

    Result<String> check(TypeAssignationNode typeAssignationNode);

    Result<String> check(ValueAssignationNode valueAssignationNode);

    Result<String> check(IdentifierNode identifierNode);

    Result<String> check(LiteralNode literalNode);

    Result<String> check(TypeNode typeNode);
}
