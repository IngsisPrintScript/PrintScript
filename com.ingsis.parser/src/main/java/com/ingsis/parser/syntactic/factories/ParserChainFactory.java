/*
 * My Project
 */

package com.ingsis.syntactic.factories;

import com.ingsis.nodes.Node;
import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.syntactic.parsers.Parser;

public interface ParserChainFactory {
    Parser<Node> createDefaultChain();

    Parser<ExpressionNode> createExpressionChain();
}
