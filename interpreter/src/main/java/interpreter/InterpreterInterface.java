package interpreter;

import common.nodes.Node;
import common.responses.Result;

public interface InterpreterInterface {
    Result interpret(Node tree);
}
