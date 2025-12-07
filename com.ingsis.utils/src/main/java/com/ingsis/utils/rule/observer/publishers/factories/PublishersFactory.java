/*
 * My Project
 */

package com.ingsis.utils.rule.observer.publishers.factories;

import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.rule.observer.publishers.GenericNodeEventPublisher;

public interface PublishersFactory {
    GenericNodeEventPublisher<DeclarationKeywordNode> createLetNodePublisher();

    GenericNodeEventPublisher<IfKeywordNode> createConditionalNodePublisher();

    GenericNodeEventPublisher<ExpressionNode> createExpressionNodePublisher();
}
