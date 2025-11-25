/*
 * My Project
 */

package com.ingsis.formatter.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.nodes.Node;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.factory.DefaultResultFactory;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.visitors.Checkable;
import com.ingsis.visitors.Checker;
import java.util.List;
import java.util.function.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FormatterConditionalHandlerTest {
    private ResultFactory resultFactory;
    private Supplier<Checker> checkerSupplier;
    private FormatterConditionalHandler handler;

    static class TestCheckableNode implements Node, Checkable {
        private final com.ingsis.result.CorrectResult<String> result;

        TestCheckableNode(String s) {
            this.result = new com.ingsis.result.CorrectResult<>(s);
        }

        @Override
        public com.ingsis.result.Result<String> acceptChecker(Checker checker) {
            return result;
        }

        @Override
        public Integer line() {
            return 1;
        }

        @Override
        public Integer column() {
            return 1;
        }

        @Override
        public com.ingsis.result.Result<String> acceptVisitor(com.ingsis.visitors.Visitor visitor) {
            return new com.ingsis.result.CorrectResult<>("visited");
        }
    }

    @BeforeEach
    void setup() {
        resultFactory = new DefaultResultFactory();
        checkerSupplier =
                () ->
                        new Checker() {
                            @Override
                            public com.ingsis.result.Result<String> check(
                                    com.ingsis.nodes.keyword.IfKeywordNode ifKeywordNode) {
                                return resultFactory.createCorrectResult("if-block");
                            }

                            @Override
                            public com.ingsis.result.Result<String> check(
                                    com.ingsis.nodes.keyword.DeclarationKeywordNode
                                            declarationKeywordNode) {
                                return resultFactory.createCorrectResult("decl");
                            }

                            @Override
                            public com.ingsis.result.Result<String> check(
                                    com.ingsis.nodes.expression.ExpressionNode expressionNode) {
                                return resultFactory.createCorrectResult("cond");
                            }
                        };
        handler = new FormatterConditionalHandler(checkerSupplier, resultFactory);
    }

    @Test
    void handleFormatsConditionAndChildren() {
        IdentifierNode cond = new IdentifierNode("c", 1, 1);
        TestCheckableNode thenNode = new TestCheckableNode("thenFormatted");
        TestCheckableNode elseNode = new TestCheckableNode("elseFormatted");
        IfKeywordNode ifNode = new IfKeywordNode(cond, List.of(thenNode), List.of(elseNode), 1, 1);

        CorrectResult<String> r = (CorrectResult<String>) handler.handle(ifNode);
        String expected = "if(cond){\n\tthenFormatted\telseFormatted}\n";
        assertEquals(expected, r.result());
    }

    @Test
    void handleWhenExpressionFailsReturnsClonedIncorrect() {
        Supplier<Checker> badSupplier =
                () ->
                        new Checker() {
                            @Override
                            public com.ingsis.result.Result<String> check(
                                    com.ingsis.nodes.keyword.IfKeywordNode ifKeywordNode) {
                                return resultFactory.createIncorrectResult("bad cond");
                            }

                            @Override
                            public com.ingsis.result.Result<String> check(
                                    com.ingsis.nodes.keyword.DeclarationKeywordNode
                                            declarationKeywordNode) {
                                return resultFactory.createCorrectResult("ok");
                            }

                            @Override
                            public com.ingsis.result.Result<String> check(
                                    com.ingsis.nodes.expression.ExpressionNode expressionNode) {
                                return resultFactory.createIncorrectResult("bad cond");
                            }
                        };
        FormatterConditionalHandler badHandler =
                new FormatterConditionalHandler(badSupplier, resultFactory);

        IdentifierNode cond = new IdentifierNode("c", 1, 1);
        IfKeywordNode ifNode = new IfKeywordNode(cond, List.of(), List.of(), 1, 1);

        IncorrectResult<String> r = (IncorrectResult<String>) badHandler.handle(ifNode);
        assertEquals("bad cond", r.error());
    }
}
