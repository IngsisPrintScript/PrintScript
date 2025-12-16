/*
 * My Project
 */

package com.ingsis.utils.nodes.expressions;

import com.ingsis.utils.evalstate.EvalState;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.visitors.InterpretResult;
import java.util.List;

public sealed interface ExpressionNode extends Node
        permits LiteralNode,
                IdentifierNode,
                CallFunctionNode,
                OperatorNode,
                NilExpressionNode,
                BlockNode {

    List<ExpressionNode> children();

    InterpretResult solve(EvalState evalState);

    String symbol();
}
