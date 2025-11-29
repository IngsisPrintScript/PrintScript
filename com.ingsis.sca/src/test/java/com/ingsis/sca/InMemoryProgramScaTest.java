/*
 * My Project
 */

package com.ingsis.sca;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.peekableiterator.PeekableIterator;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.rule.observer.EventsChecker;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import com.ingsis.utils.rule.observer.publishers.GenericNodeEventPublisher;
import com.ingsis.utils.rule.observer.publishers.factories.PublishersFactory;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.Test;

class InMemoryProgramScaTest {

    static class AlwaysCorrect implements Checkable, Interpretable {
        @Override
        public Result<String> acceptChecker(Checker checker) {
            return new DefaultResultFactory().createCorrectResult("ok");
        }

        @Override
        public Result<String> acceptInterpreter(Interpreter interpreter) {
            return new DefaultResultFactory().createCorrectResult("ok");
        }
    }

    static class AlwaysIncorrect implements Checkable, Interpretable {
        private final Result r;

        AlwaysIncorrect(Result r) {
            this.r = r;
        }

        @Override
        public Result<String> acceptChecker(Checker checker) {
            return r;
        }

        @Override
        public Result<String> acceptInterpreter(Interpreter interpreter) {
            return r;
        }
    }

    @Test
    void analyzeReturnsCorrectWhenAllPass() {
        Iterator<Interpretable> it =
                java.util.List.<com.ingsis.utils.nodes.visitors.Interpretable>of(
                                new AlwaysCorrect(), new AlwaysCorrect())
                        .iterator();
        PeekableIterator<Interpretable> pk =
                new PeekableIterator<>() {
                    private final Iterator<Interpretable> inner = it;

                    @Override
                    public Interpretable peek() {
                        return inner.hasNext() ? inner.next() : null;
                    }

                    @Override
                    public boolean hasNext() {
                        return inner.hasNext();
                    }

                    @Override
                    public Interpretable next() {
                        return inner.next();
                    }
                };
        InMemoryProgramSca sca = new InMemoryProgramSca(pk, new EventsChecker(pf()));
        Result<String> res = sca.analyze();
        assertEquals("Check passed.", res.result());
    }

    @Test
    void analyzeReturnsFirstIncorrect() {
        Result bad = new DefaultResultFactory().createIncorrectResult("bad");
        Result later = new DefaultResultFactory().createIncorrectResult("later");
        Iterator<com.ingsis.utils.nodes.visitors.Interpretable> it =
                java.util.List.<com.ingsis.utils.nodes.visitors.Interpretable>of(
                                new AlwaysCorrect(),
                                new AlwaysIncorrect(bad),
                                new AlwaysIncorrect(later))
                        .iterator();
        PeekableIterator<Interpretable> pk2 =
                new PeekableIterator<>() {
                    private final Iterator<Interpretable> inner = it;

                    @Override
                    public Interpretable peek() {
                        return inner.hasNext() ? inner.next() : null;
                    }

                    @Override
                    public boolean hasNext() {
                        return inner.hasNext();
                    }

                    @Override
                    public Interpretable next() {
                        return inner.next();
                    }
                };
        InMemoryProgramSca sca = new InMemoryProgramSca(pk2, new EventsChecker(pf()));
        Result<String> res = sca.analyze();
        assertEquals("bad", res.error());
    }

    private static PublishersFactory pf() {
        return new PublishersFactory() {
            @Override
            public GenericNodeEventPublisher<DeclarationKeywordNode> createLetNodePublisher() {
                return new GenericNodeEventPublisher<>(
                        List.of(
                                (NodeEventHandler<DeclarationKeywordNode>)
                                        n -> new DefaultResultFactory().createCorrectResult("ok")));
            }

            @Override
            public GenericNodeEventPublisher<IfKeywordNode> createConditionalNodePublisher() {
                return new GenericNodeEventPublisher<>(
                        List.of(
                                (NodeEventHandler<IfKeywordNode>)
                                        n -> new DefaultResultFactory().createCorrectResult("ok")));
            }

            @Override
            public GenericNodeEventPublisher<ExpressionNode> createExpressionNodePublisher() {
                return new GenericNodeEventPublisher<>(
                        List.of(
                                (NodeEventHandler<ExpressionNode>)
                                        n -> new DefaultResultFactory().createCorrectResult("ok")));
            }
        };
    }
}
