/*
 * My Project
 */

package com.ingsis.engine.factories.semantic;

import static org.junit.jupiter.api.Assertions.*;

import com.ingsis.result.factory.DefaultResultFactory;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.semantic.SemanticChecker;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.Queue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaultSemanticFactoryTest {
    private DefaultSemanticFactory factory;

    @BeforeEach
    void setup() {
        factory =
                new DefaultSemanticFactory(
                        new com.ingsis.engine.factories.syntactic.SyntacticFactory() {
                            @Override
                            public com.ingsis.syntactic.SyntacticParser createCliSyntacticChecker(
                                    Queue<Character> buffer) {
                                return new com.ingsis.syntactic.SyntacticParser() {
                                    @Override
                                    public com.ingsis.result.Result<? extends com.ingsis.nodes.Node>
                                            parse() {
                                        return new com.ingsis.result.IncorrectResult<>("no");
                                    }

                                    @Override
                                    public boolean hasNext() {
                                        return false;
                                    }

                                    @Override
                                    public com.ingsis.visitors.Checkable next() {
                                        throw new NoSuchElementException();
                                    }

                                    @Override
                                    public com.ingsis.visitors.Checkable peek() {
                                        throw new NoSuchElementException();
                                    }
                                };
                            }

                            @Override
                            public com.ingsis.syntactic.SyntacticParser createFileSyntacticChecker(
                                    Path filePath) throws IOException {
                                return createCliSyntacticChecker(new ArrayDeque<>());
                            }
                        },
                        new DefaultResultFactory());
    }

    @Test
    void createCliSemanticCheckerReturnsNotNull() {
        SemanticChecker checker =
                factory.createCliSemanticChecker(new ArrayDeque<>(), DefaultRuntime.getInstance());
        assertNotNull(checker);
    }

    @Test
    void createFileSemanticCheckerReturnsNotNull() throws IOException {
        SemanticChecker checker =
                factory.createFileSemanticChecker(
                        Path.of("/tmp/some.ps"), DefaultRuntime.getInstance());
        assertNotNull(checker);
    }
}
