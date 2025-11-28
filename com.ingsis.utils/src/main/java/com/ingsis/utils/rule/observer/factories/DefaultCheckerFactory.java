/*
 * My Project
 */

package com.ingsis.rule.observer.factories;

import com.ingsis.nodes.Node;
import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.rule.observer.EventsChecker;
import com.ingsis.rule.observer.publishers.GenericNodeEventPublisher;
import com.ingsis.rule.observer.publishers.factories.PublishersFactory;
import com.ingsis.visitors.Checker;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DefaultCheckerFactory implements CheckerFactory {

    public Checker createInMemoryEventBasedChecker(PublishersFactory publishersFactory) {

        Supplier<Map<Class<? extends Node>, GenericNodeEventPublisher<? extends Node>>> supplier =
                () -> {
                    Map<Class<? extends Node>, GenericNodeEventPublisher<? extends Node>> map =
                            new LinkedHashMap<>();

                    map.put(
                            IfKeywordNode.class,
                            publishersFactory.createConditionalNodePublisher());
                    map.put(
                            DeclarationKeywordNode.class,
                            publishersFactory.createLetNodePublisher());
                    map.put(
                            ExpressionNode.class,
                            publishersFactory.createExpressionNodePublisher());

                    return map;
                };

        return new EventsChecker(supplier);
    }
}
