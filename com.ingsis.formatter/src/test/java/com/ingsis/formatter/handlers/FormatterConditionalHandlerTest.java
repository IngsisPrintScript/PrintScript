/*
 * My Project
 */

package com.ingsis.formatter.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Visitor;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.result.factory.ResultFactory;
import java.util.List;
import java.util.function.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FormatterConditionalHandlerTest {
    private ResultFactory resultFactory;
    private Supplier<Checker> checkerSupplier;
    private FormatterConditionalHandler handler;

    static class TestCheckableNode implements Node, Checkable {
        private final CorrectResult<String> result;

        TestCheckableNode(String s) {
            this.result = new CorrectResult<>(s);
        }

        @Override
        public Result<String> acceptChecker(Checker checker) {
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
        public Result<String> acceptVisitor(Visitor visitor) {
            return new CorrectResult<>("visited");
        }
    }

    @BeforeEach
    void setup() {
        resultFactory = new DefaultResultFactory();
        checkerSupplier =
                () ->
                        new Checker() {
                            @Override
                            public Result<String> check(IfKeywordNode ifKeywordNode) {
                                return resultFactory.createCorrectResult("if-block");
                            }

                            @Override
                            public Result<String> check(
                                    DeclarationKeywordNode declarationKeywordNode) {
                                return resultFactory.createCorrectResult("decl");
                            }

                            @Override
                            public Result<String> check(ExpressionNode expressionNode) {
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
                            public Result<String> check(IfKeywordNode ifKeywordNode) {
                                return resultFactory.createIncorrectResult("bad cond");
                            }

                            @Override
                            public Result<String> check(
                                    DeclarationKeywordNode declarationKeywordNode) {
                                return resultFactory.createCorrectResult("ok");
                            }

                            @Override
                            public Result<String> check(ExpressionNode expressionNode) {
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
