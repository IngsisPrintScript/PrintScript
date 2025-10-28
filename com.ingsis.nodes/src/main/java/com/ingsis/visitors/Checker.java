/*
 * My Project
 */

package com.ingsis.visitors;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.result.Result;

public interface Checker {
  Result<String> check(IfKeywordNode ifKeywordNode);

  Result<String> check(DeclarationKeywordNode declarationKeywordNode);

  Result<String> check(ExpressionNode expressionNode);
}
