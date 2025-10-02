/*
 * My Project
 */

package com.ingsis.engine.factories.interpreter;

import com.ingsis.interpreter.ProgramInterpreter;
import com.ingsis.runtime.Runtime;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Queue;

public interface ProgramInterpreterFactory {
    ProgramInterpreter createCliProgramInterpreter(Queue<Character> buffer, Runtime runtime);

    ProgramInterpreter createFileProgramInterpreter(Path filePath, Runtime runtime)
            throws IOException;
}
