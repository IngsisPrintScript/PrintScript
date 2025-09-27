/*
 * My Project
 */

package com.ingsis.visitors;

import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.nodes.expression.operator.BinaryOperatorNode;
import com.ingsis.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.nodes.keyword.LetKeywordNode;
import com.ingsis.nodes.type.TypeNode;
import com.ingsis.result.Result;

public interface Interpreter {
    Result<String> interpret(IfKeywordNode ifKeywordNode);

    Result<String> interpret(LetKeywordNode letKeywordNode);

    Result<String> interpret(BinaryOperatorNode binaryOperatorNode);

    Result<String> interpret(TypeAssignationNode typeAssignationNode);

    Result<String> interpret(ValueAssignationNode valueAssignationNode);

    Result<String> interpret(IdentifierNode identifierNode);

    Result<String> interpret(LiteralNode literalNode);

    Result<String> interpret(TypeNode typeNode);
}
