package com.ingsis.nodes.expression.operator;

import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.type.TypeNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.Result;
import com.ingsis.visitors.Visitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TypeAssignationNodeTest {

    private TypeAssignationNode node;

    @BeforeEach
    public void setUp() {
        IdentifierNode id = new IdentifierNode("x",1,1);
        TypeNode t = new TypeNode(com.ingsis.types.Types.NUMBER,1,1);
        node = new TypeAssignationNode(id,t,1,2);
    }

    @Test
    public void acceptVisitorDelegates() {
        Visitor visitor = new Visitor() {
            @Override
            public Result<String> visit(com.ingsis.nodes.keyword.IfKeywordNode ifKeywordNode) { return new CorrectResult<>("ok"); }
            @Override
            public Result<String> visit(com.ingsis.nodes.keyword.DeclarationKeywordNode declarationKeywordNode) { return new CorrectResult<>("ok"); }
            @Override
            public Result<String> visit(com.ingsis.nodes.expression.function.CallFunctionNode callFunctionNode) { return new CorrectResult<>("ok"); }
            @Override
            public Result<String> visit(com.ingsis.nodes.expression.operator.BinaryOperatorNode binaryOperatorNode) { return new CorrectResult<>("ok"); }
            @Override
            public Result<String> visit(TypeAssignationNode typeAssignationNode) { return new CorrectResult<>("visited"); }
            @Override
            public Result<String> visit(com.ingsis.nodes.expression.operator.ValueAssignationNode valueAssignationNode) { return new CorrectResult<>("ok"); }
            @Override
            public Result<String> visit(com.ingsis.nodes.expression.identifier.IdentifierNode identifierNode) { return new CorrectResult<>("ok"); }
            @Override
            public Result<String> visit(com.ingsis.nodes.expression.literal.LiteralNode literalNode) { return new CorrectResult<>("ok"); }
            @Override
            public Result<String> visit(com.ingsis.nodes.type.TypeNode typeNode) { return new CorrectResult<>("ok"); }
        };

        assertEquals("visited", node.acceptVisitor(visitor).result());
    }
}
