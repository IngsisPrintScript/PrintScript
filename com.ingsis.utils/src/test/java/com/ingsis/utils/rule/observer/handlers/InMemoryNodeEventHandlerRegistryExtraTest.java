/*
 * My Project
 */

package com.ingsis.utils.rule.observer.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.nodes.visitors.Visitor;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.result.factory.ResultFactory;
import org.junit.jupiter.api.Test;

class InMemoryNodeEventHandlerRegistryExtraTest {

    @Test
    void singleArgConstructorCreatesEmptyRegistry() {
        ResultFactory rf = new DefaultResultFactory();
        InMemoryNodeEventHandlerRegistry<com.ingsis.utils.nodes.nodes.Node> reg =
                new InMemoryNodeEventHandlerRegistry<>(rf);

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
                        return null;
                    }
                };

        Result<String> r = reg.handle(n);
        assertTrue(r.isCorrect());
        assertEquals("All checks passed.", r.result());
    }

    @Test
    void registerAddsHandlerSuccessfully() {
        ResultFactory rf = new DefaultResultFactory();
        InMemoryNodeEventHandlerRegistry<com.ingsis.utils.nodes.nodes.Node> reg =
                new InMemoryNodeEventHandlerRegistry<>(rf);

        NodeEventHandler<com.ingsis.utils.nodes.nodes.Node> h = node -> new CorrectResult<>("x");

        // register should NOT throw
        reg.register(h);

        // Verify that handle() now calls the registered handler
        com.ingsis.utils.nodes.nodes.Node n =
                new com.ingsis.utils.nodes.nodes.Node() {
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
                        return null;
                    }
                };

        Result<String> r = reg.handle(n);
        assertEquals("All checks passed.", r.result()); // handler's result is returned
        assertTrue(r.isCorrect());
    }
}
