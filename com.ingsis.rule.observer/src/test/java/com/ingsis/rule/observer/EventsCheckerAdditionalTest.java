/*
 * My Project
 */

package com.ingsis.rule.observer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.nodes.Node;
import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.rule.observer.handlers.NodeEventHandler;
import com.ingsis.rule.observer.publishers.factories.PublishersFactory;
import java.lang.reflect.Method;
import java.util.List;
import org.junit.jupiter.api.Test;

class EventsCheckerAdditionalTest {

    private static PublishersFactory okPublishersFactory() {
        return new PublishersFactory() {
            @Override
            public com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<
                            com.ingsis.nodes.keyword.DeclarationKeywordNode>
                    createLetNodePublisher() {
                return new com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<>(
                        List.of(
                                (NodeEventHandler<com.ingsis.nodes.keyword.DeclarationKeywordNode>)
                                        (node -> new CorrectResult<>("ok"))));
            }

            @Override
            public com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<
                            com.ingsis.nodes.keyword.IfKeywordNode>
                    createConditionalNodePublisher() {
                return new com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<>(
                        List.of(
                                (NodeEventHandler<com.ingsis.nodes.keyword.IfKeywordNode>)
                                        (node -> new CorrectResult<>("ok"))));
            }

            @Override
            public com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<
                            com.ingsis.nodes.expression.ExpressionNode>
                    createExpressionNodePublisher() {
                return new com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<>(
                        List.of(
                                (NodeEventHandler<com.ingsis.nodes.expression.ExpressionNode>)
                                        (node -> new CorrectResult<>("ok"))));
            }
        };
    }

    private static PublishersFactory badPublishersFactory() {
        return new PublishersFactory() {
            @Override
            public com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<
                            com.ingsis.nodes.keyword.DeclarationKeywordNode>
                    createLetNodePublisher() {
                return new com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<>(
                        List.of(
                                (NodeEventHandler<com.ingsis.nodes.keyword.DeclarationKeywordNode>)
                                        (node -> new IncorrectResult<>("pub-bad"))));
            }

            @Override
            public com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<
                            com.ingsis.nodes.keyword.IfKeywordNode>
                    createConditionalNodePublisher() {
                return new com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<>(
                        List.of(
                                (NodeEventHandler<com.ingsis.nodes.keyword.IfKeywordNode>)
                                        (node -> new IncorrectResult<>("pub-bad"))));
            }

            @Override
            public com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<
                            com.ingsis.nodes.expression.ExpressionNode>
                    createExpressionNodePublisher() {
                return new com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<>(
                        List.of(
                                (NodeEventHandler<com.ingsis.nodes.expression.ExpressionNode>)
                                        (node -> new IncorrectResult<>("pub-bad"))));
            }
        };
    }

    private static PublishersFactory emptyPublishersFactory() {
        return new PublishersFactory() {
            @Override
            public com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<
                            com.ingsis.nodes.keyword.DeclarationKeywordNode>
                    createLetNodePublisher() {
                return new com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<>(
                        List.of());
            }

            @Override
            public com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<
                            com.ingsis.nodes.keyword.IfKeywordNode>
                    createConditionalNodePublisher() {
                return new com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<>(
                        List.of());
            }

            @Override
            public com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<
                            com.ingsis.nodes.expression.ExpressionNode>
                    createExpressionNodePublisher() {
                return new com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<>(
                        List.of());
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
            public com.ingsis.result.Result<String> acceptChecker(
                    com.ingsis.visitors.Checker checker) {
                return checker.check(this);
            }

            @Override
            public com.ingsis.result.Result<String> acceptInterpreter(
                    com.ingsis.visitors.Interpreter interpreter) {
                return new CorrectResult<>("interp");
            }

            @Override
            public com.ingsis.result.Result<String> acceptVisitor(
                    com.ingsis.visitors.Visitor visitor) {
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

    private static class OkNode implements Node, com.ingsis.visitors.Checkable {
        @Override
        public Integer line() {
            return 1;
        }

        @Override
        public Integer column() {
            return 1;
        }

        @Override
        public com.ingsis.result.Result<String> acceptVisitor(com.ingsis.visitors.Visitor visitor) {
            return new CorrectResult<>("v");
        }

        @Override
        public com.ingsis.result.Result<String> acceptChecker(com.ingsis.visitors.Checker checker) {
            return new CorrectResult<>("ok");
        }
    }

    private static class BadNode implements Node, com.ingsis.visitors.Checkable {
        @Override
        public Integer line() {
            return 1;
        }

        @Override
        public Integer column() {
            return 1;
        }

        @Override
        public com.ingsis.result.Result<String> acceptVisitor(com.ingsis.visitors.Visitor visitor) {
            return new CorrectResult<>("v");
        }

        @Override
        public com.ingsis.result.Result<String> acceptChecker(com.ingsis.visitors.Checker checker) {
            return new IncorrectResult<>("else-bad");
        }
    }

    @Test
    void ifNodeElseBodyShortCircuitsOnFailure() {
        EventsChecker checker = new EventsChecker(okPublishersFactory());

        com.ingsis.nodes.keyword.IfKeywordNode ifNode =
                new com.ingsis.nodes.keyword.IfKeywordNode(
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
                    public com.ingsis.result.Result<String> acceptVisitor(
                            com.ingsis.visitors.Visitor visitor) {
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
