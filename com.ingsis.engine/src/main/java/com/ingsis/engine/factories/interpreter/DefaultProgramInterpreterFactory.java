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

public final class DefaultProgramInterpreterFactory implements ProgramInterpreterFactory {
    private final SemanticFactory semanticFactory;
    private final InterpreterVisitorFactory interpreterFactory;

    public DefaultProgramInterpreterFactory(
            SemanticFactory semanticFactory, InterpreterVisitorFactory interpreterFactory) {
        this.semanticFactory = semanticFactory;
        this.interpreterFactory = interpreterFactory;
    }

    @Override
    public ProgramInterpreter createCliProgramInterpreter(String input, Runtime runtime)
            throws IOException {
        return new DefaultProgramInterpreter(
                semanticFactory.createCliSemanticChecker(input, runtime),
                interpreterFactory.createDefaultInterpreter(runtime));
    }

    @Override
    public ProgramInterpreter createFileProgramInterpreter(Path filePath, Runtime runtime)
            throws IOException {
        return new DefaultProgramInterpreter(
                semanticFactory.createFileSemanticChecker(filePath, runtime),
                interpreterFactory.createDefaultInterpreter(runtime));
    }

    @Override
    public ProgramInterpreter createReplProgramInterpreter(Runtime runtime) throws IOException {
        return new DefaultProgramInterpreter(
                semanticFactory.createReplSemanticChecker(runtime),
                interpreterFactory.createDefaultInterpreter(runtime));
    }
}
