package interpreter;

import common.Node;
import results.Result;

public interface InterpreterInterface {
    Result<String> interpreter(Node tree);
}
