/*
 * My Project
 */

package com.ingsis.nodes.expression;

import com.ingsis.nodes.Node;
import com.ingsis.visitors.Checkable;
import java.util.List;

public interface ExpressionNode extends Node, Checkable {
    List<ExpressionNode> children();
}
