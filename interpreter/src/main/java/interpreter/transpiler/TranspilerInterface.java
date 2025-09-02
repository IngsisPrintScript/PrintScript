package interpreter.transpiler;


import nodes.common.Node;
import results.Result;

public interface TranspilerInterface {
    Result<String> transpile(Node tree);
}
