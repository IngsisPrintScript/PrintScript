package expression;

import common.Node;
import results.Result;
import visitor.SemanticallyCheckable;


public interface ExpressionNode extends Node, SemanticallyCheckable {
    Result<Object> evaluate();
    Result<String> prettyPrint();
}
