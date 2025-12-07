/*
 * My Project
 */

package com.ingsis.parser.syntactic.factories;

import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.expressions.ExpressionNode;

public interface ParserChainFactory {
  Parser<Node> createDefaultChain();

  Parser<ExpressionNode> createExpressionChain();
}
