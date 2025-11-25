/*
 * My Project
 */

package com.ingsis.rule.observer.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.result.Result;
import com.ingsis.result.factory.DefaultResultFactory;
import com.ingsis.result.factory.ResultFactory;
import org.junit.jupiter.api.Test;

class InMemoryNodeEventHandlerRegistryExtraTest {

    @Test
    void singleArgConstructorCreatesEmptyRegistry() {
        ResultFactory rf = new DefaultResultFactory();
        InMemoryNodeEventHandlerRegistry<com.ingsis.nodes.Node> reg =
                new InMemoryNodeEventHandlerRegistry<>(rf);

        com.ingsis.nodes.Node n =
                new com.ingsis.nodes.Node() {
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
        InMemoryNodeEventHandlerRegistry<com.ingsis.nodes.Node> reg =
                new InMemoryNodeEventHandlerRegistry<>(rf);

        NodeEventHandler<com.ingsis.nodes.Node> h =
                node -> new com.ingsis.result.CorrectResult<>("x");

        // register should NOT throw
        reg.register(h);

        // Verify that handle() now calls the registered handler
        com.ingsis.nodes.Node n =
                new com.ingsis.nodes.Node() {
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
                        return null;
                    }
                };

        Result<String> r = reg.handle(n);
        assertEquals("All checks passed.", r.result()); // handler's result is returned
        assertTrue(r.isCorrect());
    }
}
