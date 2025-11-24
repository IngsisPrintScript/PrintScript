/*
 * My Project
 */

package com.ingsis.nodes.expression.operator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.visitors.Interpreter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BinaryOperatorNodeTest {

    private BinaryOperatorNode node;

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
        Interpreter good =
                new Interpreter() {
                    @Override
                    public Result<String> interpret(
                            com.ingsis.nodes.keyword.IfKeywordNode ifKeywordNode) {
                        return new CorrectResult<>("ok");
                    }

                    @Override
                    public Result<String> interpret(
                            com.ingsis.nodes.keyword.DeclarationKeywordNode
                                    declarationKeywordNode) {
                        return new CorrectResult<>("ok");
                    }

                    @Override
                    public Result<Object> interpret(
                            com.ingsis.nodes.expression.ExpressionNode expressionNode) {
                        return new CorrectResult<>(42);
                    }
                };

        Result<String> res = node.acceptInterpreter(good);
        assertEquals("Interpreted successfully.", res.result());

        Interpreter bad =
                new Interpreter() {
                    @Override
                    public Result<String> interpret(
                            com.ingsis.nodes.keyword.IfKeywordNode ifKeywordNode) {
                        return new CorrectResult<>("ok");
                    }

                    @Override
                    public Result<String> interpret(
                            com.ingsis.nodes.keyword.DeclarationKeywordNode
                                    declarationKeywordNode) {
                        return new CorrectResult<>("ok");
                    }

                    @Override
                    public Result<Object> interpret(
                            com.ingsis.nodes.expression.ExpressionNode expressionNode) {
                        return new IncorrectResult<>("err");
                    }
                };

        Result<String> resBad = node.acceptInterpreter(bad);
        assertEquals("err", resBad.error());
    }

    @Test
    public void acceptVisitorAndCheckerDelegate() {
        com.ingsis.visitors.Visitor visitor =
                new com.ingsis.visitors.Visitor() {
                    @Override
                    public com.ingsis.result.Result<String> visit(
                            com.ingsis.nodes.keyword.IfKeywordNode ifKeywordNode) {
                        return new com.ingsis.result.CorrectResult<>("ok");
                    }

                    @Override
                    public com.ingsis.result.Result<String> visit(
                            com.ingsis.nodes.keyword.DeclarationKeywordNode
                                    declarationKeywordNode) {
                        return new com.ingsis.result.CorrectResult<>("ok");
                    }

                    @Override
                    public com.ingsis.result.Result<String> visit(
                            com.ingsis.nodes.expression.function.CallFunctionNode
                                    callFunctionNode) {
                        return new com.ingsis.result.CorrectResult<>("ok");
                    }

                    @Override
                    public com.ingsis.result.Result<String> visit(
                            BinaryOperatorNode binaryOperatorNode) {
                        return new com.ingsis.result.CorrectResult<>("visited");
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

        com.ingsis.visitors.Checker checker =
                new com.ingsis.visitors.Checker() {
                    @Override
                    public com.ingsis.result.Result<String> check(
                            com.ingsis.nodes.keyword.IfKeywordNode ifKeywordNode) {
                        return new com.ingsis.result.CorrectResult<>("ok");
                    }

                    @Override
                    public com.ingsis.result.Result<String> check(
                            com.ingsis.nodes.keyword.DeclarationKeywordNode
                                    declarationKeywordNode) {
                        return new com.ingsis.result.CorrectResult<>("ok");
                    }

                    @Override
                    public com.ingsis.result.Result<String> check(
                            com.ingsis.nodes.expression.ExpressionNode expressionNode) {
                        return new com.ingsis.result.CorrectResult<>("checked");
                    }
                };

        assertEquals("visited", node.acceptVisitor(visitor).result());
        assertEquals("checked", node.acceptChecker(checker).result());
    }
}
