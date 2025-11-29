/*
 * My Project
 */

package com.ingsis.engine.factories.interpreter;

import com.ingsis.engine.factories.semantic.SemanticFactory;
import com.ingsis.interpreter.DefaultProgramInterpreter;
import com.ingsis.interpreter.ProgramInterpreter;
import com.ingsis.interpreter.visitor.factory.InterpreterVisitorFactory;
import com.ingsis.runtime.Runtime;
import com.ingsis.utils.nodes.visitors.Interpreter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public final class DefaultProgramInterpreterFactory implements ProgramInterpreterFactory {
    private final SemanticFactory semanticFactory;
    private final Interpreter interpreter;

    public DefaultProgramInterpreterFactory(
            SemanticFactory semanticFactory,
            InterpreterVisitorFactory interpreterFactory,
            Runtime runtime) {
        this.semanticFactory = semanticFactory;
        this.interpreter = interpreterFactory.createDefaultInterpreter(runtime);
    }

    @Override
    public ProgramInterpreter fromInputStream(InputStream in) throws IOException {
        return new DefaultProgramInterpreter(semanticFactory.fromInputStream(in), interpreter);
    }

    @Override
    public ProgramInterpreter fromFile(Path path) throws IOException {
        return new DefaultProgramInterpreter(semanticFactory.fromFile(path), interpreter);
    }

    @Override
    public ProgramInterpreter fromString(CharSequence input) throws IOException {
        return new DefaultProgramInterpreter(semanticFactory.fromString(input), interpreter);
    }
}
