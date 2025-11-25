/*
 * My Project
 */

package com.ingsis.sca;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.result.Result;
import com.ingsis.result.factory.DefaultResultFactory;
import com.ingsis.visitors.Checkable;
import com.ingsis.visitors.Checker;
import java.util.Iterator;
import org.junit.jupiter.api.Test;

class InMemoryProgramScaTest {

    static class AlwaysCorrect implements Checkable, com.ingsis.visitors.Interpretable {
        @Override
        public Result<String> acceptChecker(Checker checker) {
            return new DefaultResultFactory().createCorrectResult("ok");
        }

        @Override
        public Result<String> acceptInterpreter(com.ingsis.visitors.Interpreter interpreter) {
            return new DefaultResultFactory().createCorrectResult("ok");
        }
    }

    static class AlwaysIncorrect implements Checkable, com.ingsis.visitors.Interpretable {
        private final Result r;

        AlwaysIncorrect(Result r) {
            this.r = r;
        }

        @Override
        public Result<String> acceptChecker(Checker checker) {
            return r;
        }

        @Override
        public Result<String> acceptInterpreter(com.ingsis.visitors.Interpreter interpreter) {
            return r;
        }
    }

    @Test
    void analyzeReturnsCorrectWhenAllPass() {
        Iterator<com.ingsis.visitors.Interpretable> it =
                java.util.List.<com.ingsis.visitors.Interpretable>of(
                                new AlwaysCorrect(), new AlwaysCorrect())
                        .iterator();
        com.ingsis.peekableiterator.PeekableIterator<com.ingsis.visitors.Interpretable> pk =
                new com.ingsis.peekableiterator.PeekableIterator<>() {
                    private final Iterator<com.ingsis.visitors.Interpretable> inner = it;

                    @Override
                    public com.ingsis.visitors.Interpretable peek() {
                        return inner.hasNext() ? inner.next() : null;
                    }

                    @Override
                    public boolean hasNext() {
                        return inner.hasNext();
                    }

                    @Override
                    public com.ingsis.visitors.Interpretable next() {
                        return inner.next();
                    }
                };
        InMemoryProgramSca sca =
                new InMemoryProgramSca(pk, new com.ingsis.rule.observer.EventsChecker(pf()));
        Result res = sca.analyze();
        assertEquals("Check passed.", res.result());
    }

    @Test
    void analyzeReturnsFirstIncorrect() {
        Result bad = new DefaultResultFactory().createIncorrectResult("bad");
        Result later = new DefaultResultFactory().createIncorrectResult("later");
        Iterator<com.ingsis.visitors.Interpretable> it =
                java.util.List.<com.ingsis.visitors.Interpretable>of(
                                new AlwaysCorrect(),
                                new AlwaysIncorrect(bad),
                                new AlwaysIncorrect(later))
                        .iterator();
        com.ingsis.peekableiterator.PeekableIterator<com.ingsis.visitors.Interpretable> pk2 =
                new com.ingsis.peekableiterator.PeekableIterator<>() {
                    private final Iterator<com.ingsis.visitors.Interpretable> inner = it;

                    @Override
                    public com.ingsis.visitors.Interpretable peek() {
                        return inner.hasNext() ? inner.next() : null;
                    }

                    @Override
                    public boolean hasNext() {
                        return inner.hasNext();
                    }

                    @Override
                    public com.ingsis.visitors.Interpretable next() {
                        return inner.next();
                    }
                };
        InMemoryProgramSca sca =
                new InMemoryProgramSca(pk2, new com.ingsis.rule.observer.EventsChecker(pf()));
        Result res = sca.analyze();
        assertEquals("bad", res.error());
    }

    private static com.ingsis.rule.observer.publishers.factories.PublishersFactory pf() {
        return new com.ingsis.rule.observer.publishers.factories.PublishersFactory() {
            @Override
            public com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<
                            com.ingsis.nodes.keyword.DeclarationKeywordNode>
                    createLetNodePublisher() {
                return new com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<>(
                        java.util.List.of(
                                (com.ingsis.rule.observer.handlers.NodeEventHandler<
                                                com.ingsis.nodes.keyword.DeclarationKeywordNode>)
                                        n -> new DefaultResultFactory().createCorrectResult("ok")));
            }

            @Override
            public com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<
                            com.ingsis.nodes.keyword.IfKeywordNode>
                    createConditionalNodePublisher() {
                return new com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<>(
                        java.util.List.of(
                                (com.ingsis.rule.observer.handlers.NodeEventHandler<
                                                com.ingsis.nodes.keyword.IfKeywordNode>)
                                        n -> new DefaultResultFactory().createCorrectResult("ok")));
            }

            @Override
            public com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<
                            com.ingsis.nodes.expression.ExpressionNode>
                    createExpressionNodePublisher() {
                return new com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<>(
                        java.util.List.of(
                                (com.ingsis.rule.observer.handlers.NodeEventHandler<
                                                com.ingsis.nodes.expression.ExpressionNode>)
                                        n -> new DefaultResultFactory().createCorrectResult("ok")));
            }
        };
    }
}
