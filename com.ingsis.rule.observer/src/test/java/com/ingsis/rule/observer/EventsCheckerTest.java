package com.ingsis.rule.observer;

import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.rule.observer.publishers.factories.PublishersFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EventsCheckerTest {

    private EventsChecker checker;

    @BeforeEach
    void setUp() {
        PublishersFactory pf = new PublishersFactory() {
            @Override
            public com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<com.ingsis.nodes.keyword.DeclarationKeywordNode> createLetNodePublisher() {
                return new com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<>(List.of(node -> new CorrectResult<>("ok")));
            }

            @Override
            public com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<com.ingsis.nodes.keyword.IfKeywordNode> createConditionalNodePublisher() {
                return new com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<>(List.of(node -> new CorrectResult<>("ok")));
            }

            @Override
            public com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<com.ingsis.nodes.expression.ExpressionNode> createExpressionNodePublisher() {
                return new com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<>(List.of(node -> new CorrectResult<>("ok")));
            }
        };
        checker = new EventsChecker(pf);
    }

    @Test
    void dispatchReturnsIncorrectFromPublisher() {
        PublishersFactory pf = new PublishersFactory() {
            @Override
            public com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<com.ingsis.nodes.keyword.DeclarationKeywordNode> createLetNodePublisher() {
                return new com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<>(List.of(node -> new IncorrectResult<>("bad")));
            }

            @Override
            public com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<com.ingsis.nodes.keyword.IfKeywordNode> createConditionalNodePublisher() {
                return new com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<>(List.of(node -> new IncorrectResult<>("bad")));
            }

            @Override
            public com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<com.ingsis.nodes.expression.ExpressionNode> createExpressionNodePublisher() {
                return new com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<>(List.of(node -> new IncorrectResult<>("bad")));
            }
        };
        EventsChecker ec = new EventsChecker(pf);
        ExpressionNode expr = new ExpressionNode() {
            @Override
            public java.util.List<ExpressionNode> children() { return List.of(); }

            @Override
            public Boolean isTerminalNode() { return true; }

            @Override
            public String symbol() { return "x"; }

            @Override
            public com.ingsis.result.Result<String> acceptChecker(com.ingsis.visitors.Checker checker) { return checker.check(this); }

            @Override
            public com.ingsis.result.Result<String> acceptInterpreter(com.ingsis.visitors.Interpreter interpreter) { return new CorrectResult<>("interp"); }

            @Override
            public com.ingsis.result.Result<String> acceptVisitor(com.ingsis.visitors.Visitor visitor) { return new CorrectResult<>("visit"); }

            @Override
            public Integer line() { return 0; }

            @Override
            public Integer column() { return 0; }
        };

        Result<String> res = ec.check(expr);
        assertFalse(res.isCorrect());
        assertEquals("bad", res.error());
    }

    @Test
    void ifNodeChildFailureShortCircuits() {
        class CheckableNode implements com.ingsis.nodes.Node, com.ingsis.visitors.Checkable {
            @Override public Integer line() { return 1; }
            @Override public Integer column() { return 1; }
            @Override public com.ingsis.result.Result<String> acceptVisitor(com.ingsis.visitors.Visitor visitor) { return new CorrectResult<>("v"); }
            @Override public com.ingsis.result.Result<String> acceptChecker(com.ingsis.visitors.Checker checker) { return new IncorrectResult<>("childErr"); }
        }

        CheckableNode checkableChild = new CheckableNode();
        com.ingsis.nodes.keyword.IfKeywordNode ifNode = new com.ingsis.nodes.keyword.IfKeywordNode(
                new ExpressionNode() {
                    @Override public java.util.List<ExpressionNode> children() { return List.of(); }
                    @Override public Boolean isTerminalNode() { return true; }
                    @Override public String symbol() { return "c"; }
                    @Override public com.ingsis.result.Result<String> acceptChecker(com.ingsis.visitors.Checker checker) { return checker.check(this); }
                    @Override public com.ingsis.result.Result<String> acceptInterpreter(com.ingsis.visitors.Interpreter interpreter) { return new CorrectResult<>("interp"); }
                    @Override public com.ingsis.result.Result<String> acceptVisitor(com.ingsis.visitors.Visitor visitor) { return new CorrectResult<>("visit"); }
                    @Override public Integer line() { return 0; }
                    @Override public Integer column() { return 0; }
                },
                List.of(checkableChild),
                List.of(),
                1,
                1);

        Result<String> out = checker.check(ifNode);
        assertFalse(out.isCorrect());
        assertEquals("childErr", out.error());
    }
}
