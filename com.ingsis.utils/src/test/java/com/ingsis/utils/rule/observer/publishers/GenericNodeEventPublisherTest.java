/*
 * My Project
 */

package com.ingsis.utils.rule.observer.publishers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.nodes.visitors.Visitor;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import java.util.List;
import org.junit.jupiter.api.Test;

class GenericNodeEventPublisherTest {

    @Test
    void whenListenerFailsThenNotifyReturnsIncorrect() {
        GenericNodeEventPublisher<com.ingsis.utils.nodes.nodes.Node> pub =
                new GenericNodeEventPublisher<>(
                        List.of(
                                (NodeEventHandler<com.ingsis.utils.nodes.nodes.Node>)
                                        (n -> new IncorrectResult<>("err"))));

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
        assertFalse(r.isCorrect());
        assertEquals("err", r.error());
    }

    @Test
    void whenAllListenersPassThenReturnsCorrectWithMessage() {
        GenericNodeEventPublisher<com.ingsis.utils.nodes.nodes.Node> pub =
                new GenericNodeEventPublisher<>(
                        List.of(
                                (NodeEventHandler<com.ingsis.utils.nodes.nodes.Node>)
                                        (n -> new CorrectResult<>("ok"))));

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
        assertTrue(r.result().startsWith("Let node passed the following semantic rules:"));
    }
}
