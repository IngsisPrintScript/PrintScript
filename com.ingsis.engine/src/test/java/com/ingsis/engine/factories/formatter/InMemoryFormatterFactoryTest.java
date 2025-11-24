/*
 * My Project
 */

package com.ingsis.engine.factories.formatter;

import static org.junit.jupiter.api.Assertions.*;

import com.ingsis.engine.factories.semantic.SemanticFactory;
import com.ingsis.formatter.ProgramFormatter;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.rule.observer.EventsChecker;
import com.ingsis.runtime.Runtime;
import com.ingsis.semantic.SemanticChecker;
import com.ingsis.visitors.Checkable;
import com.ingsis.visitors.Interpretable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Test;

class InMemoryFormatterFactoryTest {

    @Test
    void formatConcatenatesCorrectResults() throws IOException {
        InMemoryFormatterFactory factory = new InMemoryFormatterFactory();

        // Prepare semantic factory that will return a semantic checker yielding two checkables
        SemanticFactory semanticFactory =
                new SemanticFactory() {
                    @Override
                    public com.ingsis.semantic.SemanticChecker createCliSemanticChecker(
                            java.util.Queue<Character> buffer, Runtime runtime) {
                        throw new UnsupportedOperationException();
                    }

                    @Override
                    public SemanticChecker createFileSemanticChecker(
                            Path filePath, Runtime runtime) {
                        List<Interpretable> list = new ArrayList<>();
                        list.add(new MockNode("A", true));
                        list.add(new MockNode("B", true));
                        return new ListSemanticChecker(list);
                    }
                };

        EventsChecker eventsChecker = new EventsChecker(() -> new HashMap<>());
        ProgramFormatter formatter =
                factory.createFormatter(semanticFactory, Path.of("file.ps"), null, eventsChecker);

        Result<String> result = formatter.format();
        assertTrue(result.isCorrect());
        assertEquals("AB", result.result());
    }

    @Test
    void formatReturnsIncorrectWhenAnyNodeFails() throws IOException {
        InMemoryFormatterFactory factory = new InMemoryFormatterFactory();

        SemanticFactory semanticFactory =
                new SemanticFactory() {
                    @Override
                    public com.ingsis.semantic.SemanticChecker createCliSemanticChecker(
                            java.util.Queue<Character> buffer, Runtime runtime) {
                        throw new UnsupportedOperationException();
                    }

                    @Override
                    public SemanticChecker createFileSemanticChecker(
                            Path filePath, Runtime runtime) {
                        List<Interpretable> list = new ArrayList<>();
                        list.add(new MockNode("X", true));
                        list.add(new MockNode("ERR", false));
                        list.add(new MockNode("Y", true));
                        return new ListSemanticChecker(list);
                    }
                };

        EventsChecker eventsChecker = new EventsChecker(() -> new HashMap<>());
        ProgramFormatter formatter =
                factory.createFormatter(semanticFactory, Path.of("file.ps"), null, eventsChecker);

        Result<String> result = formatter.format();
        assertFalse(result.isCorrect());
        assertNotNull(result.error());
    }

    // Simple mock node that implements both Interpretable and Checkable. The formatter will call
    // acceptChecker.
    private static final class MockNode implements Interpretable, Checkable {
        private final String value;
        private final boolean correct;

        MockNode(String value, boolean correct) {
            this.value = value;
            this.correct = correct;
        }

        @Override
        public Result<String> acceptInterpreter(com.ingsis.visitors.Interpreter interpreter) {
            return new CorrectResult<>(value);
        }

        @Override
        public Result<String> acceptChecker(com.ingsis.visitors.Checker checker) {
            if (correct) {
                return new CorrectResult<>(value);
            }
            return new IncorrectResult<>("fail: " + value);
        }
    }

    // Minimal SemanticChecker implementation backed by a list
    private static final class ListSemanticChecker implements SemanticChecker {
        private final List<Interpretable> list;
        private int index = 0;

        ListSemanticChecker(List<Interpretable> list) {
            this.list = List.copyOf(list);
        }

        @Override
        public boolean hasNext() {
            return index < list.size();
        }

        @Override
        public Interpretable next() {
            return list.get(index++);
        }

        @Override
        public Interpretable peek() {
            return list.get(index);
        }

        @Override
        public Result<Interpretable> parse() {
            return new IncorrectResult<>("parse not implemented");
        }
    }
}
