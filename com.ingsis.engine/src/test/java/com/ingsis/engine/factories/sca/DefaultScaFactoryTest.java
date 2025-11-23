package com.ingsis.engine.factories.sca;

import static org.junit.jupiter.api.Assertions.*;

import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.runtime.Runtime;
import com.ingsis.semantic.SemanticChecker;
import com.ingsis.sca.ProgramSca;
import java.io.IOException;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.ArrayDeque;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaultScaFactoryTest {
    private DefaultScaFactory factory;

    @BeforeEach
    void setup() {
        factory = new DefaultScaFactory();
    }

    @Test
    void createScaReturnsProgramSca() throws IOException {
        com.ingsis.engine.factories.semantic.SemanticFactory semFactory =
                new com.ingsis.engine.factories.semantic.SemanticFactory() {
                    @Override
                    public SemanticChecker createCliSemanticChecker(Queue<Character> buffer, Runtime runtime) {
                        return new SemanticChecker() {
                            @Override
                            public com.ingsis.result.Result<com.ingsis.visitors.Interpretable> parse() {
                                return new com.ingsis.result.IncorrectResult<>("no");
                            }

                            @Override
                            public com.ingsis.visitors.Interpretable peek() {
                                throw new NoSuchElementException();
                            }

                            @Override
                            public boolean hasNext() {
                                return false;
                            }

                            @Override
                            public com.ingsis.visitors.Interpretable next() {
                                throw new NoSuchElementException();
                            }
                        };
                    }

                    @Override
                    public SemanticChecker createFileSemanticChecker(Path filePath, Runtime runtime) throws IOException {
                        return createCliSemanticChecker(new ArrayDeque<>(), runtime);
                    }
                };

        ProgramSca sca = factory.createSca(semFactory, Path.of("nonexistent"), DefaultRuntime.getInstance(), null);
        assertNotNull(sca);
        com.ingsis.result.Result<String> res = sca.analyze();
        assertTrue(res.isCorrect());
    }
}
