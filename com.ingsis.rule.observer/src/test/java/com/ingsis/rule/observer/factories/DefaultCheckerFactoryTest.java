/*
 * My Project
 */

package com.ingsis.rule.observer.factories;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.rule.observer.publishers.factories.PublishersFactory;
import org.junit.jupiter.api.Test;

class DefaultCheckerFactoryTest {
    @Test
    void createsChecker() {
        DefaultCheckerFactory f = new DefaultCheckerFactory();
        PublishersFactory pf =
                new PublishersFactory() {
                    @Override
                    public com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<
                                    com.ingsis.nodes.keyword.DeclarationKeywordNode>
                            createLetNodePublisher() {
                        return null;
                    }

                    @Override
                    public com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<
                                    com.ingsis.nodes.keyword.IfKeywordNode>
                            createConditionalNodePublisher() {
                        return null;
                    }

                    @Override
                    public com.ingsis.rule.observer.publishers.GenericNodeEventPublisher<
                                    com.ingsis.nodes.expression.ExpressionNode>
                            createExpressionNodePublisher() {
                        return null;
                    }
                };
        assertTrue(f.createInMemoryEventBasedChecker(pf) instanceof com.ingsis.visitors.Checker);
    }
}
