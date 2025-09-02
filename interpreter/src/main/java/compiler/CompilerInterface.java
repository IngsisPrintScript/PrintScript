package compiler;


import common.Node;
import results.Result;

public interface CompilerInterface {
    Result<String> compile(Node tree);
}
