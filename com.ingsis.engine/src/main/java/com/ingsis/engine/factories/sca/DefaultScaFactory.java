/*
 * My Project
 */

package com.ingsis.engine.factories.sca;

import com.ingsis.engine.factories.semantic.SemanticFactory;
import com.ingsis.rule.observer.EventsChecker;
import com.ingsis.runtime.Runtime;
import com.ingsis.sca.InMemoryProgramSca;
import com.ingsis.sca.ProgramSca;
import java.io.IOException;
import java.nio.file.Path;

public class DefaultScaFactory implements ScaFactory {
    @Override
    public ProgramSca createSca(
            SemanticFactory semanticFactory,
            Path filePath,
            Runtime runtime,
            EventsChecker eventsChecker)
            throws IOException {
        return new InMemoryProgramSca(
                semanticFactory.createFileSemanticChecker(filePath, runtime), eventsChecker);
    }
}
