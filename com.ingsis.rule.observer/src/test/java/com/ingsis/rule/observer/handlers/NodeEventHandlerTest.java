package com.ingsis.rule.observer.handlers;

import com.ingsis.result.CorrectResult;
import com.ingsis.result.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NodeEventHandlerTest {
    @Test
    void handlerInvocationReturnsResult() {
        NodeEventHandler<com.ingsis.nodes.Node> handler = node -> new CorrectResult<>("ok");
        com.ingsis.nodes.Node n = new com.ingsis.nodes.Node() {
            @Override public Integer line() { return 0; }
            @Override public Integer column() { return 0; }
            @Override public com.ingsis.result.Result<String> acceptVisitor(com.ingsis.visitors.Visitor visitor) { return new CorrectResult<>("v"); }
        };
        Result<String> r = handler.handle(n);
        assertEquals("ok", r.result());
    }
}
