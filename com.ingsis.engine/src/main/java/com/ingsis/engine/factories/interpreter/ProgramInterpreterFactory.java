/*
 * My Project
 */

package com.ingsis.engine.factories.interpreter;

import com.ingsis.interpreter.ProgramInterpreter;
import java.io.InputStream;

public interface ProgramInterpreterFactory {
    ProgramInterpreter fromInputStream(InputStream in);
}
