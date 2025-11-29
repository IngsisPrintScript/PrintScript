/*
 * My Project
 */

package com.ingsis.engine.factories.formatter;

import com.ingsis.engine.factories.semantic.SemanticFactory;
import com.ingsis.formatter.ProgramFormatter;
import com.ingsis.runtime.Runtime;
import com.ingsis.utils.rule.observer.EventsChecker;
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
