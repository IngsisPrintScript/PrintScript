package com.ingsis.nodes.expression.literal;

import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.visitors.Checker;
import com.ingsis.visitors.Interpreter;
import com.ingsis.visitors.Visitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LiteralNodeTest {

    private LiteralNode literal;

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
        Interpreter good = new Interpreter() {
            @Override
            public Result<String> interpret(com.ingsis.nodes.keyword.IfKeywordNode ifKeywordNode) {
                return new CorrectResult<>("ok");
            }

            @Override
            public Result<String> interpret(com.ingsis.nodes.keyword.DeclarationKeywordNode declarationKeywordNode) {
                return new CorrectResult<>("ok");
            }

            @Override
            public Result<Object> interpret(com.ingsis.nodes.expression.ExpressionNode expressionNode) {
                return new CorrectResult<>(123);
            }
        };

        Result<String> goodResult = literal.acceptInterpreter(good);
        assertEquals("Interpreted successfully.", goodResult.result());

        Interpreter bad = new Interpreter() {
            @Override
            public Result<String> interpret(com.ingsis.nodes.keyword.IfKeywordNode ifKeywordNode) {
                return new CorrectResult<>("ok");
            }

            @Override
            public Result<String> interpret(com.ingsis.nodes.keyword.DeclarationKeywordNode declarationKeywordNode) {
                return new CorrectResult<>("ok");
            }

            @Override
            public Result<Object> interpret(com.ingsis.nodes.expression.ExpressionNode expressionNode) {
                return new IncorrectResult<>("err");
            }
        };

        Result<String> badResult = literal.acceptInterpreter(bad);
        assertEquals("err", badResult.error());
    }

    @Test
    public void acceptCheckerAndVisitorDelegate() {
        Checker checker = new Checker() {
            @Override
            public Result<String> check(com.ingsis.nodes.keyword.IfKeywordNode ifKeywordNode) {
                return new CorrectResult<>("ok");
            }

            @Override
            public Result<String> check(com.ingsis.nodes.keyword.DeclarationKeywordNode declarationKeywordNode) {
                return new CorrectResult<>("ok");
            }

            @Override
            public Result<String> check(com.ingsis.nodes.expression.ExpressionNode expressionNode) {
                return new CorrectResult<>("checked");
            }
        };

        Visitor visitor = new Visitor() {
            @Override
            public Result<String> visit(com.ingsis.nodes.keyword.IfKeywordNode ifKeywordNode) {
                return new CorrectResult<>("ok");
            }

            @Override
            public Result<String> visit(com.ingsis.nodes.keyword.DeclarationKeywordNode declarationKeywordNode) {
                return new CorrectResult<>("ok");
            }

            @Override
            public Result<String> visit(com.ingsis.nodes.expression.function.CallFunctionNode callFunctionNode) {
                return new CorrectResult<>("ok");
            }

            @Override
            public Result<String> visit(com.ingsis.nodes.expression.operator.BinaryOperatorNode binaryOperatorNode) {
                return new CorrectResult<>("ok");
            }

            @Override
            public Result<String> visit(com.ingsis.nodes.expression.operator.TypeAssignationNode typeAssignationNode) {
                return new CorrectResult<>("ok");
            }

            @Override
            public Result<String> visit(com.ingsis.nodes.expression.operator.ValueAssignationNode valueAssignationNode) {
                return new CorrectResult<>("ok");
            }

            @Override
            public Result<String> visit(com.ingsis.nodes.expression.identifier.IdentifierNode identifierNode) {
                return new CorrectResult<>("ok");
            }

            @Override
            public Result<String> visit(LiteralNode literalNode) {
                return new CorrectResult<>("visited");
            }

            @Override
            public Result<String> visit(com.ingsis.nodes.type.TypeNode typeNode) {
                return new CorrectResult<>("ok");
            }
        };

        Result<String> checkResult = literal.acceptChecker(checker);
        assertEquals("checked", checkResult.result());

        Result<String> visitResult = literal.acceptVisitor(visitor);
        assertEquals("visited", visitResult.result());
    }
}
