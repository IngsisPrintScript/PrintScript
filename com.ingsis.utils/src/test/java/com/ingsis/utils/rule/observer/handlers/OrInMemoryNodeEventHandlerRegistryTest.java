/*
 * My Project
 */

package com.ingsis.utils.rule.observer.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.nodes.visitors.Visitor;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.result.factory.ResultFactory;
import org.junit.jupiter.api.Test;

class OrInMemoryNodeEventHandlerRegistryTest {

    @Test
    void whenNoHandlerThenReturnsIncorrect() {
        ResultFactory rf = new DefaultResultFactory();
        OrInMemoryNodeEventHandlerRegistry<Node> reg = new OrInMemoryNodeEventHandlerRegistry<>(rf);

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

        Result<String> r = reg.handle(n);
        assertTrue(!r.isCorrect());
        assertEquals("No way to handle that.", r.error());
    }

    @Test
    void registersAndFindsFirstCorrectHandler() {
        ResultFactory rf = new DefaultResultFactory();
        OrInMemoryNodeEventHandlerRegistry<Node> reg = new OrInMemoryNodeEventHandlerRegistry<>(rf);

        NodeEventHandler<Node> bad = node -> new IncorrectResult<>("bad");
        NodeEventHandler<Node> good = node -> new CorrectResult<>("ok");

        reg.register(bad);
        reg.register(good);

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

        Result<String> r = reg.handle(n);
        assertTrue(r.isCorrect());
        assertEquals("ok", r.result());
    }
}
