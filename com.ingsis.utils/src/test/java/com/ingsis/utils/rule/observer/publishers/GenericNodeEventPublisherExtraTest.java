/*
 * My Project
 */

package com.ingsis.utils.rule.observer.publishers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.nodes.visitors.Visitor;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import java.util.List;
import org.junit.jupiter.api.Test;

class GenericNodeEventPublisherExtraTest {

    @Test
    void singleHandlerConstructorReturnsCorrect() {
        NodeEventHandler<com.ingsis.utils.nodes.nodes.Node> h = n -> new CorrectResult<>("ok");
        GenericNodeEventPublisher<com.ingsis.utils.nodes.nodes.Node> pub =
                new GenericNodeEventPublisher<>(h);

        com.ingsis.utils.nodes.nodes.Node n =
                new Node() {
                    @Override
                    public Integer line() {
                        return 0;
                    }

                    @Override
                    public Integer column() {
                        return 0;
                    }

                    @Override
                    public Result<String> acceptVisitor(Visitor visitor) {
                        return new CorrectResult<>("v");
                    }
                };

        Result<String> r = pub.notify(n);
        assertTrue(r.isCorrect());
        assertTrue(r.result().startsWith("Let node passed the following semantic rules:"));
    }

    @Test
    void multipleHandlersStopAtFirstIncorrect() {
        NodeEventHandler<com.ingsis.utils.nodes.nodes.Node> good = n -> new CorrectResult<>("ok");
        NodeEventHandler<com.ingsis.utils.nodes.nodes.Node> bad =
                n -> new IncorrectResult<>("boom");

        GenericNodeEventPublisher<com.ingsis.utils.nodes.nodes.Node> pub =
                new GenericNodeEventPublisher<>(List.of(good, bad));

        com.ingsis.utils.nodes.nodes.Node n =
                new com.ingsis.utils.nodes.nodes.Node() {
                    @Override
                    public Integer line() {
                        return 0;
                    }

                    @Override
                    public Integer column() {
                        return 0;
                    }

                    @Override
                    public Result<String> acceptVisitor(Visitor visitor) {
                        return new CorrectResult<>("v");
                    }
                };

        Result<String> r = pub.notify(n);
        assertTrue(!r.isCorrect());
        assertEquals("boom", r.error());
    }
}
