/*
 * My Project
 */

package com.ingsis.utils.nodes.expressions;

import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.result.Result;
import java.util.List;

public sealed interface ExpressionNode extends Node, Interpretable
    permits LiteralNode,
    IdentifierNode,
    CallFunctionNode,
    OperatorNode,
    NilExpressionNode,
    GlobalFunctionBody, BlockNode {

  List<ExpressionNode> children();

  Result<Object> solve();

  String symbol();
}
