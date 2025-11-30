/*
 * My Project
 */

package com.ingsis.utils.nodes.nodes.expression; /*
                                                  * My Project
                                                  */

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.nodes.visitors.Interpretable;
import java.util.List;

public interface ExpressionNode extends Node, Checkable, Interpretable {
    List<ExpressionNode> children();

    String symbol();
}
