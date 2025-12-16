/*
 * My Project
 */

package com.ingsis.utils.nodes.visitors;

import com.ingsis.utils.evalstate.EvalState;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.keyword.IfKeywordNode;

public interface Interpreter {
    InterpretResult interpret(IfKeywordNode ifKeywordNode, EvalState evalState);

    InterpretResult interpret(DeclarationKeywordNode declarationKeywordNode, EvalState evalState);

    InterpretResult interpret(ExpressionNode expressionNode, EvalState evalState);
}
