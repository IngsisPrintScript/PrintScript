/*
 * My Project
 */

package com.ingsis.semantic.checkers.publishers.factories;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.keyword.LetKeywordNode;
import com.ingsis.semantic.checkers.publishers.GenericNodeEventPublisher;

public interface PublishersFactory {
  GenericNodeEventPublisher<LetKeywordNode> createLetNodePublisher();

  GenericNodeEventPublisher<ExpressionNode> createExpressionNodePublisher();
}
