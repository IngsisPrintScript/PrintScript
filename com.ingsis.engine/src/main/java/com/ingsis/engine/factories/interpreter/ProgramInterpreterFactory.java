/*
 * My Project
 */

package com.ingsis.engine.factories.interpreter;

import com.ingsis.interpreter.ProgramInterpreter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public interface ProgramInterpreterFactory {
  ProgramInterpreter fromInputStream(InputStream in) throws IOException;

  ProgramInterpreter fromFile(Path path) throws IOException;

  ProgramInterpreter fromString(CharSequence input) throws IOException;
}
