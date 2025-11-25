/*
 * My Project
 */

package com.ingsis.engine.factories.formatter;

import com.ingsis.engine.factories.semantic.SemanticFactory;
import com.ingsis.formatter.ProgramFormatter;
import com.ingsis.rule.observer.EventsChecker;
import com.ingsis.runtime.Runtime;
import java.io.IOException;
import java.nio.file.Path;

public interface FormatterFactory {
  public ProgramFormatter fromFile(
      SemanticFactory semanticFactory,
      Path path,
      Runtime runtime,
      EventsChecker eventsChecker)
      throws IOException;
}
