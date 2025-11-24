/*
 * My Project
 */

package com.ingsis.nodes.expression.function;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.visitors.Interpreter;
import com.ingsis.visitors.Visitor;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CallFunctionNodeTest {

    private CallFunctionNode callNode;

    @BeforeEach
    public void setUp() {
        IdentifierNode id = new IdentifierNode("f", 1, 1);
        LiteralNode arg = new LiteralNode("1", 1, 2);
        callNode = new CallFunctionNode(id, List.of(arg), 1, 3);
    }

    @Test
    public void argumentNodesAreImmutableAndChildrenIncludeIdentifier() {
        assertEquals(1, callNode.argumentNodes().size());
        assertEquals("f", callNode.symbol());
        assertEquals(2, callNode.children().size());
    }

    @Test
    public void isTerminalTrue() {
        assertEquals(Boolean.TRUE, callNode.isTerminalNode());
    }

    @Test
    public void acceptVisitorDelegates() {
        Visitor visitor =
                new Visitor() {
                    @Override
                    public Result<String> visit(
                            com.ingsis.nodes.keyword.IfKeywordNode ifKeywordNode) {
                        return new CorrectResult<>("ok");
                    }

                    @Override
                    public Result<String> visit(
                            com.ingsis.nodes.keyword.DeclarationKeywordNode
                                    declarationKeywordNode) {
                        return new CorrectResult<>("ok");
                    }

                    @Override
                    public Result<String> visit(CallFunctionNode callFunctionNode) {
                        return new CorrectResult<>("visited");
                    }

                    @Override
                    public Result<String> visit(
                            com.ingsis.nodes.expression.operator.BinaryOperatorNode
                                    binaryOperatorNode) {
                        return new CorrectResult<>("ok");
                    }

                    @Override
                    public Result<String> visit(
                            com.ingsis.nodes.expression.operator.TypeAssignationNode
                                    typeAssignationNode) {
                        return new CorrectResult<>("ok");
                    }

                    @Override
                    public Result<String> visit(
                            com.ingsis.nodes.expression.operator.ValueAssignationNode
                                    valueAssignationNode) {
                        return new CorrectResult<>("ok");
                    }

                    @Override
                    public Result<String> visit(
                            com.ingsis.nodes.expression.identifier.IdentifierNode identifierNode) {
                        return new CorrectResult<>("ok");
                    }

                    @Override
                    public Result<String> visit(
                            com.ingsis.nodes.expression.literal.LiteralNode literalNode) {
                        return new CorrectResult<>("ok");
                    }

                    @Override
                    public Result<String> visit(com.ingsis.nodes.type.TypeNode typeNode) {
                        return new CorrectResult<>("ok");
                    }
                };

        Result<String> r = callNode.acceptVisitor(visitor);
        assertEquals("visited", r.result());
    }

    @Test
    public void acceptInterpreterHandlesResults() {
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
                        return new CorrectResult<>(new Object());
                    }
                };

        Result<String> rGood = callNode.acceptInterpreter(good);
        assertEquals("Interpreted successfully.", rGood.result());

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

        Result<String> rBad = callNode.acceptInterpreter(bad);
        assertEquals("err", rBad.error());
    }
}
