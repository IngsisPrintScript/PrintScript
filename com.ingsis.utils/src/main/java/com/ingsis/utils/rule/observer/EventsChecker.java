/*
 * My Project
 */

package com.ingsis.utils.rule.observer; /*
                                         * My Project
                                         */

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.rule.observer.publishers.GenericNodeEventPublisher;
import com.ingsis.utils.rule.observer.publishers.factories.PublishersFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class EventsChecker implements Checker {
    private final Supplier<Map<Class<? extends Node>, GenericNodeEventPublisher<? extends Node>>>
            publishersSupplier;

    public EventsChecker(
            Supplier<Map<Class<? extends Node>, GenericNodeEventPublisher<? extends Node>>>
                    publishersSupplier) {
        this.publishersSupplier = publishersSupplier;
    }

    public EventsChecker(PublishersFactory publishersFactory) {
        this(
                () -> {
                    HashMap<Class<? extends Node>, GenericNodeEventPublisher<? extends Node>> map =
                            new HashMap<>();
                    map.put(
                            DeclarationKeywordNode.class,
                            publishersFactory.createLetNodePublisher());
                    map.put(
                            IfKeywordNode.class,
                            publishersFactory.createConditionalNodePublisher());
                    map.put(
                            ExpressionNode.class,
                            publishersFactory.createExpressionNodePublisher());
                    return map;
                });
    }

    @Override
    public Result<String> check(IfKeywordNode ifKeywordNode) {
        for (Node child : ifKeywordNode.thenBody()) {
            Result<String> checkChildResult = ((Checkable) child).acceptChecker(this);
            if (!checkChildResult.isCorrect()) {
                return checkChildResult;
            }
        }
        for (Node child : ifKeywordNode.elseBody()) {
            Result<String> checkChildResult = ((Checkable) child).acceptChecker(this);
            if (!checkChildResult.isCorrect()) {
                return checkChildResult;
            }
        }
        return dispatch(ifKeywordNode, IfKeywordNode.class);
    }

    @Override
    public Result<String> check(DeclarationKeywordNode letKeywordNode) {
        return dispatch(letKeywordNode, DeclarationKeywordNode.class);
    }

    @Override
    public Result<String> check(ExpressionNode expressionNode) {
        return dispatch(expressionNode, ExpressionNode.class);
    }

    @SuppressWarnings("unchecked")
    private <T extends Node> Result<String> dispatch(T node, Class<?> mapClass) {
        Map<Class<? extends Node>, GenericNodeEventPublisher<? extends Node>> eventPublishers =
                publishersSupplier.get();
        if (!eventPublishers.containsKey(mapClass)) {
            return new IncorrectResult<>("No publisher for node type: " + node.getClass());
        }
        return ((GenericNodeEventPublisher<T>) eventPublishers.get(mapClass)).notify(node);
    }
}
