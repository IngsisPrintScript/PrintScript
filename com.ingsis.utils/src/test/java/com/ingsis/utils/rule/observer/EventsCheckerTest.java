/*
 * My Project
 */

package com.ingsis.utils.rule.observer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.nodes.visitors.Visitor;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import com.ingsis.utils.rule.observer.publishers.GenericNodeEventPublisher;
import com.ingsis.utils.rule.observer.publishers.factories.PublishersFactory;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventsCheckerTest {

    private EventsChecker checker;

    @BeforeEach
    void setUp() {
        PublishersFactory pf =
                new PublishersFactory() {
                    @Override
                    public GenericNodeEventPublisher<DeclarationKeywordNode>
                            createLetNodePublisher() {
                        return new GenericNodeEventPublisher<>(
                                List.of(
                                        (NodeEventHandler<
                                                        com.ingsis.utils.nodes.nodes.keyword
                                                                .DeclarationKeywordNode>)
                                                (node -> new CorrectResult<>("ok"))));
                    }

                    @Override
                    public GenericNodeEventPublisher<IfKeywordNode>
                            createConditionalNodePublisher() {
                        return new GenericNodeEventPublisher<>(
                                List.of(
                                        (NodeEventHandler<IfKeywordNode>)
                                                (node -> new CorrectResult<>("ok"))));
                    }

                    @Override
                    public GenericNodeEventPublisher<ExpressionNode>
                            createExpressionNodePublisher() {
                        return new GenericNodeEventPublisher<>(
                                List.of(
                                        (NodeEventHandler<ExpressionNode>)
                                                (node -> new CorrectResult<>("ok"))));
                    }
                };
        checker = new EventsChecker(pf);
    }

    private static PublishersFactory incorrectPublishersFactory() {
        return new PublishersFactory() {
            @Override
            public GenericNodeEventPublisher<DeclarationKeywordNode> createLetNodePublisher() {
                return new GenericNodeEventPublisher<>(
                        List.of(
                                (NodeEventHandler<DeclarationKeywordNode>)
                                        (node -> new IncorrectResult<>("bad"))));
            }

            @Override
            public GenericNodeEventPublisher<IfKeywordNode> createConditionalNodePublisher() {
                return new GenericNodeEventPublisher<>(
                        List.of(
                                (NodeEventHandler<IfKeywordNode>)
                                        (node -> new IncorrectResult<>("bad"))));
            }

            @Override
            public GenericNodeEventPublisher<ExpressionNode> createExpressionNodePublisher() {
                return new GenericNodeEventPublisher<>(
                        List.of(
                                (NodeEventHandler<ExpressionNode>)
                                        (node -> new IncorrectResult<>("bad"))));
            }
        };
    }

    private static ExpressionNode simpleExpressionNode() {
        return new ExpressionNode() {
            @Override
            public java.util.List<ExpressionNode> children() {
                return List.of();
            }

            @Override
            public Boolean isTerminalNode() {
                return true;
            }

            @Override
            public String symbol() {
                return "x";
            }

            @Override
            public Result<String> acceptChecker(Checker checker) {
                return checker.check(this);
            }

            @Override
            public Result<String> acceptInterpreter(Interpreter interpreter) {
                return new CorrectResult<>("interp");
            }

            @Override
            public Result<String> acceptVisitor(Visitor visitor) {
                return new CorrectResult<>("visit");
            }

            @Override
            public Integer line() {
                return 0;
            }

            @Override
            public Integer column() {
                return 0;
            }
        };
    }

    @Test
    void dispatchReturnsIncorrectFromPublisher() {
        PublishersFactory pf = incorrectPublishersFactory();
        EventsChecker ec = new EventsChecker(pf);
        ExpressionNode expr = simpleExpressionNode();

        Result<String> res = ec.check(expr);
        assertFalse(res.isCorrect());
        assertEquals("bad", res.error());
    }

    @Test
    void ifNodeChildFailureShortCircuits() {
        class CheckableNode
                implements com.ingsis.utils.nodes.nodes.Node,
                        com.ingsis.utils.nodes.visitors.Checkable {
            @Override
            public Integer line() {
                return 1;
            }

            @Override
            public Integer column() {
                return 1;
            }

            @Override
            public Result<String> acceptVisitor(Visitor visitor) {
                return new CorrectResult<>("v");
            }

            @Override
            public Result<String> acceptChecker(Checker checker) {
                return new IncorrectResult<>("childErr");
            }
        }

        CheckableNode checkableChild = new CheckableNode();
        IfKeywordNode ifNode =
                new IfKeywordNode(simpleExpressionNode(), List.of(checkableChild), List.of(), 1, 1);

        Result<String> out = checker.check(ifNode);
        assertFalse(out.isCorrect());
        assertEquals("childErr", out.error());
    }
}
