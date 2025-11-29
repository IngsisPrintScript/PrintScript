/*
 * My Project
 */

package com.ingsis.utils.nodes.nodes.type;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.utils.nodes.nodes.expression.function.CallFunctionNode;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.nodes.expression.literal.LiteralNode;
import com.ingsis.utils.nodes.nodes.expression.operator.BinaryOperatorNode;
import com.ingsis.utils.nodes.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.utils.nodes.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.nodes.visitors.Visitor;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.type.types.Types;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TypeNodeTest {

    private TypeNode node;

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
                    return new CorrectResult<>("visited");
                }
            };

    @BeforeEach
    public void setUp() {
        node = new TypeNode(Types.NUMBER, 5, 6);
    }

    @Test
    public void acceptVisitorDelegates() {
        Result<String> r = node.acceptVisitor(SIMPLE_VISITOR);
        assertEquals("visited", r.result());
    }
}
