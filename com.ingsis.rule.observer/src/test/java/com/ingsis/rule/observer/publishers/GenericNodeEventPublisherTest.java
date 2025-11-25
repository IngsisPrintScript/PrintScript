/*
 * My Project
 */

package com.ingsis.rule.observer.publishers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.rule.observer.handlers.NodeEventHandler;
import java.util.List;
import org.junit.jupiter.api.Test;

class GenericNodeEventPublisherTest {

    @Test
    void whenListenerFailsThenNotifyReturnsIncorrect() {
        GenericNodeEventPublisher<com.ingsis.nodes.Node> pub =
                new GenericNodeEventPublisher<>(
                        List.of(
                                (NodeEventHandler<com.ingsis.nodes.Node>)
                                        (n -> new IncorrectResult<>("err"))));

        com.ingsis.nodes.Node n =
                new com.ingsis.nodes.Node() {
                    @Override
                    public Integer line() {
                        return 0;
                    }

                    @Override
                    public Integer column() {
                        return 0;
                    }

                    @Override
                    public com.ingsis.result.Result<String> acceptVisitor(
                            com.ingsis.visitors.Visitor visitor) {
                        return new CorrectResult<>("v");
                    }
                };

        Result<String> r = pub.notify(n);
        assertFalse(r.isCorrect());
        assertEquals("err", r.error());
    }

    @Test
    void whenAllListenersPassThenReturnsCorrectWithMessage() {
        GenericNodeEventPublisher<com.ingsis.nodes.Node> pub =
                new GenericNodeEventPublisher<>(
                        List.of(
                                (NodeEventHandler<com.ingsis.nodes.Node>)
                                        (n -> new CorrectResult<>("ok"))));

        com.ingsis.nodes.Node n =
                new com.ingsis.nodes.Node() {
                    @Override
                    public Integer line() {
                        return 0;
                    }

                    @Override
                    public Integer column() {
                        return 0;
                    }

                    @Override
                    public com.ingsis.result.Result<String> acceptVisitor(
                            com.ingsis.visitors.Visitor visitor) {
                        return new CorrectResult<>("v");
                    }
                };

        Result<String> r = pub.notify(n);
        assertTrue(r.result().startsWith("Let node passed the following semantic rules:"));
    }
}
