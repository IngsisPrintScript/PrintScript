/*
 * My Project
 */

package com.ingsis.utils.rule.observer.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.nodes.visitors.Visitor;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.result.factory.ResultFactory;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryNodeEventHandlerRegistryTest {

    private ResultFactory resultFactory;

    @BeforeEach
    void setUp() {
        resultFactory = new DefaultResultFactory();
    }

    @Test
    void whenHandlerFailsThenRegistryReturnsClonedIncorrect() {
        NodeEventHandler<com.ingsis.utils.nodes.nodes.Node> bad =
                node -> new IncorrectResult<>("bad");
        InMemoryNodeEventHandlerRegistry<com.ingsis.utils.nodes.nodes.Node> reg =
                new InMemoryNodeEventHandlerRegistry<>(List.of(bad), resultFactory);

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

        Result<String> r = reg.handle(n);
        assertFalse(r.isCorrect());
        assertEquals("bad", r.error());
    }

    @Test
    void whenAllHandlersPassThenReturnsCorrect() {
        NodeEventHandler<com.ingsis.utils.nodes.nodes.Node> ok = node -> new CorrectResult<>("ok");
        InMemoryNodeEventHandlerRegistry<com.ingsis.utils.nodes.nodes.Node> reg =
                new InMemoryNodeEventHandlerRegistry<>(List.of(ok), resultFactory);
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
                        return new CorrectResult<>("v");
                    }
                };
        Result<String> r = reg.handle(n);
        assertEquals("All checks passed.", r.result());
    }
}
