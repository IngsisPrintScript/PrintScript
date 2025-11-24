package com.ingsis.nodes.expression.identifier;

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

public class IdentifierNodeTest {

    private IdentifierNode identifier;

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
                return new CorrectResult<>("value");
            }
        };

        Result<String> goodResult = identifier.acceptInterpreter(good);
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

        Result<String> badResult = identifier.acceptInterpreter(bad);
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
            public Result<String> visit(IdentifierNode identifierNode) {
                return new CorrectResult<>("visited");
            }

            @Override
            public Result<String> visit(com.ingsis.nodes.expression.literal.LiteralNode literalNode) {
                return new CorrectResult<>("ok");
            }

            @Override
            public Result<String> visit(com.ingsis.nodes.type.TypeNode typeNode) {
                return new CorrectResult<>("ok");
            }
        };

        Result<String> checkResult = identifier.acceptChecker(checker);
        assertEquals("checked", checkResult.result());

        Result<String> visitResult = identifier.acceptVisitor(visitor);
        assertEquals("visited", visitResult.result());
    }
}
