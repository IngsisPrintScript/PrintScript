package com.ingsis.rule.observer.publishers;

import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GenericNodeEventPublisherTest {

    @Test
    void whenListenerFails_thenNotifyReturnsIncorrect() {
        GenericNodeEventPublisher<com.ingsis.nodes.Node> pub = new GenericNodeEventPublisher<>(List.of(n -> new IncorrectResult<>("err")));
        com.ingsis.nodes.Node n = new com.ingsis.nodes.Node() { @Override public Integer line() { return 0;} @Override public Integer column() { return 0;} @Override public com.ingsis.result.Result<String> acceptVisitor(com.ingsis.visitors.Visitor visitor) { return new CorrectResult<>("v"); } };
        Result<String> r = pub.notify(n);
        assertFalse(r.isCorrect());
        assertEquals("err", r.error());
    }

    @Test
    void whenAllListenersPass_thenReturnsCorrectWithMessage() {
        GenericNodeEventPublisher<com.ingsis.nodes.Node> pub = new GenericNodeEventPublisher<>(List.of(n -> new CorrectResult<>("ok")));
        com.ingsis.nodes.Node n = new com.ingsis.nodes.Node() { @Override public Integer line() { return 0;} @Override public Integer column() { return 0;} @Override public com.ingsis.result.Result<String> acceptVisitor(com.ingsis.visitors.Visitor visitor) { return new CorrectResult<>("v"); } };
        Result<String> r = pub.notify(n);
        assertTrue(r.result().startsWith("Let node passed the following semantic rules:"));
    }
}
