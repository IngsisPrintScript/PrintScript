/*
 * My Project
 */

package com.ingsis.utils.nodes.visitors;

import com.ingsis.utils.evalstate.EvalState;

public interface Interpretable {
    InterpretResult acceptInterpreter(Interpreter interpreter, EvalState evalState);
}
