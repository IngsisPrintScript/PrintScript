/*
 * My Project
 */

package com.ingsis.utils.rule.observer.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.nodes.visitors.Visitor;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.Result;
import org.junit.jupiter.api.Test;

class NodeEventHandlerTest {
    @Test
    void handlerInvocationReturnsResult() {
        NodeEventHandler<com.ingsis.utils.nodes.nodes.Node> handler =
                node -> new CorrectResult<>("ok");
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
        Result<String> r = handler.handle(n);
        assertEquals("ok", r.result());
    }
}
