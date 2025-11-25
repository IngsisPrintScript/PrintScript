/*
 * My Project
 */

package com.ingsis.engine.factories.interpreter;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ingsis.interpreter.ProgramInterpreter;
import com.ingsis.interpreter.visitor.factory.InterpreterVisitorFactory;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.Result;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.runtime.Runtime;
import com.ingsis.semantic.SemanticChecker;
import com.ingsis.visitors.Interpretable;
import com.ingsis.visitors.Interpreter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.Queue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaultProgramInterpreterFactoryTest {
    private DefaultProgramInterpreterFactory factory;

    @BeforeEach
    void setup() {
        factory =
                new DefaultProgramInterpreterFactory(
                        createSemanticFactoryStub(), createInterpreterVisitorFactoryStub());
    }

    private com.ingsis.engine.factories.semantic.SemanticFactory createSemanticFactoryStub() {
        return new com.ingsis.engine.factories.semantic.SemanticFactory() {
            @Override
            public SemanticChecker createCliSemanticChecker(
                    Queue<Character> buffer, Runtime runtime) {
                return new SemanticChecker() {
                    @Override
                    public Result<Interpretable> parse() {
                        return new CorrectResult<>(null);
                    }

                    @Override
                    public Interpretable peek() {
                        throw new NoSuchElementException();
                    }

                    @Override
                    public boolean hasNext() {
                        return false;
                    }

                    @Override
                    public Interpretable next() {
                        throw new NoSuchElementException();
                    }
                };
            }

            @Override
            public SemanticChecker createFileSemanticChecker(Path filePath, Runtime runtime)
                    throws IOException {
                return createCliSemanticChecker(new ArrayDeque<>(), runtime);
            }
        };
    }

    private InterpreterVisitorFactory createInterpreterVisitorFactoryStub() {
        return new InterpreterVisitorFactory() {
            @Override
            public Interpreter createDefaultInterpreter(Runtime runtime) {
                return new Interpreter() {
                    @Override
                    public Result<String> interpret(
                            com.ingsis.nodes.keyword.IfKeywordNode ifKeywordNode) {
                        return new CorrectResult<>("");
                    }

                    @Override
                    public Result<String> interpret(
                            com.ingsis.nodes.keyword.DeclarationKeywordNode
                                    declarationKeywordNode) {
                        return new CorrectResult<>("");
                    }

                    @Override
                    public Result<Object> interpret(
                            com.ingsis.nodes.expression.ExpressionNode expressionNode) {
                        return new CorrectResult<>(null);
                    }
                };
            }
        };
    }

    @Test
    void createCliProgramInterpreterReturnsNotNull() {
        ProgramInterpreter pi =
                factory.createCliProgramInterpreter(
                        new ArrayDeque<>(), DefaultRuntime.getInstance());
        assertNotNull(pi);
    }

    @Test
    void createFileProgramInterpreterReturnsNotNull() throws IOException {
        ProgramInterpreter pi =
                factory.createFileProgramInterpreter(
                        Path.of("/tmp/nonexistent.ps"), DefaultRuntime.getInstance());
        assertNotNull(pi);
    }
}
