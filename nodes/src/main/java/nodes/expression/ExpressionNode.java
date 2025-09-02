package nodes.expression;

import nodes.common.Node;
import results.Result;
import nodes.visitor.SemanticallyCheckable;


public interface ExpressionNode extends Node, SemanticallyCheckable {
    Result<Object> evaluate();
    Result<String> prettyPrint();
}
