/*
 * My Project
 */

package com.ingsis.formatter.handlers.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.result.factory.DefaultResultFactory;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.rule.status.provider.RuleStatusProvider;
import com.ingsis.visitors.Checker;
import java.util.function.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InMemoryFormatterHandlerFactoryTest {
    private ResultFactory resultFactory;
    private RuleStatusProvider ruleStatusProvider;
    private Supplier<Checker> checkerSupplier;
    private InMemoryFormatterHandlerFactory factory;

    @BeforeEach
    public void setUp() {
        // given: permissive ResultFactory, RuleStatusProvider and Checker
        this.resultFactory = new DefaultResultFactory();
        this.ruleStatusProvider =
                new RuleStatusProvider() {
                    @Override
                    public Boolean getRuleStatus(String ruleName) {
                        return true;
                    }

                    @Override
                    public <T> T getRuleValue(String ruleName, Class<T> type) {
                        // return a simple integer 1 for println separation lines when requested
                        if (type.isAssignableFrom(Integer.class)) {
                            return type.cast(1);
                        }
                        return null;
                    }
                };

        this.checkerSupplier =
                () ->
                        new Checker() {
                            @Override
                            public com.ingsis.result.Result<String> check(
                                    IfKeywordNode ifKeywordNode) {
                                return resultFactory.createCorrectResult("ok");
                            }

                            @Override
                            public com.ingsis.result.Result<String> check(
                                    DeclarationKeywordNode declarationKeywordNode) {
                                return resultFactory.createCorrectResult("ok");
                            }

                            @Override
                            public com.ingsis.result.Result<String> check(
                                    com.ingsis.nodes.expression.ExpressionNode expressionNode) {
                                return resultFactory.createCorrectResult("ok");
                            }
                        };

        this.factory =
                new InMemoryFormatterHandlerFactory(
                        resultFactory, ruleStatusProvider, checkerSupplier);
    }

    @Test
    public void createHandlersPartialBehaviour() {
        // expression handler should be available
        var expr = factory.createExpressionHandler();
        assertNotNull(expr);

        // declaration and conditional handlers should also be created successfully
        var decl = factory.createDeclarationHandler();
        assertNotNull(decl);

        var cond = factory.createConditionalHandler();
        assertNotNull(cond);

        // optionally, verify that handlers can handle nodes
        var literal = new LiteralNode("42", 1, 1);
        assertTrue(expr.handle(literal).isCorrect());
    }

    @Test
    public void expressionHandlerHandlesLiteralAndIdentifier() {
        // when
        var exprHandler = factory.createExpressionHandler();
        var literal = new LiteralNode("42", 1, 1);
        var identifier = new IdentifierNode("x", 1, 1);

        var r1 = exprHandler.handle(literal);
        var r2 = exprHandler.handle(identifier);

        // then: handlers return the literal value / identifier name
        assertTrue(r1.isCorrect());
        assertTrue(r2.isCorrect());
        assertEquals("42", r1.result());
        assertEquals("x", r2.result());
    }
}
