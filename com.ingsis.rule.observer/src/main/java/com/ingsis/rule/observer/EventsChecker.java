/*
 * My Project
 */

package com.ingsis.rule.observer;

import com.ingsis.nodes.Node;
import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.rule.observer.publishers.GenericNodeEventPublisher;
import com.ingsis.visitors.Checkable;
import com.ingsis.visitors.Checker;
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
