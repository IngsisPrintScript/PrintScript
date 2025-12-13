/*
 * My Project
 */

package com.ingsis.utils.nodes.expressions;

public sealed interface LiteralNode extends ExpressionNode
        permits NumberLiteralNode, StringLiteralNode, BooleanLiteralNode {
    Object value();
}
