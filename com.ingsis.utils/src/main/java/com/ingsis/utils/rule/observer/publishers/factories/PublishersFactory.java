/*
 * My Project
 */

package com.ingsis.utils.rule.observer.publishers.factories; /*
                                                              * My Project
                                                              */

import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.rule.observer.publishers.GenericNodeEventPublisher;

public interface PublishersFactory {
    GenericNodeEventPublisher<DeclarationKeywordNode> createLetNodePublisher();

    GenericNodeEventPublisher<IfKeywordNode> createConditionalNodePublisher();

    GenericNodeEventPublisher<ExpressionNode> createExpressionNodePublisher();
}
