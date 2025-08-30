package interpreter.transpiler;


import common.Node;
import responses.Result;

public interface TranspilerInterface {
    Result<String> transpile(Node tree);
}
