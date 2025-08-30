package expression;

import common.Node;
import responses.Result;


public interface ExpressionNode extends Node {
    Result<Object> evaluate();
    Result<String> prettyPrint();
}
