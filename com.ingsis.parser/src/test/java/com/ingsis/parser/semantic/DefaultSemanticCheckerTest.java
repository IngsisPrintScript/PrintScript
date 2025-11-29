/*
 * My Project
 */

package com.ingsis.parser.semantic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.runtime.Runtime;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.peekableiterator.PeekableIterator;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
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
        public Result<String> acceptInterpreter(Interpreter interpreter) {
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
                            public Result<String> check(IfKeywordNode ifKeywordNode) {
                                return new CorrectResult<>("ok");
                            }

                            @Override
                            public Result<String> check(
                                    DeclarationKeywordNode declarationKeywordNode) {
                                return new CorrectResult<>("ok");
                            }

                            @Override
                            public Result<String> check(ExpressionNode expressionNode) {
                                return new CorrectResult<>("ok");
                            }
                        },
                        runtime);

        Result<com.ingsis.utils.nodes.visitors.Interpretable> result = checker.parse();

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
                            public Result<String> check(IfKeywordNode ifKeywordNode) {
                                return new IncorrectResult<>("bad");
                            }

                            @Override
                            public Result<String> check(
                                    DeclarationKeywordNode declarationKeywordNode) {
                                return new IncorrectResult<>("bad");
                            }

                            @Override
                            public Result<String> check(ExpressionNode expressionNode) {
                                return new IncorrectResult<>("bad");
                            }
                        },
                        runtime);

        Result<com.ingsis.utils.nodes.visitors.Interpretable> result = checker.parse();

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
                            public Result<String> check(IfKeywordNode ifKeywordNode) {
                                return new CorrectResult<>("ok");
                            }

                            @Override
                            public Result<String> check(
                                    DeclarationKeywordNode declarationKeywordNode) {
                                return new CorrectResult<>("ok");
                            }

                            @Override
                            public Result<String> check(ExpressionNode expressionNode) {
                                return new CorrectResult<>("ok");
                            }
                        },
                        runtime);

        assertTrue(checker.hasNext());
        com.ingsis.utils.nodes.visitors.Interpretable p = checker.peek();
        assertNotNull(p);
        com.ingsis.utils.nodes.visitors.Interpretable n = checker.next();
        assertNotNull(n);
        assertFalse(checker.hasNext());
        assertThrows(NoSuchElementException.class, checker::peek);
        assertThrows(NoSuchElementException.class, checker::next);
    }
}
