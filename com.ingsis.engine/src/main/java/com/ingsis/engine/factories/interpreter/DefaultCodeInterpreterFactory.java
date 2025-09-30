/*
 * My Project
 */

package com.ingsis.engine.factories.interpreter;

import com.ingsis.engine.factories.semantic.SemanticFactory;
import com.ingsis.interpreter.DefaultProgramInterpreter;
import com.ingsis.interpreter.ProgramInterpreter;
import com.ingsis.interpreter.visitor.factory.InterpreterVisitorFactory;
import com.ingsis.runtime.Runtime;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Queue;

public final class DefaultCodeInterpreterFactory implements CodeInterpreterFactory {
    private final SemanticFactory semanticFactory;
    private final InterpreterVisitorFactory interpreterFactory;

    public DefaultCodeInterpreterFactory(
            SemanticFactory semanticFactory, InterpreterVisitorFactory interpreterFactory) {
        this.semanticFactory = semanticFactory;
        this.interpreterFactory = interpreterFactory;
    }

    @Override
    public ProgramInterpreter createCliProgramInterpreter(
            Queue<Character> buffer, Runtime runtime) {
        return new DefaultProgramInterpreter(
                semanticFactory.createCliSemanticChecker(buffer),
                interpreterFactory.createDefaultInterpreter(runtime));
    }

    @Override
    public ProgramInterpreter createFileProgramInterpreter(Path filePath, Runtime runtime)
            throws IOException {
        return new DefaultProgramInterpreter(
                semanticFactory.createFileSemanticChecker(filePath),
                interpreterFactory.createDefaultInterpreter(runtime));
    }
}
