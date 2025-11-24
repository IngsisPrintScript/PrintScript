/*
 * My Project
 */

package com.ingsis.nodes.keyword;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.result.CorrectResult;
import com.ingsis.result.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeclarationKeywordNodeTest {

    private DeclarationKeywordNode decl;

    @BeforeEach
    public void setUp() {
        com.ingsis.nodes.expression.identifier.IdentifierNode id =
                new com.ingsis.nodes.expression.identifier.IdentifierNode("x", 1, 1);
        com.ingsis.nodes.type.TypeNode t =
                new com.ingsis.nodes.type.TypeNode(com.ingsis.types.Types.NUMBER, 1, 1);
        com.ingsis.nodes.expression.operator.TypeAssignationNode typeAssign =
                new com.ingsis.nodes.expression.operator.TypeAssignationNode(id, t, 1, 1);
        com.ingsis.nodes.expression.literal.LiteralNode lit =
                new com.ingsis.nodes.expression.literal.LiteralNode("1", 1, 1);
        com.ingsis.nodes.expression.operator.ValueAssignationNode valueAssign =
                new com.ingsis.nodes.expression.operator.ValueAssignationNode(id, lit, 1, 1);
        decl = new DeclarationKeywordNode(typeAssign, valueAssign, true, 1, 1);
    }

    @Test
    public void acceptMethodsDelegate() {
        com.ingsis.visitors.Checker checker =
                new com.ingsis.visitors.Checker() {
                    @Override
                    public Result<String> check(
                            com.ingsis.nodes.keyword.IfKeywordNode ifKeywordNode) {
                        return new CorrectResult<>("ok");
                    }

                    @Override
                    public Result<String> check(DeclarationKeywordNode declarationKeywordNode) {
                        return new CorrectResult<>("checked");
                    }

                    @Override
                    public Result<String> check(
                            com.ingsis.nodes.expression.ExpressionNode expressionNode) {
                        return new CorrectResult<>("ok");
                    }
                };

        com.ingsis.visitors.Interpreter interpreter =
                new com.ingsis.visitors.Interpreter() {
                    @Override
                    public Result<String> interpret(
                            com.ingsis.nodes.keyword.IfKeywordNode ifKeywordNode) {
                        return new CorrectResult<>("ok");
                    }

                    @Override
                    public Result<String> interpret(DeclarationKeywordNode declarationKeywordNode) {
                        return new CorrectResult<>("interpreted");
                    }

                    @Override
                    public Result<Object> interpret(
                            com.ingsis.nodes.expression.ExpressionNode expressionNode) {
                        return new CorrectResult<>("v");
                    }
                };

        com.ingsis.visitors.Visitor visitor =
                new com.ingsis.visitors.Visitor() {
                    @Override
                    public Result<String> visit(
                            com.ingsis.nodes.keyword.IfKeywordNode ifKeywordNode) {
                        return new CorrectResult<>("ok");
                    }

                    @Override
                    public Result<String> visit(DeclarationKeywordNode declarationKeywordNode) {
                        return new CorrectResult<>("visited");
                    }

                    @Override
                    public Result<String> visit(
                            com.ingsis.nodes.expression.function.CallFunctionNode
                                    callFunctionNode) {
                        return new CorrectResult<>("ok");
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

        assertEquals("checked", decl.acceptChecker(checker).result());
        assertEquals("interpreted", decl.acceptInterpreter(interpreter).result());
        assertEquals("visited", decl.acceptVisitor(visitor).result());
    }
}
