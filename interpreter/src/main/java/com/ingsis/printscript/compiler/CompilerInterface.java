/*
 * My Project
 */

package com.ingsis.printscript.compiler;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.results.Result;

public interface CompilerInterface {
    Result<String> compile(Node tree);
}
