package com.ingsis.printscript.compiler.transpiler;


import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.results.Result;

public interface TranspilerInterface {
    Result<String> transpile(Node tree);
}
