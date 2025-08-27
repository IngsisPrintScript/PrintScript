package interpreter.transpiler;


import common.Node;
import responses.Result;

public interface TranspilerInterface {
    Result transpile(Node tree);
}
