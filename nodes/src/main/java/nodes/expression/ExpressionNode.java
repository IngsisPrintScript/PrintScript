package nodes.expression;

import nodes.common.Node;
import nodes.visitor.SemanticallyCheckable;
import results.Result;

public interface ExpressionNode extends Node, SemanticallyCheckable {
  Result<Object> evaluate();

  Result<String> prettyPrint();
}
