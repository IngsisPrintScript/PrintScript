package compiler.transpiler;


import common.Node;
import results.Result;

public interface TranspilerInterface {
    Result<String> transpile(Node tree);
}
