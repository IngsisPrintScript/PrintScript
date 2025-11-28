/*
 * My Project
 */

package com.ingsis.visitors;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.result.Result;

public interface Interpreter {
    Result<String> interpret(IfKeywordNode ifKeywordNode);

    Result<String> interpret(DeclarationKeywordNode declarationKeywordNode);

    Result<Object> interpret(ExpressionNode expressionNode);
}
