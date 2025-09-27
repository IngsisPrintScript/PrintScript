/*
 * My Project
 */

package com.ingsis.semantic.checkers;

import com.ingsis.nodes.Node;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.nodes.expression.operator.BinaryOperatorNode;
import com.ingsis.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.nodes.keyword.LetKeywordNode;
import com.ingsis.nodes.type.TypeNode;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.semantic.checkers.publishers.GenericNodeEventPublisher;
import com.ingsis.visitors.Checker;
import java.util.LinkedHashMap;
import java.util.Map;

public final class EventsChecker implements Checker {
    private final Map<Class<? extends Node>, GenericNodeEventPublisher<? extends Node>>
            eventPublishers;

    public EventsChecker() {
        this.eventPublishers = new LinkedHashMap<>();
    }

    @Override
    public Result<String> check(IfKeywordNode ifKeywordNode) {
        return dispatch(ifKeywordNode);
    }

    @Override
    public Result<String> check(LetKeywordNode letKeywordNode) {
        return dispatch(letKeywordNode);
    }

    @Override
    public Result<String> check(BinaryOperatorNode binaryOperatorNode) {
        return dispatch(binaryOperatorNode);
    }

    @Override
    public Result<String> check(TypeAssignationNode typeAssignationNode) {
        return dispatch(typeAssignationNode);
    }

    @Override
    public Result<String> check(ValueAssignationNode valueAssignationNode) {
        return dispatch(valueAssignationNode);
    }

    @Override
    public Result<String> check(IdentifierNode identifierNode) {
        return dispatch(identifierNode);
    }

    @Override
    public Result<String> check(LiteralNode literalNode) {
        return dispatch(literalNode);
    }

    @Override
    public Result<String> check(TypeNode typeNode) {
        return dispatch(typeNode);
    }

    @SuppressWarnings("unchecked")
    private <T extends Node> Result<String> dispatch(T node) {
        Class<T> nodeClass = (Class<T>) node.getClass();
        GenericNodeEventPublisher<T> eventPublisher =
                (GenericNodeEventPublisher<T>) eventPublishers.get(nodeClass);
        if (eventPublisher == null) {
            return new IncorrectResult<>("No publisher for node type: " + nodeClass);
        }
        return eventPublisher.notify(node);
    }
}
