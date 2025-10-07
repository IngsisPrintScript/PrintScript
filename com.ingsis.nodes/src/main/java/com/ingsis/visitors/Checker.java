/*
 * My Project
 */

package com.ingsis.visitors;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.function.CallFunctionNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.nodes.keyword.LetKeywordNode;
import com.ingsis.result.Result;

public interface Checker {
  Result<String> check(IfKeywordNode ifKeywordNode);

  Result<String> check(LetKeywordNode letKeywordNode);

  Result<String> check(CallFunctionNode callFunctionNode);

  Result<String> check(ExpressionNode expressionNode);
}
