package interpreter;

import nodes.common.Node;
import results.Result;

public interface InterpreterInterface {
  Result<String> interpret(Node tree);
}
