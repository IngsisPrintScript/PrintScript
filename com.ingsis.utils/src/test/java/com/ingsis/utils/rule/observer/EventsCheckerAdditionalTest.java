/*
 * My Project
 */

package com.ingsis.utils.rule.observer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.utils.nodes.nodes.Node;
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
import java.lang.reflect.Method;
import java.util.List;
import org.junit.jupiter.api.Test;

class EventsCheckerAdditionalTest {

    private static PublishersFactory okPublishersFactory() {
        return new PublishersFactory() {
            @Override
            public GenericNodeEventPublisher<DeclarationKeywordNode> createLetNodePublisher() {
                return new GenericNodeEventPublisher<>(
                        List.of(
                                (NodeEventHandler<DeclarationKeywordNode>)
                                        (node -> new CorrectResult<>("ok"))));
            }

            @Override
            public GenericNodeEventPublisher<IfKeywordNode> createConditionalNodePublisher() {
                return new GenericNodeEventPublisher<>(
                        List.of(
                                (NodeEventHandler<IfKeywordNode>)
                                        (node -> new CorrectResult<>("ok"))));
            }

            @Override
            public GenericNodeEventPublisher<ExpressionNode> createExpressionNodePublisher() {
                return new GenericNodeEventPublisher<>(
                        List.of(
                                (NodeEventHandler<ExpressionNode>)
                                        (node -> new CorrectResult<>("ok"))));
            }
        };
    }

    private static PublishersFactory badPublishersFactory() {
        return new PublishersFactory() {
            @Override
            public GenericNodeEventPublisher<DeclarationKeywordNode> createLetNodePublisher() {
                return new GenericNodeEventPublisher<>(
                        List.of(
                                (NodeEventHandler<DeclarationKeywordNode>)
                                        (node -> new IncorrectResult<>("pub-bad"))));
            }

            @Override
            public GenericNodeEventPublisher<IfKeywordNode> createConditionalNodePublisher() {
                return new GenericNodeEventPublisher<>(
                        List.of(
                                (NodeEventHandler<IfKeywordNode>)
                                        (node -> new IncorrectResult<>("pub-bad"))));
            }

            @Override
            public GenericNodeEventPublisher<ExpressionNode> createExpressionNodePublisher() {
                return new GenericNodeEventPublisher<>(
                        List.of(
                                (NodeEventHandler<ExpressionNode>)
                                        (node -> new IncorrectResult<>("pub-bad"))));
            }
        };
    }

    private static PublishersFactory emptyPublishersFactory() {
        return new PublishersFactory() {
            @Override
            public GenericNodeEventPublisher<DeclarationKeywordNode> createLetNodePublisher() {
                return new GenericNodeEventPublisher<>(List.of());
            }

            @Override
            public GenericNodeEventPublisher<IfKeywordNode> createConditionalNodePublisher() {
                return new GenericNodeEventPublisher<>(List.of());
            }

            @Override
            public GenericNodeEventPublisher<ExpressionNode> createExpressionNodePublisher() {
                return new GenericNodeEventPublisher<>(List.of());
            }
        };
    }

    private static ExpressionNode simpleExpressionNode() {
        return new ExpressionNode() {
            @Override
            public List<ExpressionNode> children() {
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

    private static class OkNode implements Node, com.ingsis.utils.nodes.visitors.Checkable {
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
            return new CorrectResult<>("ok");
        }
    }

    private static class BadNode implements Node, com.ingsis.utils.nodes.visitors.Checkable {
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
            return new IncorrectResult<>("else-bad");
        }
    }

    @Test
    void ifNodeElseBodyShortCircuitsOnFailure() {
        EventsChecker checker = new EventsChecker(okPublishersFactory());

        IfKeywordNode ifNode =
                new IfKeywordNode(
                        // expression
                        simpleExpressionNode(),
                        List.of(new OkNode()),
                        List.of(new BadNode()),
                        1,
                        1);

        Result<String> out = checker.check(ifNode);
        assertFalse(out.isCorrect());
        assertEquals("else-bad", out.error());
    }

    @Test
    void dispatchUsesPublisherResultCorrectAndIncorrect() {
        EventsChecker badChecker = new EventsChecker(badPublishersFactory());

        ExpressionNode expr = simpleExpressionNode();

        Result<String> r = badChecker.check(expr);
        assertFalse(r.isCorrect());
        assertEquals("pub-bad", r.error());

        // publisher that returns correct
        EventsChecker okChecker = new EventsChecker(okPublishersFactory());
        Result<String> okRes = okChecker.check(expr);
        assertTrue(okRes.isCorrect());
    }

    @Test
    void dispatchMissingPublisherReturnsIncorrectUsingReflection() throws Exception {
        EventsChecker checker = new EventsChecker(emptyPublishersFactory());

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
                        return new CorrectResult<>("v");
                    }
                };

        Method dispatch =
                EventsChecker.class.getDeclaredMethod("dispatch", Node.class, Class.class);
        dispatch.setAccessible(true);
        Result<String> res = (Result<String>) dispatch.invoke(checker, n, Node.class);
        assertFalse(res.isCorrect());
        assertTrue(res.error().contains("No publisher"));
    }
}
