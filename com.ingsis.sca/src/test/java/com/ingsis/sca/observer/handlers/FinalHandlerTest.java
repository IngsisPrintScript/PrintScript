/*
 * My Project
 */

package com.ingsis.sca.observer.handlers;

import static org.junit.jupiter.api.Assertions.*;

import com.ingsis.nodes.Node;
import com.ingsis.result.Result;
import com.ingsis.result.factory.DefaultResultFactory;
import com.ingsis.result.factory.ResultFactory;
import org.junit.jupiter.api.Test;

class FinalHandlerTest {

    static class DummyNode implements Node {
        @Override
        public Integer line() {
            return 1;
        }

        @Override
        public Integer column() {
            return 2;
        }

        @Override
        public com.ingsis.result.Result<String> acceptVisitor(com.ingsis.visitors.Visitor visitor) {
            return new DefaultResultFactory().createCorrectResult("ok");
        }
    }

    @Test
    void returnsCorrectResultAlways() {
        ResultFactory rf = new DefaultResultFactory();
        FinalHandler<Node> h = new FinalHandler<>(rf);
        Result<String> res = h.handle(new DummyNode());
        assertTrue(res.isCorrect());
        assertEquals("Check passed.", res.result());
    }
}
