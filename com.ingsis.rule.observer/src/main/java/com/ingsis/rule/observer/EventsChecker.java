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
import com.ingsis.rule.observer.publishers.factories.PublishersFactory;
import com.ingsis.visitors.Checkable;
import com.ingsis.visitors.Checker;
import java.util.LinkedHashMap;
import java.util.Map;

public final class EventsChecker implements Checker {
    private final Map<Class<? extends Node>, GenericNodeEventPublisher<? extends Node>>
            eventPublishers;

    public EventsChecker(PublishersFactory publishersFactory) {
        this.eventPublishers = new LinkedHashMap<>();
        eventPublishers.put(
                IfKeywordNode.class, publishersFactory.createConditionalNodePublisher());
        eventPublishers.put(
                DeclarationKeywordNode.class, publishersFactory.createLetNodePublisher());
        eventPublishers.put(
                ExpressionNode.class, publishersFactory.createExpressionNodePublisher());
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
        if (!eventPublishers.containsKey(mapClass)) {
            return new IncorrectResult<>("No publisher for node type: " + node.getClass());
        }
        return ((GenericNodeEventPublisher<T>) eventPublishers.get(mapClass)).notify(node);
    }
}
