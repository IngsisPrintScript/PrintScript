/*
 * My Project
 */

package com.ingsis.sca.observer.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.nodes.visitors.Visitor;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.result.factory.ResultFactory;
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
        public Result<String> acceptVisitor(Visitor visitor) {
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
