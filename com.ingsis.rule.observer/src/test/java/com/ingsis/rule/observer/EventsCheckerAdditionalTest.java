/*
 * My Project
 */

package com.ingsis.rule.observer;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void ifNodeElseBodyShortCircuitsOnFailure() {
        PublishersFactory pf =
                new PublishersFactory() {
                    @Override
                    public com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<
                                    com.ingsis.nodes.keyword.DeclarationKeywordNode>
                            createLetNodePublisher() {
                        return new com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<>(
                                List.of(
                                        (NodeEventHandler<
                                                        com.ingsis.nodes.keyword
                                                                .DeclarationKeywordNode>)
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
                                        (NodeEventHandler<
                                                        com.ingsis.nodes.expression.ExpressionNode>)
                                                (node -> new CorrectResult<>("ok"))));
                    }
                };
        EventsChecker checker = new EventsChecker(pf);

        class OkNode implements Node, com.ingsis.visitors.Checkable {
            @Override
            public Integer line() {
                return 1;
            }

            @Override
            public Integer column() {
                return 1;
            }

            @Override
            public com.ingsis.result.Result<String> acceptVisitor(
                    com.ingsis.visitors.Visitor visitor) {
                return new CorrectResult<>("v");
            }

            @Override
            public com.ingsis.result.Result<String> acceptChecker(
                    com.ingsis.visitors.Checker checker) {
                return new CorrectResult<>("ok");
            }
        }

        class BadNode implements Node, com.ingsis.visitors.Checkable {
            @Override
            public Integer line() {
                return 1;
            }

            @Override
            public Integer column() {
                return 1;
            }

            @Override
            public com.ingsis.result.Result<String> acceptVisitor(
                    com.ingsis.visitors.Visitor visitor) {
                return new CorrectResult<>("v");
            }

            @Override
            public com.ingsis.result.Result<String> acceptChecker(
                    com.ingsis.visitors.Checker checker) {
                return new IncorrectResult<>("else-bad");
            }
        }

        com.ingsis.nodes.keyword.IfKeywordNode ifNode =
                new com.ingsis.nodes.keyword.IfKeywordNode(
                        new ExpressionNode() {
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
                                return "c";
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
                        },
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
        // publisher that returns incorrect
        PublishersFactory badPf =
                new PublishersFactory() {
                    @Override
                    public com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<
                                    com.ingsis.nodes.keyword.DeclarationKeywordNode>
                            createLetNodePublisher() {
                        return new com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<>(
                                List.of(
                                        (NodeEventHandler<
                                                        com.ingsis.nodes.keyword
                                                                .DeclarationKeywordNode>)
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
                                        (NodeEventHandler<
                                                        com.ingsis.nodes.expression.ExpressionNode>)
                                                (node -> new IncorrectResult<>("pub-bad"))));
                    }
                };
        EventsChecker badChecker = new EventsChecker(badPf);

        ExpressionNode expr =
                new ExpressionNode() {
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

        Result<String> r = badChecker.check(expr);
        assertFalse(r.isCorrect());
        assertEquals("pub-bad", r.error());

        // publisher that returns correct
        PublishersFactory okPf =
                new PublishersFactory() {
                    @Override
                    public com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<
                                    com.ingsis.nodes.keyword.DeclarationKeywordNode>
                            createLetNodePublisher() {
                        return new com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<>(
                                List.of(
                                        (NodeEventHandler<
                                                        com.ingsis.nodes.keyword
                                                                .DeclarationKeywordNode>)
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
                                        (NodeEventHandler<
                                                        com.ingsis.nodes.expression.ExpressionNode>)
                                                (node -> new CorrectResult<>("ok"))));
                    }
                };
        EventsChecker okChecker = new EventsChecker(okPf);
        Result<String> okRes = okChecker.check(expr);
        assertTrue(okRes.isCorrect());
    }

    @Test
    void dispatchMissingPublisherReturnsIncorrect_usingReflection() throws Exception {
        PublishersFactory pf =
                new PublishersFactory() {
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
        EventsChecker checker = new EventsChecker(pf);

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
