/*
 * My Project
 */

package com.ingsis.nodes.expression;

import com.ingsis.nodes.Node;
import java.util.List;

public interface ExpressionNode extends Node {
    List<ExpressionNode> children();
}
