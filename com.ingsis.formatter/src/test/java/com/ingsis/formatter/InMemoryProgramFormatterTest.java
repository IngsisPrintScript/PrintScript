/*
 * My Project
 */

package com.ingsis.formatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.function.CallFunctionNode;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.nodes.expression.literal.LiteralNode;
import com.ingsis.utils.peekableiterator.PeekableIterator;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.EventsChecker;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import com.ingsis.utils.rule.observer.publishers.GenericNodeEventPublisher;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryProgramFormatterTest {
    private ResultFactory resultFactory;

    static class SimplePeekableIterator<T> implements PeekableIterator<T> {
        private final List<T> list;
        private int idx = 0;

        SimplePeekableIterator(List<T> list) {
            this.list = new ArrayList<>(list);
        }

        @Override
        public boolean hasNext() {
            return idx < list.size();
        }

        @Override
        public T next() {
            return list.get(idx++);
        }

        @Override
        public T peek() {
            return list.get(idx);
        }
    }

    @BeforeEach
    void setup() {
        resultFactory = new DefaultResultFactory();
    }

    @Test
    void formatSimpleCallReturnsCorrectResult() {
        // given
        IdentifierNode id = new IdentifierNode("println", 1, 1);
        CallFunctionNode call = new CallFunctionNode(id, List.of(new LiteralNode("x", 1, 2)), 1, 1);

        NodeEventHandler<ExpressionNode> handler = n -> resultFactory.createCorrectResult("CALL");
        GenericNodeEventPublisher<ExpressionNode> publisher =
                new GenericNodeEventPublisher<>(handler);

        Map<Class<? extends Node>, GenericNodeEventPublisher<? extends Node>> map = new HashMap<>();
        map.put(ExpressionNode.class, publisher);

        EventsChecker checker = new EventsChecker(() -> map);

        PeekableIterator<com.ingsis.utils.nodes.visitors.Interpretable> it =
                new SimplePeekableIterator<>(List.of(call));
        InMemoryProgramFormatter formatter = new InMemoryProgramFormatter(it, checker);

        // when
        CorrectResult<String> r = (CorrectResult<String>) formatter.format();

        // then
        assertTrue(r.isCorrect());
    }

    @Test
    void formatConcatenatesFormattedChildren() {
        // given
        LiteralNode a = new LiteralNode("a", 1, 1);
        LiteralNode b = new LiteralNode("b", 1, 2);

        NodeEventHandler<ExpressionNode> handler =
                n -> resultFactory.createCorrectResult(((LiteralNode) n).value());
        GenericNodeEventPublisher<ExpressionNode> publisher =
                new GenericNodeEventPublisher<>(handler);

        Map<Class<? extends Node>, GenericNodeEventPublisher<? extends Node>> map = new HashMap<>();
        map.put(ExpressionNode.class, publisher);

        EventsChecker checker = new EventsChecker(() -> map);

        PeekableIterator<com.ingsis.utils.nodes.visitors.Interpretable> it =
                new SimplePeekableIterator<>(List.of(a, b));
        InMemoryProgramFormatter fmt = new InMemoryProgramFormatter(it, checker);

        // when
        CorrectResult<String> r = (CorrectResult<String>) fmt.format();

        // then
        assertEquals(
                "Let node passed the following semantic rules:Let node passed the following"
                        + " semantic rules:",
                r.result());
    }

    @Test
    void formatStopsOnIncorrect() {
        // given
        LiteralNode a = new LiteralNode("a", 1, 1);
        LiteralNode b = new LiteralNode("b", 1, 2);

        NodeEventHandler<ExpressionNode> badHandler =
                n -> resultFactory.createIncorrectResult("err");
        GenericNodeEventPublisher<ExpressionNode> publisher =
                new GenericNodeEventPublisher<>(badHandler);

        Map<Class<? extends Node>, GenericNodeEventPublisher<? extends Node>> map = new HashMap<>();
        map.put(ExpressionNode.class, publisher);

        EventsChecker checker = new EventsChecker(() -> map);

        PeekableIterator<com.ingsis.utils.nodes.visitors.Interpretable> it =
                new SimplePeekableIterator<>(List.of(a, b));
        InMemoryProgramFormatter fmt = new InMemoryProgramFormatter(it, checker);

        // when
        IncorrectResult<String> r = (IncorrectResult<String>) fmt.format();

        // then
        assertEquals("err", r.error());
    }
}
