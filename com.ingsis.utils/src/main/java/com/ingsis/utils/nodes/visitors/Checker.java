/*
 * My Project
 */

package com.ingsis.utils.nodes.visitors;

import com.ingsis.utils.evalstate.env.semantic.SemanticEnvironment;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.keyword.IfKeywordNode;

public interface Checker {
    CheckResult check(IfKeywordNode ifKeywordNode, SemanticEnvironment env);

    CheckResult check(DeclarationKeywordNode declarationKeywordNode, SemanticEnvironment env);

    CheckResult check(ExpressionNode expressionNode, SemanticEnvironment env);
}
