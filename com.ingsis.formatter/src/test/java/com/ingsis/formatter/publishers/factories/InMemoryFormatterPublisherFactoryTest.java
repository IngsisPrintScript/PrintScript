/*
 * My Project
 */

package com.ingsis.formatter.publishers.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.literal.LiteralNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import com.ingsis.utils.rule.observer.handlers.factories.HandlerFactory;
import com.ingsis.utils.rule.observer.publishers.GenericNodeEventPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryFormatterPublisherFactoryTest {
    private ResultFactory resultFactory;

    @BeforeEach
    void setup() {
        resultFactory = new DefaultResultFactory();
    }

    @Test
    void createPublishersWhenHandlersReturnCorrect() {
        // given: a handler factory that returns handlers producing correct results
        HandlerFactory hf =
                new HandlerFactory() {
                    @Override
                    public NodeEventHandler<DeclarationKeywordNode> createDeclarationHandler() {
                        return n -> resultFactory.createCorrectResult("decl-ok");
                    }

                    @Override
                    public NodeEventHandler<IfKeywordNode> createConditionalHandler() {
                        return n -> resultFactory.createCorrectResult("if-ok");
                    }

                    @Override
                    public NodeEventHandler<ExpressionNode> createExpressionHandler() {
                        return n -> resultFactory.createCorrectResult("expr-ok");
                    }
                };

        InMemoryFormatterPublisherFactory factory = new InMemoryFormatterPublisherFactory(hf);

        // when: create publishers
        GenericNodeEventPublisher<DeclarationKeywordNode> declPub =
                factory.createLetNodePublisher();
        GenericNodeEventPublisher<IfKeywordNode> condPub = factory.createConditionalNodePublisher();
        GenericNodeEventPublisher<ExpressionNode> exprPub = factory.createExpressionNodePublisher();

        // then: publishers behave correctly
        assertPublishersReturnCorrect(declPub, condPub, exprPub);
    }

    @Test
    void createPublishersWhenHandlerReturnsIncorrect() {
        // given: a handler factory where expression handler returns incorrect
        HandlerFactory hf =
                new HandlerFactory() {
                    @Override
                    public NodeEventHandler<DeclarationKeywordNode> createDeclarationHandler() {
                        return n -> resultFactory.createCorrectResult("decl-ok");
                    }

                    @Override
                    public NodeEventHandler<IfKeywordNode> createConditionalHandler() {
                        return n -> resultFactory.createCorrectResult("if-ok");
                    }

                    @Override
                    public NodeEventHandler<ExpressionNode> createExpressionHandler() {
                        return n -> resultFactory.createIncorrectResult("expr-bad");
                    }
                };

        InMemoryFormatterPublisherFactory factory = new InMemoryFormatterPublisherFactory(hf);
        GenericNodeEventPublisher<ExpressionNode> exprPub = factory.createExpressionNodePublisher();

        // when
        Result<String> r = exprPub.notify(new LiteralNode("1", 1, 1));

        // then: incorrect result is propagated
        assertEquals(true, !r.isCorrect());
        assertEquals(((IncorrectResult<String>) r).error(), "expr-bad");
    }

    private void assertPublishersReturnCorrect(
            GenericNodeEventPublisher<DeclarationKeywordNode> declPub,
            GenericNodeEventPublisher<IfKeywordNode> condPub,
            GenericNodeEventPublisher<ExpressionNode> exprPub) {
        assertNotNull(declPub);
        assertNotNull(condPub);
        assertNotNull(exprPub);

        Result<String> rd = declPub.notify(new DeclarationKeywordNode(null, null, true, 1, 1));
        Result<String> rc =
                condPub.notify(
                        new IfKeywordNode(
                                new LiteralNode("x", 1, 1),
                                java.util.List.of(),
                                java.util.List.of(),
                                1,
                                1));
        Result<String> re = exprPub.notify(new LiteralNode("1", 1, 1));

        assertEquals(true, rd.isCorrect());
        assertEquals(true, rc.isCorrect());
        assertEquals(true, re.isCorrect());
        assertEquals(
                ((CorrectResult<String>) rd).result(),
                "Let node passed the following semantic rules:");
    }
}
