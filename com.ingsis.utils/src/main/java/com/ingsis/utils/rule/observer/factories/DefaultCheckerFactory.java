/*
 * My Project
 */

package com.ingsis.utils.rule.observer.factories; /*
                                                   * My Project
                                                   */

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.rule.observer.EventsChecker;
import com.ingsis.utils.rule.observer.publishers.GenericNodeEventPublisher;
import com.ingsis.utils.rule.observer.publishers.factories.PublishersFactory;
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
