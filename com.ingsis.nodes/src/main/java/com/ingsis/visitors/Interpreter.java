/*
 * My Project
 */

package com.ingsis.visitors;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.function.CallFunctionNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.nodes.keyword.LetKeywordNode;
import com.ingsis.result.Result;

public interface Interpreter {
  Result<String> interpret(IfKeywordNode ifKeywordNode);

  Result<String> interpret(LetKeywordNode letKeywordNode);

  Result<String> interpret(CallFunctionNode callFunctionNode);

  Result<Object> interpret(ExpressionNode expressionNode);
}
