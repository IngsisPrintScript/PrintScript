/*
 * My Project
 */

package com.ingsis.utils.nodes.visitors; /*
                                          * My Project
                                          */

import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.result.Result;

public interface Checker {
    Result<String> check(IfKeywordNode ifKeywordNode);

    Result<String> check(DeclarationKeywordNode declarationKeywordNode);

    Result<String> check(ExpressionNode expressionNode);
}
