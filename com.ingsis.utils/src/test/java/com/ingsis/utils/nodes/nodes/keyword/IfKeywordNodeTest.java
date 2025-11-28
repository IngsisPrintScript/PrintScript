/*
 * My Project
 */

package com.ingsis.nodes.keyword;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IfKeywordNodeTest {

    private IfKeywordNode withElse;
    private IfKeywordNode withoutElse;

    private static final com.ingsis.visitors.Checker SIMPLE_CHECKER =
            new com.ingsis.visitors.Checker() {
                @Override
                public com.ingsis.result.Result<String> check(IfKeywordNode ifKeywordNode) {
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
                    return new com.ingsis.result.CorrectResult<>("ok");
                }
            };

    private static final com.ingsis.visitors.Interpreter SIMPLE_INTERPRETER =
            new com.ingsis.visitors.Interpreter() {
                @Override
                public com.ingsis.result.Result<String> interpret(IfKeywordNode ifKeywordNode) {
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
                    return new com.ingsis.result.CorrectResult<>("v");
                }
            };

    private static final com.ingsis.visitors.Visitor SIMPLE_VISITOR =
            new com.ingsis.visitors.Visitor() {
                @Override
                public com.ingsis.result.Result<String> visit(IfKeywordNode ifKeywordNode) {
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
        assertEquals("ok", withElse.acceptChecker(SIMPLE_CHECKER).result());
        assertEquals("ok", withElse.acceptInterpreter(SIMPLE_INTERPRETER).result());
        assertEquals("ok", withElse.acceptVisitor(SIMPLE_VISITOR).result());
    }
}
