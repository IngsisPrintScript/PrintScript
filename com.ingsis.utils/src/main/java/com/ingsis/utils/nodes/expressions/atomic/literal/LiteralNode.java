/*
 * My Project
 */

package com.ingsis.utils.nodes.expressions.atomic.literal;

import com.ingsis.utils.nodes.expressions.ExpressionNode;

public sealed interface LiteralNode extends ExpressionNode
    permits NumberLiteralNode, StringLiteralNode, BooleanLiteralNode {
  Object value();
}
