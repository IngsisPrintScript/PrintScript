/*
 * My Project
 */

package com.ingsis.nodes.expression.identifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ingsis.result.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IdentifierNodeTest {

    private IdentifierNode identifier;

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
                    return new com.ingsis.result.CorrectResult<>("value");
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
                    return new com.ingsis.result.CorrectResult<>("visited");
                }

                @Override
                public com.ingsis.result.Result<String> visit(
                        com.ingsis.nodes.expression.literal.LiteralNode literalNode) {
                    return new com.ingsis.result.CorrectResult<>("ok");
                }

                @Override
                public com.ingsis.result.Result<String> visit(
                        com.ingsis.nodes.type.TypeNode typeNode) {
                    return new com.ingsis.result.CorrectResult<>("ok");
                }
            };

    @BeforeEach
    public void setUp() {
        identifier = new IdentifierNode("x", 1, 2);
    }

    @Test
    public void accessorsReturnValues() {
        assertEquals("x", identifier.name());
        assertEquals(Integer.valueOf(1), identifier.line());
        assertEquals(Integer.valueOf(2), identifier.column());
    }

    @Test
    public void symbolThrowsUnsupported() {
        assertThrows(UnsupportedOperationException.class, () -> identifier.symbol());
    }

    @Test
    public void acceptInterpreterReturnsCorrectAndIncorrectVariants() {
        Result<String> goodResult = identifier.acceptInterpreter(GOOD_INTERPRETER);
        assertEquals("Interpreted successfully.", goodResult.result());

        Result<String> badResult = identifier.acceptInterpreter(BAD_INTERPRETER);
        assertEquals("err", badResult.error());
    }

    @Test
    public void acceptCheckerAndVisitorDelegate() {
        Result<String> checkResult = identifier.acceptChecker(SIMPLE_CHECKER);
        assertEquals("checked", checkResult.result());

        Result<String> visitResult = identifier.acceptVisitor(SIMPLE_VISITOR);
        assertEquals("visited", visitResult.result());
    }
}
