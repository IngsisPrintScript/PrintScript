package com.ingsis.parser.syntactic.parsers.operator.algorithms.postfix;

import java.util.Queue;

import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.process.checkpoint.ProcessCheckpoint;
import com.ingsis.utils.token.Token;

public interface PostfixToAstBuilder {
  ProcessCheckpoint<Token, ExpressionNode> build(Parser<ExpressionNode> leafExpressionNodesParser,
      Queue<Token> postfixTokens);
}
