package com.ingsis.nodes.type;

import com.ingsis.result.CorrectResult;
import com.ingsis.result.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TypeNodeTest {

    private TypeNode node;

    @BeforeEach
    public void setUp() {
        node = new TypeNode(com.ingsis.types.Types.NUMBER, 5, 6);
    }

    @Test
    public void acceptVisitorDelegates() {
        com.ingsis.visitors.Visitor visitor = new com.ingsis.visitors.Visitor() {
            @Override
            public Result<String> visit(com.ingsis.nodes.keyword.IfKeywordNode ifKeywordNode) { return new CorrectResult<>("ok"); }
            @Override
            public Result<String> visit(com.ingsis.nodes.keyword.DeclarationKeywordNode declarationKeywordNode) { return new CorrectResult<>("ok"); }
            @Override
            public Result<String> visit(com.ingsis.nodes.expression.function.CallFunctionNode callFunctionNode) { return new CorrectResult<>("ok"); }
            @Override
            public Result<String> visit(com.ingsis.nodes.expression.operator.BinaryOperatorNode binaryOperatorNode) { return new CorrectResult<>("ok"); }
            @Override
            public Result<String> visit(com.ingsis.nodes.expression.operator.TypeAssignationNode typeAssignationNode) { return new CorrectResult<>("ok"); }
            @Override
            public Result<String> visit(com.ingsis.nodes.expression.operator.ValueAssignationNode valueAssignationNode) { return new CorrectResult<>("ok"); }
            @Override
            public Result<String> visit(com.ingsis.nodes.expression.identifier.IdentifierNode identifierNode) { return new CorrectResult<>("ok"); }
            @Override
            public Result<String> visit(com.ingsis.nodes.expression.literal.LiteralNode literalNode) { return new CorrectResult<>("ok"); }
            @Override
            public Result<String> visit(TypeNode typeNode) { return new CorrectResult<>("visited"); }
        };

        Result<String> r = node.acceptVisitor(visitor);
        assertEquals("visited", r.result());
    }
}
