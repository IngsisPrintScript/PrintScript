/*
 * My Project
 */

package com.ingsis.engine.factories.sca;

import com.ingsis.engine.factories.semantic.SemanticFactory;
import com.ingsis.runtime.Runtime;
import com.ingsis.sca.ProgramSca;
import com.ingsis.utils.rule.observer.EventsChecker;
import java.io.IOException;
import java.nio.file.Path;

public interface ScaFactory {
    public ProgramSca fromFile(
            SemanticFactory semanticFactory,
            Path path,
            Runtime runtime,
            EventsChecker eventsChecker)
            throws IOException;
}
