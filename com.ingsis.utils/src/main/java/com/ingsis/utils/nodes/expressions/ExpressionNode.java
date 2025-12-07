/*
 * My Project
 */

package com.ingsis.utils.nodes.expressions;

import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.result.Result;

import java.util.List;

public interface ExpressionNode extends Node, Interpretable {
  List<ExpressionNode> children();

  Result<Object> solve();

  String symbol();
}
