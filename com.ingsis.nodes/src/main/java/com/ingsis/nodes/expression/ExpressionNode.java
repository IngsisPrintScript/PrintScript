/*
 * My Project
 */

package com.ingsis.nodes.expression;

import com.ingsis.nodes.Node;
import java.util.Collection;

public interface ExpressionNode extends Node {
    Collection<ExpressionNode> children();
}
