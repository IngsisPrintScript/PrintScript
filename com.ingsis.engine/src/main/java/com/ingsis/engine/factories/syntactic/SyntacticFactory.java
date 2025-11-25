/*
 * My Project
 */

package com.ingsis.engine.factories.syntactic;

import com.ingsis.syntactic.SyntacticParser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public interface SyntacticFactory {
  SyntacticParser fromInputStream(InputStream in) throws IOException;

  SyntacticParser fromFile(Path path) throws IOException;

  SyntacticParser fromString(CharSequence input) throws IOException;
}
