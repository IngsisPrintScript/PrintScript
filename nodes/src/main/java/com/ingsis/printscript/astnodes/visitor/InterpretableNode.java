/*
 * My Project
 */

package com.ingsis.printscript.astnodes.visitor;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.results.Result;

public interface InterpretableNode extends SemanticallyCheckable, Node {
    Result<String> acceptInterpreter(InterpretVisitorInterface interpreter);
}
