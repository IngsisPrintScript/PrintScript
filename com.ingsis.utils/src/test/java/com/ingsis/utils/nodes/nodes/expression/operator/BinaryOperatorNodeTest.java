/*
 * My Project
 */

package com.ingsis.utils.nodes.nodes.expression.operator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.function.CallFunctionNode;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.nodes.expression.literal.LiteralNode;
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

public class BinaryOperatorNodeTest {

    private BinaryOperatorNode node;

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
                    return new CorrectResult<>(42);
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
                    return new CorrectResult<>("visited");
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
                    return new CorrectResult<>("ok");
                }

                @Override
                public Result<String> visit(TypeNode typeNode) {
                    return new CorrectResult<>("ok");
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

    @BeforeEach
    public void setUp() {
        IdentifierNode left = new IdentifierNode("a", 1, 1);
        IdentifierNode right = new IdentifierNode("b", 1, 2);
        node = new BinaryOperatorNode("+", left, right, 1, 3);
    }

    @Test
    public void childrenContainLeftAndRight() {
        assertEquals(2, node.children().size());
        assertEquals("a", ((IdentifierNode) node.children().get(0)).name());
        assertEquals("b", ((IdentifierNode) node.children().get(1)).name());
    }

    @Test
    public void isTerminalIsFalse() {
        assertEquals(Boolean.FALSE, node.isTerminalNode());
    }

    @Test
    public void acceptInterpreterProducesCorrectOrIncorrect() {
        Result<String> res = node.acceptInterpreter(GOOD_INTERPRETER);
        assertEquals("Interpreted successfully.", res.result());

        Result<String> resBad = node.acceptInterpreter(BAD_INTERPRETER);
        assertEquals("err", resBad.error());
    }

    @Test
    public void acceptVisitorAndCheckerDelegate() {
        assertEquals("visited", node.acceptVisitor(SIMPLE_VISITOR).result());
        assertEquals("checked", node.acceptChecker(SIMPLE_CHECKER).result());
    }
}
