package interpreter;


import common.Node;
import responses.Result;

public interface InterpreterInterface {
    Result interpret(Node tree);
}
