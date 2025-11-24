package com.ingsis.nodes.factories;

import com.ingsis.nodes.Node;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.nodes.expression.operator.BinaryOperatorNode;
import com.ingsis.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.nodes.type.TypeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultNodeFactoryTest {

    private DefaultNodeFactory factory;

    @BeforeEach
    public void setUp() {
        factory = new DefaultNodeFactory();
    }

    @Test
    public void createIdentifierAndLiteral() {
        IdentifierNode id = factory.createIdentifierNode("name", 1, 2);
        LiteralNode lit = factory.createLiteralNode("v", 3, 4);
        assertEquals("name", id.name());
        assertEquals("v", lit.value());
    }

    @Test
    public void createBinaryAndIfAndDeclarationNodes() {
        IdentifierNode left = factory.createIdentifierNode("l", 1, 1);
        IdentifierNode right = factory.createIdentifierNode("r", 1, 2);
        BinaryOperatorNode bin = factory.createBinaryOperatorNode("+", left, right, 1, 3);
        assertEquals("+", bin.symbol());

        IfKeywordNode ifNode = factory.createConditionalNode(bin, List.of(), List.of(), 1, 1);
        assertEquals(0, ifNode.thenBody().size());

        TypeAssignationNode typeAssign = factory.createTypeAssignationNode(idFrom("x"), factory.createTypeNode("integer",1,1),1,1);
        ValueAssignationNode valueAssign = factory.createValueAssignationNode(idFrom("x"), litFrom("1"),1,1);
        DeclarationKeywordNode decl = factory.createLetNode(typeAssign, valueAssign, 1, 1);
        assertEquals(Boolean.TRUE, decl.isMutable());
    }

    private IdentifierNode idFrom(String n) { return factory.createIdentifierNode(n, 1, 1); }

    private LiteralNode litFrom(String v) { return factory.createLiteralNode(v, 1, 1); }
}
