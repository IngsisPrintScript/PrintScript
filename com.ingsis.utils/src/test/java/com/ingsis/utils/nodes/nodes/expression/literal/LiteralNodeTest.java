/*
 * My Project
 */

package com.ingsis.utils.nodes.nodes.expression.literal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.function.CallFunctionNode;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.nodes.expression.operator.BinaryOperatorNode;
import com.ingsis.utils.nodes.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.utils.nodes.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.nodes.nodes.type.TypeNode;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.nodes.visitors.Visitor;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LiteralNodeTest {

    private LiteralNode literal;

    private static final Interpreter GOOD_INTERPRETER =
            new Interpreter() {
                @Override
                public Result<String> interpret(IfKeywordNode ifKeywordNode) {
                    return new CorrectResult<>("ok");
                }

                @Override
                public Result<String> interpret(DeclarationKeywordNode declarationKeywordNode) {
                    return new CorrectResult<>("ok");
                }

                @Override
                public Result<Object> interpret(ExpressionNode expressionNode) {
                    return new CorrectResult<>(123);
                }
            };

    private static final Interpreter BAD_INTERPRETER =
            new Interpreter() {
                @Override
                public Result<String> interpret(IfKeywordNode ifKeywordNode) {
                    return new CorrectResult<>("ok");
                }

                @Override
                public Result<String> interpret(DeclarationKeywordNode declarationKeywordNode) {
                    return new CorrectResult<>("ok");
                }

                @Override
                public Result<Object> interpret(ExpressionNode expressionNode) {
                    return new IncorrectResult<>("err");
                }
            };

    private static final Checker SIMPLE_CHECKER =
            new Checker() {
                @Override
                public Result<String> check(IfKeywordNode ifKeywordNode) {
                    return new CorrectResult<>("ok");
                }

                @Override
                public Result<String> check(DeclarationKeywordNode declarationKeywordNode) {
                    return new CorrectResult<>("ok");
                }

                @Override
                public Result<String> check(ExpressionNode expressionNode) {
                    return new CorrectResult<>("checked");
                }
            };

    private static final Visitor SIMPLE_VISITOR =
            new Visitor() {
                @Override
                public Result<String> visit(IfKeywordNode ifKeywordNode) {
                    return new CorrectResult<>("ok");
                }

                @Override
                public Result<String> visit(DeclarationKeywordNode declarationKeywordNode) {
                    return new CorrectResult<>("ok");
                }

                @Override
                public Result<String> visit(CallFunctionNode callFunctionNode) {
                    return new CorrectResult<>("ok");
                }

                @Override
                public Result<String> visit(BinaryOperatorNode binaryOperatorNode) {
                    return new CorrectResult<>("ok");
                }

                @Override
                public Result<String> visit(TypeAssignationNode typeAssignationNode) {
                    return new CorrectResult<>("ok");
                }

                @Override
                public Result<String> visit(ValueAssignationNode valueAssignationNode) {
                    return new CorrectResult<>("ok");
                }

                @Override
                public Result<String> visit(IdentifierNode identifierNode) {
                    return new CorrectResult<>("ok");
                }

                @Override
                public Result<String> visit(LiteralNode literalNode) {
                    return new CorrectResult<>("visited");
                }

                @Override
                public Result<String> visit(TypeNode typeNode) {
                    return new CorrectResult<>("ok");
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
