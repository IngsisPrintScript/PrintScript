package com.ingsis.rule.observer.handlers;

import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.result.factory.DefaultResultFactory;
import com.ingsis.result.factory.ResultFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class InMemoryNodeEventHandlerRegistryTest {

    private ResultFactory resultFactory;

    @BeforeEach
    void setUp() {
        resultFactory = new DefaultResultFactory();
    }

    @Test
    void whenHandlerFails_thenRegistryReturnsClonedIncorrect() {
        NodeEventHandler<com.ingsis.nodes.Node> bad = node -> new IncorrectResult<>("bad");
        InMemoryNodeEventHandlerRegistry<com.ingsis.nodes.Node> reg = new InMemoryNodeEventHandlerRegistry<>(List.of(bad), resultFactory);

        com.ingsis.nodes.Node n = new com.ingsis.nodes.Node() {
            @Override public Integer line() { return 0; }
            @Override public Integer column() { return 0; }
            @Override public com.ingsis.result.Result<String> acceptVisitor(com.ingsis.visitors.Visitor visitor) { return new CorrectResult<>("v"); }
        };

        Result<String> r = reg.handle(n);
        assertFalse(r.isCorrect());
        assertEquals("bad", r.error());
    }

    @Test
    void whenAllHandlersPass_thenReturnsCorrect() {
        NodeEventHandler<com.ingsis.nodes.Node> ok = node -> new CorrectResult<>("ok");
        InMemoryNodeEventHandlerRegistry<com.ingsis.nodes.Node> reg = new InMemoryNodeEventHandlerRegistry<>(List.of(ok), resultFactory);
        com.ingsis.nodes.Node n = new com.ingsis.nodes.Node() {
            @Override public Integer line() { return 0; }
            @Override public Integer column() { return 0; }
            @Override public com.ingsis.result.Result<String> acceptVisitor(com.ingsis.visitors.Visitor visitor) { return new CorrectResult<>("v"); }
        };
        Result<String> r = reg.handle(n);
        assertEquals("All checks passed.", r.result());
    }
}
