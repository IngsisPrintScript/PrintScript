package interpreter;


import common.Node;
import common.responses.Result;

public interface InterpreterInterface {
    Result interpret(Node tree);
}
