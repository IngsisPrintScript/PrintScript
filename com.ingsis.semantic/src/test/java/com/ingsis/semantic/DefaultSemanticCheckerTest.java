/*
 * My Project
 */

package com.ingsis.semantic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.peekableiterator.PeekableIterator;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.runtime.Runtime;
import com.ingsis.visitors.Checkable;
import com.ingsis.visitors.Checker;
import com.ingsis.visitors.Interpretable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

public class DefaultSemanticCheckerTest {

    private static final class SimplePeekable<T> implements PeekableIterator<T> {
        private final java.util.Iterator<T> delegate;
        private T cached = null;

        SimplePeekable(List<T> items) {
            this.delegate = items.iterator();
        }

        @Override
        public T peek() {
            if (cached == null) {
                if (!delegate.hasNext()) throw new NoSuchElementException();
                cached = delegate.next();
            }
            return cached;
        }

        @Override
        public boolean hasNext() {
            return cached != null || delegate.hasNext();
        }

        @Override
        public T next() {
            if (cached != null) {
                T r = cached;
                cached = null;
                return r;
            }
            return delegate.next();
        }
    }

    private static final class TestCheckable implements Checkable, Interpretable {
        private final Result<String> checkResult;

        TestCheckable(Result<String> checkResult) {
            this.checkResult = checkResult;
        }

        @Override
        public Result<String> acceptChecker(Checker checker) {
            return checkResult;
        }

        @Override
        public com.ingsis.result.Result<String> acceptInterpreter(
                com.ingsis.visitors.Interpreter interpreter) {
            return new CorrectResult<>("ok");
        }
    }

    @Test
    void parseWhenCheckerReturnsCorrectShouldReturnCorrectResult() {
        Runtime runtime = DefaultRuntime.getInstance();
        List<Checkable> items = new ArrayList<>();
        TestCheckable tc = new TestCheckable(new CorrectResult<>("ok"));
        items.add(tc);
        PeekableIterator<Checkable> it = new SimplePeekable<>(items);

        DefaultSemanticChecker checker =
                new DefaultSemanticChecker(
                        it,
                        new Checker() {
                            @Override
                            public Result<String> check(
                                    com.ingsis.nodes.keyword.IfKeywordNode ifKeywordNode) {
                                return new CorrectResult<>("ok");
                            }

                            @Override
                            public Result<String> check(
                                    com.ingsis.nodes.keyword.DeclarationKeywordNode
                                            declarationKeywordNode) {
                                return new CorrectResult<>("ok");
                            }

                            @Override
                            public Result<String> check(
                                    com.ingsis.nodes.expression.ExpressionNode expressionNode) {
                                return new CorrectResult<>("ok");
                            }
                        },
                        runtime);

        Result<com.ingsis.visitors.Interpretable> result = checker.parse();

        assertTrue(result.isCorrect());
        assertInstanceOf(CorrectResult.class, result);
    }

    @Test
    void parseWhenCheckerReturnsIncorrectShouldReturnIncorrectResult() {
        Runtime runtime = DefaultRuntime.getInstance();
        List<Checkable> items = new ArrayList<>();
        TestCheckable tc = new TestCheckable(new IncorrectResult<>("bad"));
        items.add(tc);
        PeekableIterator<Checkable> it = new SimplePeekable<>(items);

        DefaultSemanticChecker checker =
                new DefaultSemanticChecker(
                        it,
                        new Checker() {
                            @Override
                            public Result<String> check(
                                    com.ingsis.nodes.keyword.IfKeywordNode ifKeywordNode) {
                                return new IncorrectResult<>("bad");
                            }

                            @Override
                            public Result<String> check(
                                    com.ingsis.nodes.keyword.DeclarationKeywordNode
                                            declarationKeywordNode) {
                                return new IncorrectResult<>("bad");
                            }

                            @Override
                            public Result<String> check(
                                    com.ingsis.nodes.expression.ExpressionNode expressionNode) {
                                return new IncorrectResult<>("bad");
                            }
                        },
                        runtime);

        Result<com.ingsis.visitors.Interpretable> result = checker.parse();

        assertFalse(result.isCorrect());
        assertInstanceOf(IncorrectResult.class, result);
    }

    @Test
    void iterationFlowPeekNextAndExceptions() {
        Runtime runtime = DefaultRuntime.getInstance();
        List<Checkable> items = new ArrayList<>();
        TestCheckable tc = new TestCheckable(new CorrectResult<>("ok"));
        items.add(tc);
        PeekableIterator<Checkable> it = new SimplePeekable<>(items);

        DefaultSemanticChecker checker =
                new DefaultSemanticChecker(
                        it,
                        new Checker() {
                            @Override
                            public Result<String> check(
                                    com.ingsis.nodes.keyword.IfKeywordNode ifKeywordNode) {
                                return new CorrectResult<>("ok");
                            }

                            @Override
                            public Result<String> check(
                                    com.ingsis.nodes.keyword.DeclarationKeywordNode
                                            declarationKeywordNode) {
                                return new CorrectResult<>("ok");
                            }

                            @Override
                            public Result<String> check(
                                    com.ingsis.nodes.expression.ExpressionNode expressionNode) {
                                return new CorrectResult<>("ok");
                            }
                        },
                        runtime);

        assertTrue(checker.hasNext());
        com.ingsis.visitors.Interpretable p = checker.peek();
        assertNotNull(p);
        com.ingsis.visitors.Interpretable n = checker.next();
        assertNotNull(n);
        assertFalse(checker.hasNext());
        assertThrows(NoSuchElementException.class, checker::peek);
        assertThrows(NoSuchElementException.class, checker::next);
    }
}
