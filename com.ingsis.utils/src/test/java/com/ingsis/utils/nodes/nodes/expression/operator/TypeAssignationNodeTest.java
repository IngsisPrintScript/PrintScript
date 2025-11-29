/*
 * My Project
 */

package com.ingsis.utils.nodes.nodes.expression.operator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.utils.nodes.nodes.expression.function.CallFunctionNode;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.nodes.expression.literal.LiteralNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.nodes.nodes.type.TypeNode;
import com.ingsis.utils.nodes.visitors.Visitor;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.type.types.Types;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TypeAssignationNodeTest {

    private TypeAssignationNode node;

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
                    return new CorrectResult<>("visited");
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

    @BeforeEach
    public void setUp() {
        IdentifierNode id = new IdentifierNode("x", 1, 1);
        TypeNode t = new TypeNode(Types.NUMBER, 1, 1);
        node = new TypeAssignationNode(id, t, 1, 2);
    }

    @Test
    public void acceptVisitorDelegates() {
        assertEquals("visited", node.acceptVisitor(SIMPLE_VISITOR).result());
    }
}
