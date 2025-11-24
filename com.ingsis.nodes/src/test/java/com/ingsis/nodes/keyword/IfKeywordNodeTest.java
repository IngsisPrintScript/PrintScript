/*
 * My Project
 */

package com.ingsis.nodes.keyword;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.result.CorrectResult;
import com.ingsis.result.Result;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IfKeywordNodeTest {

    private IfKeywordNode withElse;
    private IfKeywordNode withoutElse;

    @BeforeEach
    public void setUp() {
        com.ingsis.nodes.expression.identifier.IdentifierNode cond =
                new com.ingsis.nodes.expression.identifier.IdentifierNode("c", 1, 1);
        withElse =
                new IfKeywordNode(
                        cond,
                        List.of(),
                        List.of(new com.ingsis.nodes.expression.literal.LiteralNode("1", 1, 2)),
                        1,
                        1);
        withoutElse = new IfKeywordNode(cond, List.of(), 1, 1);
    }

    @Test
    public void bodiesAreCopiedAndAccessible() {
        assertEquals(1, withElse.elseBody().size());
        assertEquals(0, withoutElse.elseBody().size());
    }

    @Test
    public void acceptMethodsDelegate() {
        com.ingsis.visitors.Checker checker =
                new com.ingsis.visitors.Checker() {
                    @Override
                    public Result<String> check(IfKeywordNode ifKeywordNode) {
                        return new CorrectResult<>("ok");
                    }

                    @Override
                    public Result<String> check(
                            com.ingsis.nodes.keyword.DeclarationKeywordNode
                                    declarationKeywordNode) {
                        return new CorrectResult<>("ok");
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
                    public Result<String> interpret(IfKeywordNode ifKeywordNode) {
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
                        return new CorrectResult<>("v");
                    }
                };

        com.ingsis.visitors.Visitor visitor =
                new com.ingsis.visitors.Visitor() {
                    @Override
                    public Result<String> visit(IfKeywordNode ifKeywordNode) {
                        return new CorrectResult<>("ok");
                    }

                    @Override
                    public Result<String> visit(
                            com.ingsis.nodes.keyword.DeclarationKeywordNode
                                    declarationKeywordNode) {
                        return new CorrectResult<>("ok");
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

        assertEquals("ok", withElse.acceptChecker(checker).result());
        assertEquals("ok", withElse.acceptInterpreter(interpreter).result());
        assertEquals("ok", withElse.acceptVisitor(visitor).result());
    }
}
