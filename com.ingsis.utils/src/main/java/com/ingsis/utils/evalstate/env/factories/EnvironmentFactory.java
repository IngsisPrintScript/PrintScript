/*
 * My Project
 */

package com.ingsis.utils.evalstate.env.factories;

import com.ingsis.utils.evalstate.env.Environment;
import com.ingsis.utils.evalstate.io.InputSupplier;
import com.ingsis.utils.evalstate.io.OutputEmitter;

public sealed interface EnvironmentFactory permits DefaultEnviromentFactory {
  Environment createRoot(OutputEmitter emitter, InputSupplier supplier);
}
