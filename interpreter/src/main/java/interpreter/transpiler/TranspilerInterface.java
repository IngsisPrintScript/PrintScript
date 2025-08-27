package interpreter.transpiler;


import common.Node;
import common.responses.Result;

public interface TranspilerInterface {
    Result transpile(Node tree);
}
