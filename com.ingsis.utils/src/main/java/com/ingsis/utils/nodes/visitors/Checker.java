/*
 * My Project
 */

package com.ingsis.utils.nodes.visitors;

import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.result.Result;

public interface Checker {
  Result<String> check(IfKeywordNode ifKeywordNode);

  Result<String> check(DeclarationKeywordNode declarationKeywordNode);

  Result<String> check(ExpressionNode expressionNode);
}
