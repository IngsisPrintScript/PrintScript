/*
 * My Project
 */

package com.ingsis.nodes.expression;

import com.ingsis.nodes.Node;
import com.ingsis.visitors.Checkable;
import com.ingsis.visitors.Interpretable;
import java.util.List;

public interface ExpressionNode extends Node, Checkable, Interpretable {
    List<ExpressionNode> children();
}
