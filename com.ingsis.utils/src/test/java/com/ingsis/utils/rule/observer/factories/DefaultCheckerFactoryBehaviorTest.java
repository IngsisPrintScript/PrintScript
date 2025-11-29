/*
 * My Project
 */

package com.ingsis.utils.rule.observer.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.utils.nodes.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Visitor;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.rule.observer.EventsChecker;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import com.ingsis.utils.rule.observer.publishers.GenericNodeEventPublisher;
import com.ingsis.utils.rule.observer.publishers.factories.PublishersFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;

class DefaultCheckerFactoryBehaviorTest {

    private static PublishersFactory okPublishersFactory() {
        return new PublishersFactory() {
            @Override
            public GenericNodeEventPublisher<DeclarationKeywordNode> createLetNodePublisher() {
                NodeEventHandler<DeclarationKeywordNode> h = n -> new CorrectResult<>("ok");
                return new GenericNodeEventPublisher<>(List.of(h));
            }

            @Override
            public GenericNodeEventPublisher<IfKeywordNode> createConditionalNodePublisher() {
                NodeEventHandler<IfKeywordNode> h = n -> new CorrectResult<>("ok");
                return new GenericNodeEventPublisher<>(List.of(h));
            }

            @Override
            public GenericNodeEventPublisher<ExpressionNode> createExpressionNodePublisher() {
                NodeEventHandler<ExpressionNode> h = n -> new CorrectResult<>("ok");
                return new GenericNodeEventPublisher<>(List.of(h));
            }
        };
    }

    @Test
    void factoryCreatesCheckerThatDispatchesToPublishers() {
        PublishersFactory pf =
                new PublishersFactory() {
                    @Override
                    public GenericNodeEventPublisher<DeclarationKeywordNode>
                            createLetNodePublisher() {
                        NodeEventHandler<DeclarationKeywordNode> h = n -> new CorrectResult<>("ok");
                        return new GenericNodeEventPublisher<>(List.of(h));
                    }

                    @Override
                    public GenericNodeEventPublisher<IfKeywordNode>
                            createConditionalNodePublisher() {
                        NodeEventHandler<IfKeywordNode> h = n -> new CorrectResult<>("ok");
                        return new GenericNodeEventPublisher<>(List.of(h));
                    }

                    @Override
                    public GenericNodeEventPublisher<ExpressionNode>
                            createExpressionNodePublisher() {
                        NodeEventHandler<ExpressionNode> h = n -> new CorrectResult<>("ok");
                        return new GenericNodeEventPublisher<>(List.of(h));
                    }
                };

        DefaultCheckerFactory f = new DefaultCheckerFactory();
        var checker = f.createInMemoryEventBasedChecker(pf);

        // Build minimal DeclarationKeywordNode
        TypeAssignationNode typeAssign =
                new TypeAssignationNode(new IdentifierNode("id", 0, 0), null, 0, 0);
        ValueAssignationNode valueAssign =
                new ValueAssignationNode(
                        new IdentifierNode("id", 0, 0), new IdentifierNode("id", 0, 0), 0, 0);
        DeclarationKeywordNode decl =
                new DeclarationKeywordNode(typeAssign, valueAssign, false, 0, 0);

        Result<String> r = checker.check(decl);
        assertTrue(r.isCorrect());
    }

    @Test
    void eventsCheckerReturnsIncorrectWhenNoPublisherPresent() {
        Supplier<Map<Class<? extends Node>, GenericNodeEventPublisher<? extends Node>>>
                emptySupplier = () -> new HashMap<>();

        EventsChecker checker = new EventsChecker(emptySupplier);

        TypeAssignationNode typeAssign =
                new TypeAssignationNode(new IdentifierNode("id", 0, 0), null, 0, 0);
        ValueAssignationNode valueAssign =
                new ValueAssignationNode(
                        new IdentifierNode("id", 0, 0), new IdentifierNode("id", 0, 0), 0, 0);
        DeclarationKeywordNode decl =
                new DeclarationKeywordNode(typeAssign, valueAssign, false, 0, 0);

        Result<String> r = checker.check(decl);
        assertFalse(r.isCorrect());
        assertTrue(r.error().contains("No publisher for node type"));
    }

    @Test
    void ifCheckerReturnsChildErrorWhenChildFails() {
        // publisher map contains a working If publisher so dispatch would succeed,
        // but we put a failing child in thenBody to exercise early return.
        DefaultCheckerFactory f = new DefaultCheckerFactory();
        var checker = f.createInMemoryEventBasedChecker(okPublishersFactory());

        Node failingChild = new FailingChild();

        IfKeywordNode ifNode =
                new IfKeywordNode(
                        new IdentifierNode("cond", 0, 0), List.of(failingChild), List.of(), 0, 0);
        Result<String> r = checker.check(ifNode);
        assertFalse(r.isCorrect());
        assertEquals("child-failed", r.error());
    }

    private static class FailingChild implements Node, com.ingsis.utils.nodes.visitors.Checkable {
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

        @Override
        public Result<String> acceptChecker(Checker c) {
            return new IncorrectResult<>("child-failed");
        }
    }
}
