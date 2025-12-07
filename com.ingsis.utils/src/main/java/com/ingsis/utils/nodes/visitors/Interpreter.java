/*
 * My Project
 */

package com.ingsis.utils.nodes.visitors;

import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.result.Result;

public interface Interpreter {
  Result<String> interpret(IfKeywordNode ifKeywordNode);

  Result<String> interpret(DeclarationKeywordNode declarationKeywordNode);

  Result<Object> interpret(ExpressionNode expressionNode);
}
