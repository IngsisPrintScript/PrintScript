/*
 * My Project
 */

package com.ingsis.utils.evalstate.factories;

import com.ingsis.utils.evalstate.EvalState;
import com.ingsis.utils.evalstate.io.InputSupplier;
import com.ingsis.utils.evalstate.io.OutputEmitter;

public interface EvalStateFactory {
    EvalState create(OutputEmitter emitter, InputSupplier supplier);
}
