/*
 * My Project
 */

package com.ingsis.semantic.checkers.publishers.factories;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.semantic.checkers.publishers.GenericNodeEventPublisher;

public interface PublishersFactory {
  GenericNodeEventPublisher<DeclarationKeywordNode> createLetNodePublisher();

  GenericNodeEventPublisher<IfKeywordNode> createConditionalNodePublisher();

  GenericNodeEventPublisher<ExpressionNode> createExpressionNodePublisher();
}
