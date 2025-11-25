/*
 * My Project
 */

package com.ingsis.nodes.type;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.result.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TypeNodeTest {

    private TypeNode node;

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
                    return new com.ingsis.result.CorrectResult<>("ok");
                }

                @Override
                public com.ingsis.result.Result<String> visit(
                        com.ingsis.nodes.expression.literal.LiteralNode literalNode) {
                    return new com.ingsis.result.CorrectResult<>("ok");
                }

                @Override
                public com.ingsis.result.Result<String> visit(TypeNode typeNode) {
                    return new com.ingsis.result.CorrectResult<>("visited");
                }
            };

    @BeforeEach
    public void setUp() {
        node = new TypeNode(com.ingsis.types.Types.NUMBER, 5, 6);
    }

    @Test
    public void acceptVisitorDelegates() {
        Result<String> r = node.acceptVisitor(SIMPLE_VISITOR);
        assertEquals("visited", r.result());
    }
}
