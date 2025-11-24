/*
 * My Project
 */

package com.ingsis.rule.observer.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.nodes.Node;
import com.ingsis.result.Result;
import com.ingsis.result.factory.DefaultResultFactory;
import com.ingsis.result.factory.ResultFactory;
import org.junit.jupiter.api.Test;

class FinalHandlerTest {

    @Test
    void handleReturnsCorrectResultFromFactory() {
        ResultFactory rf = new DefaultResultFactory();
        FinalHandler<com.ingsis.nodes.Node> h = new FinalHandler<>(rf);

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
                    public com.ingsis.result.Result<String> acceptVisitor(
                            com.ingsis.visitors.Visitor visitor) {
                        return rf.createCorrectResult("v");
                    }
                };

        Result<String> r = h.handle(n);
        assertTrue(r.isCorrect());
        assertEquals("Check passed.", r.result());
    }
}
