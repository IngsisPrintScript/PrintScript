/*
 * My Project
 */

package com.ingsis.utils.rule.observer.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.nodes.visitors.Visitor;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.result.factory.ResultFactory;
import org.junit.jupiter.api.Test;

class FinalHandlerTest {

    @Test
    void handleReturnsCorrectResultFromFactory() {
        ResultFactory rf = new DefaultResultFactory();
        FinalHandler<com.ingsis.utils.nodes.nodes.Node> h = new FinalHandler<>(rf);

        Node n =
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
                        return rf.createCorrectResult("v");
                    }
                };

        Result<String> r = h.handle(n);
        assertTrue(r.isCorrect());
        assertEquals("Check passed.", r.result());
    }
}
