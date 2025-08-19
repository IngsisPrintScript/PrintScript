package interpreter.transpiler;

import common.nodes.Node;
import common.responses.Result;

public interface TranspilerInterface {
    Result transpile(Node tree);
}
