/*
 * My Project
 */

package com.ingsis.interpreter.factory;

import com.ingsis.interpreter.ProgramInterpreter;
import java.io.InputStream;

public interface ProgramInterpreterFactory {
    ProgramInterpreter fromInputStream(InputStream in);
}
