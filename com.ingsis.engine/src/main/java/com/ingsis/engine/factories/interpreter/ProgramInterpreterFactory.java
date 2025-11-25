/*
 * My Project
 */

package com.ingsis.engine.factories.interpreter;

import com.ingsis.interpreter.ProgramInterpreter;
import com.ingsis.runtime.Runtime;
import java.io.IOException;
import java.nio.file.Path;

public interface ProgramInterpreterFactory {

    ProgramInterpreter createCliProgramInterpreter(String input, Runtime runtime)
            throws IOException;

    ProgramInterpreter createFileProgramInterpreter(Path filePath, Runtime runtime)
            throws IOException;

    ProgramInterpreter createReplProgramInterpreter(Runtime runtime) throws IOException;
}
