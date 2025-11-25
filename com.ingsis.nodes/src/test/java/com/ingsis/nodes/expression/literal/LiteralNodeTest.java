/*
 * My Project
 */

package com.ingsis.nodes.expression.literal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ingsis.result.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LiteralNodeTest {

    private LiteralNode literal;

    private static final com.ingsis.visitors.Interpreter GOOD_INTERPRETER =
            new com.ingsis.visitors.Interpreter() {
                @Override
                public com.ingsis.result.Result<String> interpret(
                        com.ingsis.nodes.keyword.IfKeywordNode ifKeywordNode) {
                    return new com.ingsis.result.CorrectResult<>("ok");
                }

                @Override
                public com.ingsis.result.Result<String> interpret(
                        com.ingsis.nodes.keyword.DeclarationKeywordNode declarationKeywordNode) {
                    return new com.ingsis.result.CorrectResult<>("ok");
                }

                @Override
                public com.ingsis.result.Result<Object> interpret(
                        com.ingsis.nodes.expression.ExpressionNode expressionNode) {
                    return new com.ingsis.result.CorrectResult<>(123);
                }
            };

    private static final com.ingsis.visitors.Interpreter BAD_INTERPRETER =
            new com.ingsis.visitors.Interpreter() {
                @Override
                public com.ingsis.result.Result<String> interpret(
                        com.ingsis.nodes.keyword.IfKeywordNode ifKeywordNode) {
                    return new com.ingsis.result.CorrectResult<>("ok");
                }

                @Override
                public com.ingsis.result.Result<String> interpret(
                        com.ingsis.nodes.keyword.DeclarationKeywordNode declarationKeywordNode) {
                    return new com.ingsis.result.CorrectResult<>("ok");
                }

                @Override
                public com.ingsis.result.Result<Object> interpret(
                        com.ingsis.nodes.expression.ExpressionNode expressionNode) {
                    return new com.ingsis.result.IncorrectResult<>("err");
                }
            };

    private static final com.ingsis.visitors.Checker SIMPLE_CHECKER =
            new com.ingsis.visitors.Checker() {
                @Override
                public com.ingsis.result.Result<String> check(
                        com.ingsis.nodes.keyword.IfKeywordNode ifKeywordNode) {
                    return new com.ingsis.result.CorrectResult<>("ok");
                }

                @Override
                public com.ingsis.result.Result<String> check(
                        com.ingsis.nodes.keyword.DeclarationKeywordNode declarationKeywordNode) {
                    return new com.ingsis.result.CorrectResult<>("ok");
                }

                @Override
                public com.ingsis.result.Result<String> check(
                        com.ingsis.nodes.expression.ExpressionNode expressionNode) {
                    return new com.ingsis.result.CorrectResult<>("checked");
                }
            };

    private static final com.ingsis.visitors.Visitor SIMPLE_VISITOR =
            new com.ingsis.visitors.Visitor() {
                @Override
                public com.ingsis.result.Result<String> visit(
                        com.ingsis.nodes.keyword.IfKeywordNode ifKeywordNode) {
                    return new com.ingsis.result.CorrectResult<>("ok");
                }

                @Override
                public com.ingsis.result.Result<String> visit(
                        com.ingsis.nodes.keyword.DeclarationKeywordNode declarationKeywordNode) {
                    return new com.ingsis.result.CorrectResult<>("ok");
                }

                @Override
                public com.ingsis.result.Result<String> visit(
                        com.ingsis.nodes.expression.function.CallFunctionNode callFunctionNode) {
                    return new com.ingsis.result.CorrectResult<>("ok");
                }

                @Override
                public com.ingsis.result.Result<String> visit(
                        com.ingsis.nodes.expression.operator.BinaryOperatorNode
                                binaryOperatorNode) {
                    return new com.ingsis.result.CorrectResult<>("ok");
                }

                @Override
                public com.ingsis.result.Result<String> visit(
                        com.ingsis.nodes.expression.operator.TypeAssignationNode
                                typeAssignationNode) {
                    return new com.ingsis.result.CorrectResult<>("ok");
                }

                @Override
                public com.ingsis.result.Result<String> visit(
                        com.ingsis.nodes.expression.operator.ValueAssignationNode
                                valueAssignationNode) {
                    return new com.ingsis.result.CorrectResult<>("ok");
                }

                @Override
                public com.ingsis.result.Result<String> visit(
                        com.ingsis.nodes.expression.identifier.IdentifierNode identifierNode) {
                    return new com.ingsis.result.CorrectResult<>("ok");
                }

                @Override
                public com.ingsis.result.Result<String> visit(LiteralNode literalNode) {
                    return new com.ingsis.result.CorrectResult<>("visited");
                }

                @Override
                public com.ingsis.result.Result<String> visit(
                        com.ingsis.nodes.type.TypeNode typeNode) {
                    return new com.ingsis.result.CorrectResult<>("ok");
                }
            };

    @BeforeEach
    public void setUp() {
        literal = new LiteralNode("42", 2, 3);
    }

    @Test
    public void accessorsReturnValues() {
        assertEquals("42", literal.value());
        assertEquals(Integer.valueOf(2), literal.line());
        assertEquals(Integer.valueOf(3), literal.column());
    }

    @Test
    public void symbolThrowsUnsupported() {
        assertThrows(UnsupportedOperationException.class, () -> literal.symbol());
    }

    @Test
    public void acceptInterpreterReturnsCorrectAndIncorrectVariants() {
        Result<String> goodResult = literal.acceptInterpreter(GOOD_INTERPRETER);
        assertEquals("Interpreted successfully.", goodResult.result());

        Result<String> badResult = literal.acceptInterpreter(BAD_INTERPRETER);
        assertEquals("err", badResult.error());
    }

    @Test
    public void acceptCheckerAndVisitorDelegate() {
        Result<String> checkResult = literal.acceptChecker(SIMPLE_CHECKER);
        assertEquals("checked", checkResult.result());

        Result<String> visitResult = literal.acceptVisitor(SIMPLE_VISITOR);
        assertEquals("visited", visitResult.result());
    }
}
